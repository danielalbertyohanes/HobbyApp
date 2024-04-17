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

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginSuccessLD = MutableLiveData<Boolean>()

    private val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun loginUser(username: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/login.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")

                val jsonObject = JSONObject(response)
                val message = jsonObject.optString("message", "")

                if (message == "Incorrect password" || message == "No user found" || message == "Some input is empty") {
                    //Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                    loginSuccessLD.value = false
                } else {
                    loginSuccessLD.value = true
                }
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Error: ${error.message}")
                loginSuccessLD.value = false
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
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
