package com.example.storyapp.view.addstory

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.storylist.StoryListFragment
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

        openCameraButton.setOnClickListener {
            openCamera()
        }

        openGalleryButton.setOnClickListener {
            openGallery()
        }

        uploadButton.setOnClickListener {
            if (imageView.drawable == null || description.text.isEmpty()) {
                Toast.makeText(context, "Image or description cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
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

                uploadStory(token, descRequestBody, imageRequestBody, null, null)

                Log.d("AddStoryFragment", "requestBody: $requestFile")
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
        lat: RequestBody?,
        lon: RequestBody?,
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
}
















