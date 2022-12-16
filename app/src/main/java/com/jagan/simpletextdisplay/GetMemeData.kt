package com.jagan.simpletextdisplay

import com.jagan.simpletextdisplay.memeDataPackage.MemeData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val baseUrl = "https://api.imgflip.com/"
interface GetMemeData {
    @GET(value = "get_memes")
    fun getMemeInfo(): Call<MemeData>
}

object MemeDataInstance{
    val getMemeData:GetMemeData
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getMemeData = retrofit.create(GetMemeData::class.java)
    }
}