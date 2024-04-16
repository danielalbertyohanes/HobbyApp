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

class UpdateViewModel (application: Application): AndroidViewModel(application){

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    fun updateUser(username: String, firstName: String, lastName: String) {
        queue = Volley.newRequestQueue(getApplication())
        //val url = "http://your-web-service-url/updatePass.php"
        val url = "http://10.0.2.2/updatePass.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Error: ${error.message}")

            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["first_name"] = firstName
                params["last_name"] = lastName
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun updatePass(username: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        //val url = "http://your-web-service-url/updatePass.php"
        val url = "http://10.0.2.2/updatePass.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Error: ${error.message}")
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