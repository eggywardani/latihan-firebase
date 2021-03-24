package com.eggy.belajarfirebase.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.eggy.belajarfirebase.databinding.ActivityRegisterBinding
import com.eggy.belajarfirebase.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initFirebase()
    }

    private fun initView() {

        binding.btnSignup.setOnClickListener {
            binding.progress.visibility =View.VISIBLE
            val email =binding.edtEmail.text.toString()
            val pass= binding.edtPass.text.toString()
            val passConf= binding.edtPassConf.text.toString()

            if (email.isEmpty()){
                binding.edtEmail.error = "Email tidak boleh kosong"
            } else if (pass.isEmpty()){
                binding.edtPass.error = "Password tidak boleh kosong"
            }
            else if (pass.length < 6){
                Snackbar.make(binding.root, "Password harus terdiri dari 6 Karakter", Snackbar.LENGTH_SHORT).show()
            }
            else if (pass != passConf){
                Snackbar.make(binding.root, "Password tidak sama", Snackbar.LENGTH_SHORT).show()
            }
            else{
                registerToFirebase(email, pass)
            }
        }

        binding.tvMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerToFirebase(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {

                if (it.isSuccessful){
                    binding.progress.visibility = View.GONE
                    val user = mAuth.currentUser
                    finish()


                }else{
                    binding.progress.visibility = View.GONE
                    Snackbar.make(binding.root, "Authentication Failed", Snackbar.LENGTH_SHORT).show()
                }
            }
    }



    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
    }
}