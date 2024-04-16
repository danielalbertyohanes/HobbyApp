package com.example.hobbyapp.model

import com.google.gson.annotations.SerializedName

data class News(var id:String?,
                @SerializedName("Author")
                var author:String?,
                var title:String?,
                var photo_url:String?,
                var sub_title:String?,
                var deskripsi_singkat:String?,
                var content:String?)
