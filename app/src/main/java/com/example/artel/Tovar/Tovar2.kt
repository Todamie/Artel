package com.example.artel.Tovar

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.artel.MainFragments.BasketActivity
import com.example.artel.MainFragments.HomeActivity
import com.example.artel.R
import com.example.artel.URL.Companion.url2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class Tovar2 : Fragment() {

    lateinit var tvName: TextView
    lateinit var ivImg: ImageView
    lateinit var btnAddToBask: Button
    lateinit var tovPrice: TextView
    lateinit var tovDesc: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tovar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvName = view.findViewById(R.id.tv_name)
        ivImg = view.findViewById(R.id.iv_img)
        tovDesc = view.findViewById(R.id.tovarDesc)
        tovPrice = view.findViewById(R.id.tovarPrice)
        btnAddToBask = view.findViewById(R.id.addToBasket)


        val title = arguments?.getString("title")
        val id = arguments?.getInt("id")
        val price = arguments?.getString("price")

        // Загружаем данные с сервера по id
        loadDataWithId(id ?: -1)

        tvName.text = title
        tovPrice.text = price

        val viewModel: SharedViewModel by activityViewModels()

        btnAddToBask.setOnClickListener {

            writeToDatabase(id)
            val fragment = HomeActivity()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putInt("id", id ?: -1) // Добавляем id
            bundle.putString("price", price)
            fragment.arguments = bundle

            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()


        }

    }

    fun writeToDatabase(id: Int?) = runBlocking {
        launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val requestBody = FormBody.Builder()
                .add("id", id.toString())
                .build()

            val request = Request.Builder()
                .url(url2 + "addToCart.php")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("fail", "хуета")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        throw IOException("Ошибка запроса: " + response.code())
                    }
                }
            })
        }
    }

    private fun loadDataWithId(id: Int) {
        // Загружаем данные с сервера по id
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url2 + "getPodDetails.php?id=$id")
                .build()
            val response = client.newCall(request).execute()
            val json = response.body()?.string() ?: ""
            val jsonArray = JSONArray(json)
            val jsonObject = jsonArray.optJSONObject(0) ?: return@launch
            val imageUrl = jsonObject.optString("image", "")
            if (imageUrl.isNullOrEmpty()) {
                return@launch
            }
            val imageData = Base64.decode(imageUrl, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            val description = jsonObject.getString("description")
            val price = jsonObject.getString("price")

            // Обновляем UI в главном потоке
            GlobalScope.launch(Dispatchers.Main) {
                Glide.with(requireContext()).load(bitmap).override(800, 600).centerCrop().into(ivImg)
                tovDesc.text = description
                tovPrice.text = "$price Р"
            }
        }
    }
}