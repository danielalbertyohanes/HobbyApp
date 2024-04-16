package com.example.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.model.News
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListNewsViewModel(application: Application): AndroidViewModel(application) {
    val listNewsLD = MutableLiveData<ArrayList<News>>()
    val newsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun getNews() {

        newsLoadErrorLD.value = false
        loadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        //val url = "http://adv.jitusolution.com/news.php" // Ubah url sesuai dengan endpoint yang benar
        val url = "http://10.0.2.2/news.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("show_volley", response)

                val sType = object : TypeToken<List<News>>() {}.type
                val result = Gson().fromJson<List<News>>(response, sType)
                listNewsLD.value = result as ArrayList<News>?
                loadingLD.value = false
            },
            { error ->
                Log.d("show_volley", error.toString())
                newsLoadErrorLD.value = true
                loadingLD.value = false
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

}