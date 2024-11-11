package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.signup)

        val btnSignupconfirm = findViewById<Button>(R.id.btnSignupconfirm)
        val txtLinkLogin = findViewById<TextView>(R.id.txtLinklogin)

        // Navigate to login page
        txtLinkLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // Handle signup button click
        btnSignupconfirm.setOnClickListener {
            if (validField()) {
                val intent = Intent(this, MainActivity::class.java)
                val queue = Volley.newRequestQueue(this)

                val url: String = "http://10.0.2.2:8080/api/v1/users/register"
                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener<String> { response ->
                        println("Response is: $response")
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        Toast.makeText(this, "Register failed with error: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()

                        val edtSignupName = findViewById<EditText>(R.id.edtSignupName)
                        val edtSignupEmail = findViewById<EditText>(R.id.edtSignupEmail)
                        val edtSignAddress = findViewById<EditText>(R.id.edtSignupAdress)
                        val edtSignupPass = findViewById<EditText>(R.id.edtSignupPassword)
                        val edtSignupPassAgain = findViewById<EditText>(R.id.edtSignupPassAgain)

                        var gender: String = "0" // Default to "Male"
                        val rdbGender = findViewById<RadioGroup>(R.id.rdbGender)
                        rdbGender.setOnCheckedChangeListener { group, checkedId ->
                            val selectedRdb = findViewById<RadioButton>(checkedId)
                            gender = if (selectedRdb.text.toString() == "Female") "1" else "0"
                        }

                        val name = edtSignupName.text.toString()
                        val email = edtSignupEmail.text.toString()
                        val address = edtSignAddress.text.toString()
                        val pass = edtSignupPass.text.toString()
                        val passAgain = edtSignupPassAgain.text.toString()

                        params["param_email"] = email
                        params["param_name"] = name
                        params["param_password"] = pass
                        params["param_role"] = "User"
                        params["param_address"] = address
                        params["param_sex"] = gender

                        return params
                    }
                }
                queue.add(stringRequest)
                startActivity(intent)
            }
        }
    }

    // Function to validate form fields
    public fun validField(): Boolean {
        val edtSignupName = findViewById<EditText>(R.id.edtSignupName)
        val edtSignupEmail = findViewById<EditText>(R.id.edtSignupEmail)
        val edtSignupPass = findViewById<EditText>(R.id.edtSignupPassword)
        val edtSignupPassAgain = findViewById<EditText>(R.id.edtSignupPassAgain)

        val name = edtSignupName.text.toString()
        val email = edtSignupEmail.text.toString()
        val pass = edtSignupPass.text.toString()
        val passagain = edtSignupPassAgain.text.toString()

        var isValid = true

        if (name.isEmpty()) {
            edtSignupName.error = "Name cannot be empty"
            isValid = false
        } else {
            edtSignupName.error = null
        }

        if (email.isEmpty()) {
            edtSignupEmail.error = "Email cannot be empty"
            isValid = false
        } else {
            edtSignupEmail.error = null
        }

        if (pass != passagain ){
            edtSignupPassAgain.error = "Password is not match"
            isValid = false
        } else {
           edtSignupPass.error = null
           edtSignupPassAgain.error = null
        }

        return isValid
    }
}
