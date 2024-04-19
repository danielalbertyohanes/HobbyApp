package com.example.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.model.News
import org.json.JSONObject

class UpdateViewModel (application: Application): AndroidViewModel(application){
    val passSuccessLD = MutableLiveData<Boolean>()
    val userSuccessLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    fun updateUser(username: String, firstName: String, lastName: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/updateUser.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")
                val jsonObject = JSONObject(response)
                val message = jsonObject.optString("message", "")

                if (message == "Updated successfully" || message == "Failed to update" || message == "Missing parameters") {
                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                    userSuccessLD.value = false
                } else {
                    userSuccessLD.value = true
                }

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
        val url = "http://10.0.2.2/updatePass.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                Log.d(TAG, "Response: $response")
                val jsonObject = JSONObject(response)
                val message = jsonObject.optString("message", "")

                if (message == "Updated successfully" || message == "Failed to update" || message == "Missing parameters") {
                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                    passSuccessLD.value = false
                } else {
                    passSuccessLD.value = true
                }
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