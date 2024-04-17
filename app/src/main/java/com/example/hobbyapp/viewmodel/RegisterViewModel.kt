package com.example.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val registerSuccessLD = MutableLiveData<Boolean>()
    private val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun registerUser(username: String, firstName: String, lastName: String, email: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/regis.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")

                val jsonObject = JSONObject(response)
                val message = jsonObject.optString("message", "")

                if (message == "Registration successful") {
                    registerSuccessLD.value = true
                } else {
                    registerSuccessLD.value = false
                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Error: ${error.message}")
                registerSuccessLD.value = false
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["first_name"] = firstName
                params["last_name"] = lastName
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}




