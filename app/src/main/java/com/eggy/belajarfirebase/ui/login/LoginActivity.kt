package com.eggy.belajarfirebase.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.eggy.belajarfirebase.MainActivity
import com.eggy.belajarfirebase.R
import com.eggy.belajarfirebase.ui.register.RegisterActivity
import com.eggy.belajarfirebase.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding:ActivityLoginBinding
    private lateinit var googleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initFirebase()
        initGmail()
    }

    private fun initGmail() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        googleSignInClient  = GoogleSignIn.getClient(this, gso)
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun initView() {
        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnSignin.setOnClickListener {

            val email =binding.edtEmail.text.toString()
            val pass= binding.edtPass.text.toString()

            if (email.isEmpty()){
                binding.edtEmail.error = "Email tidak boleh kosong"
            } else if (pass.isEmpty()){
                binding.edtPass.error = "Password tidak boleh kosong"
            }
            else{
                binding.progress.visibility = View.VISIBLE
                loginToServer(email, pass)
            }
        }

        binding.signInGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val intent  = googleSignInClient.signInIntent
        startActivityForResult(intent, 1)
    }

    private fun loginToServer(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {

                if (it.isSuccessful){
                    binding.progress.visibility = View.GONE
                    val user = mAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()

                }else{
                    binding.progress.visibility = View.GONE
                    Snackbar.make(binding.root, "Authentication Failed", Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                Log.d("id", account?.id.toString())
                firebaseAuthWithGoogle(account?.idToken!!)


            }catch (e:ApiException){
                Log.d("error", e.message.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {

        binding.progress.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.progress.visibility = View.GONE

                    Log.d("TAG", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    binding.progress.visibility = View.GONE
                    Log.w("TAG", "signInWithCredential:failure", task.exception)

                    Snackbar.make(binding.root, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()

                }
            }
    }
}