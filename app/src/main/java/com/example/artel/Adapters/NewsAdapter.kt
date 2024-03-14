package com.example.artel.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artel.R


class NewsAdapter(val context: Context?, var newsList: List<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()  {
    // Создание нового представления элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    // Заполнение представления элемента данными
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val currentItem = newsList[position]

        Glide.with(holder.itemView.context)
            .load(currentItem.imageResource)
            .override(200, 200)
            .centerCrop()
            .into(holder.newsImage)
        holder.newsTitle.text = currentItem.title
        holder.newsDate.text = currentItem.date

        holder.itemView.setOnClickListener{
            Toast.makeText(context,currentItem.title, Toast.LENGTH_LONG).show()
        }
    }

    // Возвращает общее количество элементов в списке
    override fun getItemCount() :Int {
       return newsList.size
    }


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        val newsTitle: TextView = itemView.findViewById(R.id.newsTitle)
        val newsDate: TextView = itemView.findViewById(R.id.newsDate)
    }
}