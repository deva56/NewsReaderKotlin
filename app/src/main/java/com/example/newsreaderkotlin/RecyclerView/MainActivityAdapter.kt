package com.example.newsreaderkotlin.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsreaderkotlin.Entity.NewsEntity
import com.example.newsreaderkotlin.R

class MainActivityAdapter(
    private val context: Context,
    private var records: List<NewsEntity>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MainActivityAdapter.MainActivityHolder>() {

    inner class MainActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerText: TextView = itemView.findViewById(R.id.MainHeaderText)
        val imageView: ImageView = itemView.findViewById(R.id.MainImageView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(records[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivityAdapter.MainActivityHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_activity_recycler_view_item, parent, false)
        return MainActivityHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainActivityAdapter.MainActivityHolder, position: Int) {
        val currentRecord = records[position]
        holder.headerText.text = currentRecord.title
        Glide.with(context).load(currentRecord.urlToImage).centerCrop()
            .placeholder(R.drawable.ic_no_image_available).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    fun setRecords(records: List<NewsEntity>) {
        this.records = records
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(newsItem: NewsEntity)
    }
}