
package com.example.artel.MainFragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artel.Adapters.*
import com.example.artel.R
import com.example.artel.Tovar.Tovar2
import com.example.artel.URL.Companion.url2
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class HomeActivity : Fragment(R.layout.activity_home), onTovarClickListener {

    private lateinit var adapter: MyAdapter
    private lateinit var newsAdapter: NewsAdapter
    private var podsList = ArrayList<Model>()
    private var newsList = ArrayList<News>()

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val newsRecyclerView: RecyclerView = view.findViewById(R.id.newsRecyclerView)

        val buttonKatal: Button = view.findViewById(R.id.buttonKatal)
        val buttonrecyclerleft1: ImageButton = view.findViewById(R.id.recyclerbuttonleft1)
        val buttonrecyclerright1: ImageButton = view.findViewById(R.id.recyclerbuttonright1)
        val buttonrecyclerleft2: ImageButton = view.findViewById(R.id.recyclerbuttonleft2)
        val buttonrecyclerright2: ImageButton = view.findViewById(R.id.recyclerbuttonright2)

        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        adapter = MyAdapter(requireContext(), podsList, this)
        recyclerView.adapter = adapter

        newsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        newsAdapter = NewsAdapter(requireActivity(), newsList)
        newsRecyclerView.adapter = newsAdapter

        loadData()

        buttonKatal.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogActivity())
            transaction.addToBackStack(null)
            transaction.commit()

            val activity = requireActivity() as AppCompatActivity
            val buttonNavigation: BottomNavigationView = activity.findViewById(R.id.bottomNavigationView)
            buttonNavigation.selectedItemId = R.id.catalogue
        }

        buttonrecyclerleft1.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        buttonrecyclerright1.setOnClickListener {
            recyclerView.smoothScrollToPosition(4)
        }

        buttonrecyclerleft2.setOnClickListener {
            newsRecyclerView.smoothScrollToPosition(0)
        }

        buttonrecyclerright2.setOnClickListener {
            newsRecyclerView.smoothScrollToPosition(4)
        }
    }



    private fun loadData() {
        if (podsList.isEmpty()||newsList.isEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val newItems = getPodsListFromServer()
                podsList.clear()
                newItems?.let { podsList.addAll(it) }
                newsList.addAll(withContext(Dispatchers.Main) { getNewsListFromResources() })

                lifecycleScope.launch(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                    newsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private suspend fun getPodsListFromServer(): List<Model>? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url2 + "getPodsList.php")
                .build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                null
            } else {
                val json = response.body()?.string() ?: ""
                val jsonArray = JSONArray(json)
                val items = mutableListOf<Model>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title = jsonObject.getString("NAME")
                    val price = jsonObject.getString("price")
                    val imageUrl = jsonObject.optString("image", "")
                    val id = jsonObject.getInt("id")
                    if (imageUrl.isNullOrEmpty()) {
                        continue
                    }
                    val imageData = Base64.decode(imageUrl, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                    items.add(Model(bitmap, title, "$price Р", id))
                }
                items
            }
        } catch (e: Exception) {
            null
        }
    }



    private suspend fun getNewsListFromResources(): List<News> {
        val news = listOf(
            News(
                BitmapFactory.decodeResource(resources, R.drawable.traktor),
                "Трактор",
                "8 мая 2022"
            ),
            News(
                BitmapFactory.decodeResource(resources, R.drawable.img),
                "Smart Agriculture",
                "9 мая 2022"
            ),
            News(
                BitmapFactory.decodeResource(resources, R.drawable.img_1),
                "Поголовье коров",
                "10 мая 2022"
            ),
            News(
                BitmapFactory.decodeResource(resources, R.drawable.img_2),
                "Новость 4",
                "8 мая 2022"
            ),
            News(
                BitmapFactory.decodeResource(resources, R.drawable.potato),
                "Новость 5",
                "8 мая 2022"
            )
        )

        // Simulate a delay in loading data
        kotlinx.coroutines.delay(100)

        return news
    }

    override fun onTovarClicked(position: Int) {
        val fragment = Tovar2()
        val bundle = Bundle()
        bundle.putString("title", podsList[position].title)
        bundle.putString("price", podsList[position].content)
        bundle.putInt("id", podsList[position].id) // Передаем id в bundle
        fragment.arguments = bundle

        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}