package com.example.tp2v1.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tp2v1.R

// callbacks used when user click on an item of the list
interface OnGameListener {
    fun onRecycleViewItemClick(position: Int)
    fun onRecycleViewLongPress(position: Int)
}

class MyAdapter(private val touhouGames: MutableList<TouhouGame>, onGameListener: OnGameListener) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View, listener: OnGameListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val title: TextView
        val release: TextView
        val image: ImageView
        val onGameListener: OnGameListener
        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.title)
            release = view.findViewById(R.id.release)
            image = view.findViewById(R.id.imageView)
            onGameListener = listener
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onGameListener.onRecycleViewItemClick(adapterPosition)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item, viewGroup, false)
        return ViewHolder(view, mOnGameListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from the dataset at this position
        viewHolder.title.text = touhouGames.get(position).name
        viewHolder.release.text = touhouGames.get(position).release_date
        viewHolder.image.load(viewHolder.itemView.context.getString(R.string.image_server_URL)+touhouGames.get(position).cover) // get the image from the server
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount() = touhouGames.size

    var mOnGameListener: OnGameListener = onGameListener
}