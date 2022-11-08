package com.example.storyapp.view.addstory

import android.app.Activity
import android.content.ContentResolver
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
import com.example.storyapp.model.response.CommonResponse
import com.example.storyapp.network.ApiClient
import com.example.storyapp.network.RestApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class AddStoryFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
//            val requestFile = RequestBody.create(
//                MediaType.parse("multipart/form-data"),
//                file
//            )
//            RequestBody.create(
//                context?.contentResolver?.getType(imageUri)
//                    ?.let { it1 -> MediaType.parse(it1) }, file
//            )
            val imageRequestBody =
                MultipartBody.Part.createFormData("photo", file.name, requestFile)
//            val descRequestBody = RequestBody.create(
//                MediaType.parse("text/plain"),
//                description.text.toString()
//            )

            uploadStory(token, descRequestBody, imageRequestBody, null, null)

            Log.d("AddStoryFragment", "requestBody: $requestFile")
            Log.d("AddStoryFragment", "file: $file")
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
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
                val data: Intent? = result.data
                context?.let {
                    Glide.with(it)
                        .load(data?.extras?.get("data"))
                        .into(imageView)
                }
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
//                val uri = Uri.parse(imageUri.toString())
//                val file = File(intent.data.toString())
//                Log.d("AddStoryFragment", "uri: $uri")
//                Log.d("AddStoryFragment", "file: $file")
            }
        }

    private fun uploadStory(
        token: String,
        description: RequestBody,
        imageUri: MultipartBody.Part,
        lat: RequestBody?,
        lon: RequestBody?
    ) {
        val apiService = RestApiService()
        ApiClient.getApiService().uploadStory(
            token, description, imageUri, lat, lon
        ).enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                Toast.makeText(context, "response called", Toast.LENGTH_SHORT).show()
                Log.i(
                    "AddStoryFragment",
                    "onResponse message: ${response.message()}\n ${response.code()}\n ${response.errorBody()}"
                )
                Log.i("AddStoryFragment", "onResponse: ${response.body()?.error}")
                Log.i("AddStoryFragment", "onResponse: ${response.body()?.message}")
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                Log.d("AddStoryFragment", "onResponse: ${t.message}")
            }

        })
    }
}
















