package com.eggy.belajarfirebase.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eggy.belajarfirebase.ui.login.LoginActivity
import com.eggy.belajarfirebase.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth


class AccountFragment : Fragment() {


    private var binding:FragmentAccountBinding? = null
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        user.let {
            binding?.tvEmail?.text = it?.email
        }

        binding?.signOut?.setOnClickListener {
            binding?.progress?.visibility = View.VISIBLE
            mAuth.signOut()
            activity?.startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
            activity?.overridePendingTransition(0,0)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}