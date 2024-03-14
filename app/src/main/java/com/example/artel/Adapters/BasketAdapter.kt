package com.example.artel.Adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artel.R
import com.example.artel.URL.Companion.url2
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class BasketAdapter(
    private val context: Context?,
    private var basketList: MutableList<Basket>,
) : RecyclerView.Adapter<BasketAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.basket_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val basket = basketList[position]
        Glide.with(holder.itemView.context)
            .load(basket.img)
            .override(200, 200)
            .centerCrop()
            .into(holder.imageView)
        holder.titleTextView.text = basket.title
        val totalPrice = basket.quantity * basket.price
        holder.priceTextView.text = "Общая цена: $totalPrice Р"
        holder.countTextView.text = "Количество: " + basket.quantity.toString()

        holder.itemView.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Изменить количество")
            val input = EditText(context)
            input.inputType = InputType.TYPE_CLASS_NUMBER
            input.setText(basket.quantity.toString())
            dialog.setView(input)
            dialog.setPositiveButton("OK") { _, _ ->
                val newCount = input.text.toString().toInt()
                basket.quantity = newCount
                notifyDataSetChanged()
                updateItemCountOnServer(basket.title, newCount)
            }
            dialog.setNegativeButton("Отмена", null)
            dialog.setNeutralButton("Удалить") { _, _ ->
                deleteItemFromDatabase(basket.title)
            }
            dialog.show()
        }
    }

    private fun updateItemCountOnServer(title: String, count: Int) {
        if (count == 0) {
            deleteItemFromDatabase(title)
        } else {
            val request = Request.Builder()
                .url(url2 + "updateItemCount.php")
                .post(
                    FormBody.Builder()
                        .add("title", title)
                        .add("count", count.toString())
                        .build()
                )
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle failure
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle response
                }
            })
        }
    }

    private fun deleteItemFromDatabase(title: String) {
        val request = Request.Builder()
            .url(url2 + "deleteItem.php")
            .post(
                FormBody.Builder()
                    .add("title", title)
                    .build()
            )
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle response
                if (response.isSuccessful) {
                    // Create a Handler instance associated with the main thread
                    val mainHandler = Handler(Looper.getMainLooper())

                    // Post a Runnable to the main thread's message queue
                    mainHandler.post {
                        val item = basketList.find { it.title == title }
                        item?.let {
                            basketList.remove(it)
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }



    override fun getItemCount(): Int {
        return basketList.size
    }


    fun addItem(basket: Basket) {
        val existingItem = basketList.find { it.title == basket.title }
        if (existingItem != null) {
            existingItem.count++
        } else {
            basket.count = 1
            basketList.add(basket)
        }
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageBask)
        val titleTextView: TextView = itemView.findViewById(R.id.titleBask)
        val priceTextView: TextView = itemView.findViewById(R.id.contentBask)
        val countTextView: TextView = itemView.findViewById(R.id.countBask)

    }

}