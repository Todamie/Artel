package com.example.artel.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artel.R


class MyAdapter(
    val context: Context?,
    var pods_list: List<Model>,
    private val onTovarClickListener: onTovarClickListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tovar_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val podsList = pods_list[position]

        //holder.img.setImageBitmap(podsList.img)
        Glide.with(holder.itemView.context)
            .load(podsList.img)
            .override(200, 200)
            .centerCrop()
            .into(holder.img)
        holder.title.setText(podsList.title)
        holder.con.setText(podsList.content)

        holder.itemView.setOnClickListener {
            onTovarClickListener.onTovarClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return pods_list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.image)
        val title = itemView.findViewById<TextView>(R.id.title)
        val con = itemView.findViewById<TextView>(R.id.content)
    }
}