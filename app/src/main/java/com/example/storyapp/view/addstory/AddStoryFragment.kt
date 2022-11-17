package com.example.storyapp.view.addstory

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.storylist.StoryListFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class AddStoryFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri
    private lateinit var progressBar: ProgressBar
    private lateinit var uri: Uri

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_add_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progress_circular_add_story)
        imageView = view.findViewById(R.id.add_story_image)
        val uploadButton = view.findViewById<Button>(R.id.btn_add_story_upload)
        val openCameraButton = view.findViewById<Button>(R.id.btn_add_story_image_camera)
        val openGalleryButton = view.findViewById<Button>(R.id.btn_add_story_image_gallery)
        val description = view.findViewById<EditText>(R.id.et_add_story_description)

        val sharedPref = view.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "123") ?: ""

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        openCameraButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
            }
        }

        openGalleryButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            } else {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }

        uploadButton.setOnClickListener {
            if (imageView.drawable == null || description.text.isEmpty()) {
                Toast.makeText(context, "Image or description cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                getDeviceLocation()

                progressBar.visibility = View.VISIBLE

                val path: File =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val file = File.createTempFile("image", ".jpg", path)

                val contentResolver: ContentResolver = requireContext().contentResolver

                val inputStream = contentResolver.openInputStream(imageUri) as InputStream
                val outputStream: OutputStream = FileOutputStream(file)
                val buf = ByteArray(1024)
                var len: Int
                while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
                outputStream.close()
                inputStream.close()

                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val descRequestBody = description.text.toString()
                    .toRequestBody("text/plain".toMediaType())

                val imageRequestBody =
                    MultipartBody.Part.createFormData("photo", file.name, requestFile)

                uploadStory(
                    token,
                    descRequestBody,
                    imageRequestBody,
                    lastKnownLocation?.latitude,
                    lastKnownLocation?.longitude
                )

                Log.d("AddStoryFragment", "requestBody: $requestFile")
                Log.d(
                    "AddStoryFragment",
                    "requestBody: lat ${lastKnownLocation?.latitude}, lon ${lastKnownLocation?.longitude}"
                )
                Log.d("AddStoryFragment", "file: $file")
            }
        }

    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "images")

            uri = context?.contentResolver
                ?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            cameraResultLauncher.launch(intent)
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            galleryResultLauncher.launch(intent)
        }
    }

    private var cameraResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                context?.let {
                    Glide.with(it)
                        .load(uri)
                        .into(imageView)
                }
                imageUri = uri
            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                context?.let {
                    Glide.with(it)
                        .load(intent?.data)
                        .into(imageView)
                }
                imageUri = intent?.data!!
            }
        }

    private fun uploadStory(
        token: String,
        description: RequestBody,
        imageUri: MultipartBody.Part,
        lat: Double?,
        lon: Double?,
    ) {
        val apiService = RestApiService()
        apiService.uploadStory(token, description, imageUri, lat, lon) {
            if (it?.error == false) {
                progressBar.visibility = View.GONE
                Toast.makeText(context, "Story Uploaded", Toast.LENGTH_SHORT).show()

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, StoryListFragment())
                    .commit()
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
                Log.d("AddStoryFragment", "uploadStory: ${it?.message}")
            }
        }
    }

    private var requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            when {
                permission[Manifest.permission.CAMERA] ?: false -> {
                    openCamera()
                }
                permission[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false -> {
                    openGallery()
                }
                permission[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {}
                permission[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {}
            }
        }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation() {
        try {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                    }
                }
            } else {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e);
        }
    }
}
















