package com.rylderoliveira.customplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPosition = FIRST_INDEX
    lateinit var listener: ((CustomTrack) -> Unit)
    var items: List<CustomTrack> = listOf()
        set(newItems) {
            field = newItems.sortedBy { it.index }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_config, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.bind(items[position])
        itemViewHolder.itemView.isSelected = selectedPosition == position
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.text_view_item_config)

        init {
            itemView.setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = absoluteAdapterPosition
                notifyItemChanged(selectedPosition)
                listener.invoke(items[selectedPosition])
            }
        }

        fun bind(customTrack: CustomTrack) {
            textView.text = customTrack.name
        }
    }

    companion object {
        const val FIRST_INDEX = 0
    }
}
