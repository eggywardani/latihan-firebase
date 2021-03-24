package com.eggy.belajarfirebase.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eggy.belajarfirebase.ui.realtime.InputRealtimeDbActivity
import com.eggy.belajarfirebase.model.Language
import com.eggy.belajarfirebase.R
import com.eggy.belajarfirebase.databinding.ItemLanguageBinding

class RealtimeAdapter(private val list: List<Language>) : RecyclerView.Adapter<RealtimeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLanguageBinding.bind(itemView)

        fun bind(item: Language) {
            binding.tvName.text = item.name
            binding.tvDesc.text = item.desc
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, InputRealtimeDbActivity::class.java)
                intent.putExtra(InputRealtimeDbActivity.NAME, item.name)
                intent.putExtra(InputRealtimeDbActivity.ID, item.id)
                intent.putExtra(InputRealtimeDbActivity.DESC, item.desc)
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}