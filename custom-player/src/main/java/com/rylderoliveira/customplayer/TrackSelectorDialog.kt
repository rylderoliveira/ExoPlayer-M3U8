package com.rylderoliveira.customplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class TrackSelectorDialog : DialogFragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: TrackAdapter
    lateinit var onTrackClick: (CustomTrack) -> Unit
    var tracks: List<CustomTrack> = listOf()
        set(newTracks) {
            field = newTracks
            adapter.items = field
            adapter.notifyDataSetChanged()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_track_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_dialog_track_selector)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        with(adapter) {
            recyclerView.adapter = this
            this.listener = onTrackClick
        }
        recyclerView.requestFocus()
    }
}