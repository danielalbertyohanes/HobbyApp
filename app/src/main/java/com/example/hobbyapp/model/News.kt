package com.example.hobbyapp.model

import com.google.gson.annotations.SerializedName

data class News(var id:String?,
                @SerializedName("student_name")
                var name:String?,
                @SerializedName("birth_of_date")
                var dob:String?,
                var phone:String?,
                @SerializedName("photo_url")
                var photoUrl:String?)
