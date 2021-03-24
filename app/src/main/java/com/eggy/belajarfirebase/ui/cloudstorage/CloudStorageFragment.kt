package com.eggy.belajarfirebase.ui.cloudstorage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eggy.belajarfirebase.databinding.FragmentCloudStorageBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class CloudStorageFragment : Fragment() {

    companion object {
        const val FILE_CHOOSER = 100
    }

    private lateinit var path: Uri
    private var binding: FragmentCloudStorageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCloudStorageBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAction()
    }

    private fun initAction() {
        binding?.ivPhoto?.setOnClickListener {
            openFileChooser()
        }
        binding?.btnUpload?.setOnClickListener {
            uploadFile()
        }
    }

    private fun getName(): String {
        val time = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return time
    }

    private fun uploadFile() {

        binding?.viewProgress?.visibility = View.VISIBLE
        val imageReference = FirebaseStorage.getInstance().reference.child("images/${getName()}")
        imageReference.putFile(path)
            .addOnSuccessListener {
                binding?.viewProgress?.visibility = View.GONE
                binding?.ivSelectedPhoto?.visibility = View.GONE
                binding?.btnUpload?.visibility = View.GONE
                binding?.ivPhoto?.visibility = View.VISIBLE
                binding?.tvPhoto?.visibility = View.VISIBLE
                Snackbar.make(requireView(), "Upload Selesai", Snackbar.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                binding?.viewProgress?.visibility = View.GONE
                Snackbar.make(requireView(), "Upload Gagal", Snackbar.LENGTH_SHORT).show()
            }
            .addOnProgressListener {
                val progress = (100 * it.bytesTransferred) / it.totalByteCount
               
                binding?.tvProgress?.text = "Uploaded $progress %"
                binding?.progress?.progress = progress.toInt()
                binding?.progress?.animate()

            }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), FILE_CHOOSER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER && resultCode == Activity.RESULT_OK && data != null) {
            path = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, path)
            binding?.ivSelectedPhoto?.visibility = View.VISIBLE
            binding?.btnUpload?.visibility = View.VISIBLE
            binding?.ivPhoto?.visibility = View.GONE
            binding?.tvPhoto?.visibility = View.GONE

            binding?.ivSelectedPhoto?.setImageBitmap(bitmap)


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}