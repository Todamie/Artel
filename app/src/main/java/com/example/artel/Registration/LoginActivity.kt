package com.example.artel.Registration

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.artel.EtoBaza.VolleySingleton
import com.example.artel.MainActivity
import com.example.artel.R
import com.example.artel.URL.Companion.url2
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    lateinit var logTextUserName: EditText
    lateinit var logTextPassword: EditText
    lateinit var btnLogin: Button
    lateinit var redirectRegister: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logTextUserName = findViewById(R.id.logUserName)
        logTextPassword = findViewById(R.id.logPassword)
        redirectRegister = findViewById(R.id.tvRedirectSignUp)

        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val userName = logTextUserName.text.toString()
            val password = logTextPassword.text.toString()
            if (!userName.isEmpty() || !password.isEmpty()) {
                Login(userName, password)
            } else {
                logTextUserName.setError("Введите имя пользователя")
                logTextPassword.setError("Введите пароль")
            }
        }

        redirectRegister.setOnClickListener{
            val intent = Intent(this,RegistrationActivity ::class.java)
            startActivity(intent)
        }
    }

    //new record to database
    private fun Login(userName: String, password: String) {


        val URL_LOGIN_USER = url2 + "login.php" //Раздача
        //val URL_LOGIN_USER = URL_ROOT + "loginuser"



        Log.d("LoginActicity", "Sending request to server: $URL_LOGIN_USER")

        val stringRequest = object : StringRequest(
            Method.POST, URL_LOGIN_USER,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                    Log.e("gitgut1", response)

                    if (obj.getString("message") == "success"){
                    intentie()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("anyText4", response)
                    Toast.makeText(
                        applicationContext,
                        "Error: $e",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            Response.ErrorListener { volleyerror ->
                Log.e("LoginActivity", "Error while making request: ${volleyerror?.message}")
                Toast.makeText(
                    applicationContext,
                    volleyerror?.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            //@Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                Log.d("LoginActivity", "Data: $URL_LOGIN_USER")
                Log.e("gitgut2", "gitgut2")
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
