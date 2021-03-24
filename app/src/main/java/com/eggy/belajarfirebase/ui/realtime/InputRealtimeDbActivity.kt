package com.eggy.belajarfirebase.ui.realtime

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.eggy.belajarfirebase.databinding.ActivityInputRealtimeDbBinding
import com.eggy.belajarfirebase.model.Language
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class InputRealtimeDbActivity : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityInputRealtimeDbBinding
    companion object{
        const val NAME ="name"
        const val DESC ="desc"
        const val ID ="id"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputRealtimeDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance()

        val name = intent.getStringExtra(NAME)
        val desc = intent.getStringExtra(DESC)
        if (name != null && desc != null) {
            val id = intent.getStringExtra(ID)
            binding.edtName.setText(name)
            binding.edtDesc.setText(desc)
            binding.btnHapus.visibility = View.VISIBLE

            binding.btnSimpan.setOnClickListener {
                binding.progress.visibility = View.VISIBLE
                updateData(id)
            }
            binding.btnHapus.setOnClickListener {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Hapus")
                    .setCancelable(false)
                    .setMessage("Yakin ingin hapus data ini ?")
                    .setPositiveButton("Ya") { dialog, _ ->
                        deleteData(id)
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
                saveRealtimeDb()
            }
            binding.btnSimpan.text = "Simpan"
        }

    }

    private fun deleteData(id: String?) {

        binding.progress.visibility = View.VISIBLE
        db.getReference("language").child(id?:"")
            .removeValue()
            .addOnSuccessListener {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data berhasil dihapus", Snackbar.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data Gagal dihapus", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun updateData(id: String?) {
        db.getReference("language").child(id?:"").setValue(Language(id, binding.edtName.text.toString(),binding.edtDesc.text.toString(),))
            .addOnCompleteListener {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data berhasil diubah", Snackbar.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data Gagal diubah", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun saveRealtimeDb() {
        val id = UUID.randomUUID().toString()
        binding.progress.visibility = View.VISIBLE
        db.getReference("language").child(id ?: "").setValue(
            Language(
                id ?: "",
                binding.edtName.text.toString(),
                binding.edtDesc.text.toString(),
            )
        )
            .addOnSuccessListener {
                binding.progress.visibility = View.GONE

                Snackbar.make(binding.root, "Data berhasil ditambah", Snackbar.LENGTH_SHORT).show()

                finish()
            }
            .addOnFailureListener {
                Log.e("err", it.message.toString())
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.root, "Data gagal ditambah", Snackbar.LENGTH_SHORT).show()

            }
    }
}