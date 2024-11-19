package com.example.booking_listview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.example.booking_listview.R.id.txtLinksignup
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtLinksignup = findViewById<TextView>(R.id.txtLinksignup)

        txtLinksignup.setOnClickListener{
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            if (validLogin()) {
                val queue = Volley.newRequestQueue(this)
                val url = "http://10.0.2.2:8080/api/v1/users/login"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener<String> { response ->
                        Log.d("LOGIN", response)
                        println("Response is: $response")
                        Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show()

                            val edtLoginEmail = findViewById<EditText>(R.id.edtLoginEmail)
                            val emailSession = edtLoginEmail.text.toString()

                            val sharedPreferences = getSharedPreferences("emailSession", MODE_PRIVATE);
                            val editor = sharedPreferences.edit();
                            editor.putString("session_email", emailSession);
                            editor.apply()

                            val intent = Intent(this, Mainpage::class.java)
                            startActivity(intent);
                            finish()
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        Toast.makeText(this, "Login failed with error: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String,String>();

                        val edtLoginEmail = findViewById<EditText>(R.id.edtLoginEmail);
                        val edtLoginPass = findViewById<EditText>(R.id.edtLoginPass);

                        params["param_email"] = edtLoginEmail.text.toString();
                        params["param_password"]= edtLoginPass.text.toString();

                        return params;
                    }
                }
                queue.add(stringRequest);
            }
        }

    }

    private fun validLogin(): Boolean {
        val edtLoginEmail = findViewById<EditText>(R.id.edtLoginEmail)
        val edtLoginPass = findViewById<EditText>(R.id.edtLoginPass)

        val email = edtLoginEmail.text.toString()
        val pass = edtLoginPass.text.toString()

        var validLogin = true

        if (email.isEmpty()){
            edtLoginEmail.error = "Please fill your email!"
            validLogin = false
        } else {
            edtLoginEmail.error = null
        }

        if (pass.isEmpty()){
            edtLoginPass.error = "Please fill your password!"
            validLogin = false
        } else {
            edtLoginPass.error = null
        }

        return validLogin
    }
}