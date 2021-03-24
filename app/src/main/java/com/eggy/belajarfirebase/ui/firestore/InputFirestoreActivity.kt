package com.eggy.belajarfirebase.ui.firestore

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.eggy.belajarfirebase.databinding.ActivityInputBinding
import com.eggy.belajarfirebase.model.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class InputFirestoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private lateinit var db: FirebaseFirestore

    companion object {
        const val KEGIATAN = "kegiatan"
        const val KETERANGAN = "keterangan"
        const val ID = "id"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kegiatan = intent.getStringExtra(KEGIATAN)
        val keterangan = intent.getStringExtra(KETERANGAN)


        if (kegiatan != null && keterangan != null) {
            val id = intent.getStringExtra(ID) as String
            binding.edtKegiatan.setText(kegiatan)
            binding.edtKeterangan.setText(keterangan)
            binding.btnHapus.visibility = View.VISIBLE

            binding.btnSimpan.setOnClickListener {
                binding.progress.visibility = View.VISIBLE
                updateToFireStore(id)
            }
            binding.btnHapus.setOnClickListener {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Hapus")
                    .setCancelable(false)
                    .setMessage("Yakin ingin hapus data ini ?")
                    .setPositiveButton("Ya") { dialog, _ ->
                        deleteFirestore(id)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                    }
                alert.show()
            }
            binding.btnSimpan.text = "Ubah"
        } else {
            binding.btnHapus.visibility = View.GONE
            binding.btnSimpan.setOnClickListener {
                binding.progress.visibility = View.VISIBLE
                saveToFireStore()
            }
            binding.btnSimpan.text = "Simpan"
        }

        db = FirebaseFirestore.getInstance()


    }

    private fun deleteFirestore(id:String) {

        binding.progress.visibility = View.VISIBLE
        db.collection("tasks")
            .document(id)
            .delete()
            .addOnSuccessListener {

                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data berhasil dihapus", Snackbar.LENGTH_SHORT)
                    .show()
                finish()
            }
            .addOnFailureListener {
                Log.e("error", it.message.toString())
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data Gagal dihapus", Snackbar.LENGTH_SHORT)
                    .show()
            }
    }

    private fun updateToFireStore(id:String) {

        val tasks = Task( id , binding.edtKegiatan.text.toString(), binding.edtKeterangan.text.toString())
        db.collection("tasks")
            .document(id)
            .set(tasks)
            .addOnSuccessListener {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data berhasil diubah", Snackbar.LENGTH_SHORT)
                    .show()
                finish()
            }
            .addOnFailureListener {
                Log.e("error", it.message.toString())
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data Gagal diubah", Snackbar.LENGTH_SHORT)
                    .show()
            }

    }


    private fun saveToFireStore() {

        val id = UUID.randomUUID().toString()
        val tasks = Task(id,binding.edtKegiatan.text.toString(), binding.edtKeterangan.text.toString())

        db.collection("tasks")
            .document(id)
            .set(tasks)
            .addOnSuccessListener {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data berhasil tersimpan", Snackbar.LENGTH_SHORT)
                    .show()
                finish()
            }
            .addOnFailureListener {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data Gagal tersimpan", Snackbar.LENGTH_SHORT)
                    .show()
            }
    }
}