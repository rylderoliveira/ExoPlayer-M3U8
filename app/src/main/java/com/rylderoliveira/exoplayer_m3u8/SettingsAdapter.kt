package com.rylderoliveira.exoplayer_m3u8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.SettingViewHolder>() {

    private var items: MutableList<String?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_settings, parent, false)
        return SettingViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun add(names: MutableList<String?>) {
        for (name in names) {
            items.add(name)
        }
    }

    inner class SettingViewHolder(itemView: View) : ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.text_view_item_settings)

        fun bind(name: String?) {
            textView.text = name
        }
    }
}
