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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

class AddStoryFragment : Fragment(), AddStoryViewMvc.Listener {

    private lateinit var viewMvc: AddStoryViewMvc

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null

    private lateinit var uri: Uri
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewMvc = AddStoryViewMvcImpl(layoutInflater, container)
        return viewMvc.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = view.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        token = sharedPref.getString("token", "123") ?: ""

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        super.onStop()
    }

    override fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME,"images_${System.currentTimeMillis()}.jpg")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

            uri = context?.contentResolver?.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            ) ?: Uri.EMPTY

            cameraResultLauncher.launch(uri)
        } else {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
        }
    }

    override fun uploadStory(description: String, imageUri: Uri) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (viewMvc.isImageAndDescriptionIsNull()) {
                viewMvc.showToast("Image or description cannot be empty")
            } else {
                viewMvc.showProgressBar()

                getDeviceLocation()

                val (descRequestBody, imageRequestBody) = bodyPartDescAndImage(imageUri, description)

                val apiService = RestApiService()
                apiService.uploadStory(token, descRequestBody, imageRequestBody, lastKnownLocation?.latitude, lastKnownLocation?.longitude) {
                    if (it?.error == false) {
                        viewMvc.hideProgressBar()
                        viewMvc.showToast("Story Uploaded")

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, StoryListFragment())
                            .commit()
                        viewMvc.selectStoryListFragment(requireActivity())
                    } else {
                        viewMvc.hideProgressBar()
                        viewMvc.showToast("Upload Failed")
                    }
                }
            }
        } else {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }

    override fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            galleryResultLauncher.launch(intent)
        }
    }

    private var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewMvc.loadImage(uri)
            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                context?.let {
                    intent?.data?.let { viewMvc.loadImage(it) }
                }
            }
        }

    private fun bodyPartDescAndImage(
        imageUri: Uri,
        description: String
    ): Pair<RequestBody, MultipartBody.Part> {
        val path: File =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
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
        val descRequestBody = description.toRequestBody("text/plain".toMediaType())

        val imageRequestBody = MultipartBody.Part.createFormData("photo", file.name, requestFile)

        return Pair(descRequestBody, imageRequestBody)
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
















