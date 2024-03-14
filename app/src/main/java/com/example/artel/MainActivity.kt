package com.example.artel


import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.SearchView
import com.bumptech.glide.Glide
import com.example.artel.MainFragments.BasketActivity
import com.example.artel.MainFragments.CatalogActivity
import com.example.artel.MainFragments.HomeActivity
import com.example.artel.MainFragments.PersonActivity
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var podsAdapter: SimpleAdapter

    val pods = mutableListOf<HashMap<String, String>>(
        HashMap<String, String>().apply {
            put("Title", "Title1")
            put("Content", "Content1")
        },
        HashMap<String, String>().apply {
            put("Title", "Title2")
            put("Content", "Content2")
        },
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val personFragment = PersonActivity()
        val catalogFragment = CatalogActivity()
        val basketFragment = BasketActivity()
        val homeFragment = HomeActivity()

        /*val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)*/



        changeScreen(homeFragment)

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> changeScreen(homeFragment)
                R.id.catalogue -> changeScreen(catalogFragment)
                R.id.person -> changeScreen(personFragment)
                R.id.basket -> changeScreen(basketFragment)


            }
            true
        }


        initViews()


        val title = intent.getStringExtra("title")
        val imgPath = intent.getStringExtra("img_path")


    }

    fun changeScreen(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()

        }


    fun initViews() {
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Введите название продукта"
        searchView.setOnQueryTextListener(MyQueryTextListener)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    object MyQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            // Обработка события нажатия на кнопку "поиск" на клавиатуре
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            // Обработка изменения текста поискового запроса
            return false
        }
    }

}






