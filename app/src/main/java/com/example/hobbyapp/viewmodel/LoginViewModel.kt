package com.example.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.model.News

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginSuccessLD = MutableLiveData<Boolean>().apply { false }
    val loginErrorLD = MutableLiveData<String>()

    //val loginSuccessLD = MutableLiveData<Boolean>().apply { value = false }

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun loginUser(username: String, password: String) {
        loginSuccessLD.value = false
        queue = Volley.newRequestQueue(getApplication())
       //val url = "http://your-web-service-url/login.php"
        val url = "http://10.0.2.2/login.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")
                loginSuccessLD.value=true
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Error: ${error.message}")
                loginSuccessLD.value=false
                loginErrorLD.value = "Login failed: ${error.message}"
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
