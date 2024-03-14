package com.example.artel.Registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.artel.EtoBaza.EndPoint
import com.example.artel.EtoBaza.VolleySingleton
import com.example.artel.MainActivity
import com.example.artel.R
import com.example.artel.URL.Companion.url2
import org.json.JSONException
import org.json.JSONObject
import java.util.zip.Inflater

class RegistrationActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var editTextSecondName: EditText
    lateinit var editTextUserName: EditText
    lateinit var editTextPassword: EditText
    lateinit var btnRegister: Button
    lateinit var redirectLogin:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_main)


        editTextName = findViewById(R.id.etSName)
        editTextSecondName = findViewById(R.id.etSSecondName)
        editTextUserName = findViewById(R.id.etSUserName)
        editTextPassword = findViewById(R.id.etSPassword)
        redirectLogin = findViewById(R.id.tvRedirectLogin)

        btnRegister = findViewById(R.id.btnReggi)
        btnRegister.setOnClickListener {
            addUser()
        }

        redirectLogin.setOnClickListener{
            val intent = Intent(this,LoginActivity ::class.java)
            startActivity(intent)
        }

    }

    //new record to database
    private fun addUser() {

        val URL_ROOT = url2
        var URL_CREATE_USER = URL_ROOT + "?op=createuser"

        val name = editTextName.text.toString()
        val secondName = editTextSecondName.text.toString()
        val userName = editTextUserName.text.toString()
        val password = editTextPassword.text.toString()

        Log.d("RegistrationActivity", "Sending request to server: $URL_CREATE_USER")

        val stringRequest = object : StringRequest(
            Method.POST, URL_CREATE_USER,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                    if (obj.getString("message") == "User addedd successfully") {
                        intentie()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyerror ->
                Log.e("RegistrationActivity", "Error while making request: ${volleyerror?.message}")
                Toast.makeText(
                    applicationContext,
                    volleyerror?.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            //@Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                Log.d("RegistrationActivity", "Data: $URL_CREATE_USER")
                params.put("name", name)
                params.put("second_name", secondName)
                params.put("user_name", userName)
                params.put("password", password)

                return params
            }
        }

        //add request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun intentie() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}

