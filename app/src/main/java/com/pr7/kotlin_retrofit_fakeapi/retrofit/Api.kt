package com.pr7.kotlin_retrofit_fakeapi.retrofit

import com.pr7.kotlin_retrofit_fakeapi.model.ProductModelItem
import retrofit2.Call
import retrofit2.http.GET

interface Api {


    //https://fakestoreapi.com/products
    @GET("products")
    fun getAllProducts():Call<ArrayList<ProductModelItem>>


}