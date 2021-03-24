package com.eggy.belajarfirebase.ui.firestore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eggy.belajarfirebase.adapter.TaskAdapter
import com.eggy.belajarfirebase.databinding.FragmentHomeBinding
import com.eggy.belajarfirebase.model.Task
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {


    private var binding: FragmentHomeBinding? = null
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        initAction()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        binding?.progress?.visibility = View.VISIBLE
        db.collection("tasks").get()
            .addOnSuccessListener {

                binding?.progress?.visibility = View.GONE
                binding?.btnAdd?.visibility = View.VISIBLE
                binding?.rvTasks?.visibility = View.VISIBLE


                val list = ArrayList<Task>()
                for (item in it) {
                    list.add(
                        Task(
                            item.data["id"] as String,
                            item.data["kegiatan"] as String,
                            item.data["keterangan"] as String
                        )
                    )
                }
                val adapter = TaskAdapter(list)
                binding?.rvTasks?.adapter = adapter

            }
    }


    private fun initAction() {
        binding?.btnAdd?.setOnClickListener {
            startActivity(Intent(requireContext(), InputFirestoreActivity::class.java))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}