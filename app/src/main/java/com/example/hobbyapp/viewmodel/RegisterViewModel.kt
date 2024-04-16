package com.example.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.model.News
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegisterViewModel(application: Application): AndroidViewModel(application) {
    val registerSuccessLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun registerUser(username: String, firstName: String, lastName: String, email: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        //val url = "http://your-web-service-url/regis.php"
        val url = "http://10.0.2.2/regis.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")
                registerSuccessLD.value=true
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Error: ${error.message}")
                registerSuccessLD.value=false
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