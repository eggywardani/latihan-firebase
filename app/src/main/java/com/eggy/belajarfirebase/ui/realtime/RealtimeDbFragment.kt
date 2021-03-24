package com.eggy.belajarfirebase.ui.realtime

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eggy.belajarfirebase.adapter.RealtimeAdapter
import com.eggy.belajarfirebase.databinding.FragmentRealtimeDbBinding
import com.eggy.belajarfirebase.model.Language
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RealtimeDbFragment : Fragment() {



    private var binding: FragmentRealtimeDbBinding? = null
    private var db = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRealtimeDbBinding.inflate(layoutInflater, container, false)
        return binding?.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()


    }


    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        binding?.progress?.visibility = View.VISIBLE

        db.getReference("language").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding?.progress?.visibility = View.GONE
                if (snapshot.exists()) {
                    val list = ArrayList<Language>()
                    for (i in snapshot.children) {
                        val language = i.getValue(Language::class.java)
                        list.add(
                            Language(
                                language?.id ?: "",
                                language?.name ?: "",
                                language?.desc ?: ""
                            )
                        )
                    }

                    bindData(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {

                binding?.progress?.visibility = View.GONE
                Snackbar.make(requireView(), "Data gagal dimuat", Snackbar.LENGTH_SHORT).show()
                Log.e("errorsnap", error.details)
            }
        })
    }

    private fun bindData(list: java.util.ArrayList<Language>) {
        val adapter = RealtimeAdapter(list)
        binding?.rvLanguage?.adapter = adapter
    }

    private fun initAction() {
        binding?.btnAdd?.setOnClickListener {
            startActivity(Intent(requireContext(), InputRealtimeDbActivity::class.java))
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}