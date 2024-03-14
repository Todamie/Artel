package com.example.artel.MainFragments


import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artel.Adapters.Basket
import com.example.artel.Adapters.BasketAdapter
import com.example.artel.Adapters.Model
import com.example.artel.R
import com.example.artel.URL.Companion.url2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException


class BasketActivity : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BasketAdapter
    private val items = ArrayList<Basket>()
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_basket, container, false)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Находим элементы на макете и назначаем адаптер для Recycler View
        recyclerView = view.findViewById(R.id.rvBasket)


        adapter = BasketAdapter(requireContext(), items)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())





    }
    // Загружаем данные с сервера в отдельном потоке
    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            val newItems = getPodsListFromServer()
            items.clear()
            newItems?.let { items.addAll(it) }
            GlobalScope.launch(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private suspend fun getPodsListFromServer(): List<Basket>? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url2 + "getBaskList.php")

                .build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                null
            } else {
                val json = response.body()?.string() ?: ""
                val jsonArray = JSONArray(json)
                val items = mutableListOf<Basket>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title = jsonObject.getString("NAME")
                    val price = jsonObject.getString("price")
                    val quantity = jsonObject.getInt("quantity")
                    val imageUrl = jsonObject.optString("image", "")
                    if (imageUrl.isNullOrEmpty()) {
                        continue
                    }
                    val imageData = Base64.decode(imageUrl, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                    val existingItem = items.find { it.title == title }
                    if (existingItem != null) {
                        existingItem.count++
                    } else {
                        val priceString = jsonObject.getString("price")
                        val price = priceString.toDoubleOrNull() ?: 0.0
                        items.add(Basket(bitmap, title, "$price Р", price = price, quantity = quantity))
                    }
                }
                items
            }
        } catch (e: Exception) {
            null
        }
    }


}



