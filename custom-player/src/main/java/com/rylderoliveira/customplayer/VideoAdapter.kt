package com.rylderoliveira.customplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<CustomTrack> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_config, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.text_view_item_config)

        fun bind(customTrack: CustomTrack) {
            itemView.isSelected = customTrack.isSelected
            textView.text = customTrack.name
        }
    }
}