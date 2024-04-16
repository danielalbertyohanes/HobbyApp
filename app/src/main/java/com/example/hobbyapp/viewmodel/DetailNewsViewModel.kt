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

class DetailNewsViewModel (application: Application): AndroidViewModel(application) {
    val newsLD = MutableLiveData<News>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    fun getNewsDetail(newsId: String) {
        queue = Volley.newRequestQueue(getApplication())
        //val url = "http://adv.jitusolution.com/news.php?id=$newsId" // Ubah URL sesuai dengan endpoint yang benar
        val url = "http://10.0.2.2/news.php?id=$newsId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("show_volleyDetail", response)
                val result = Gson().fromJson<News>(response, News::class.java)
                newsLD.value = result
            },
            { error ->
                Log.d("show_volleyDetail", error.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

}