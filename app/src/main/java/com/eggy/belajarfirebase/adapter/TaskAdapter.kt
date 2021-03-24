package com.eggy.belajarfirebase.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eggy.belajarfirebase.ui.firestore.InputFirestoreActivity
import com.eggy.belajarfirebase.R
import com.eggy.belajarfirebase.model.Task
import com.eggy.belajarfirebase.databinding.ItemTaskBinding

class TaskAdapter(private val list: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemTaskBinding.bind(itemView)

        fun bind(item: Task) {
            binding.tvKegiatan.text = item.kegiatan
            binding.tvKeterangan.text = item.keterangan
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, InputFirestoreActivity::class.java)
                intent.putExtra(InputFirestoreActivity.KEGIATAN, item.kegiatan)
                intent.putExtra(InputFirestoreActivity.ID, item.id)
                intent.putExtra(InputFirestoreActivity.KETERANGAN, item.keterangan)
                itemView.context.startActivity(intent)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}