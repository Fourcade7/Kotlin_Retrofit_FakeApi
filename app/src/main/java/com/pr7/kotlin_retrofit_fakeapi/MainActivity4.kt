package com.pr7.kotlin_retrofit_fakeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pr7.kotlin_retrofit_fakeapi.databinding.ActivityMain4Binding
import com.pr7.kotlin_retrofit_fakeapi.model.request.ProductReq
import com.pr7.kotlin_retrofit_fakeapi.model.response.ProductRes
import com.pr7.kotlin_retrofit_fakeapi.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity4 : AppCompatActivity() {
    lateinit var binding: ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonaddproduct.setOnClickListener {
                addproduct(
                    title = edittextproducttitle.text.toString(),
                    price =edittextproductprice.text.toString().toDouble(),
                    description = edittextproductdescription.text.toString(),
                    image = "",
                    category = edittextproductcategory.text.toString()
                )
            }
        }
    }

    fun addproduct(
        title :String,
        price :Double,
        description :String,
        image :String,
        category :String
    ){
        val productReq=ProductReq(
            title = title,
            price = price,
            description = description,
            image = image,
            category = category
        )
        val call:Call<ProductRes> = RetrofitInstance.api.addproduct(productReq)
        call.enqueue(object :Callback<ProductRes>{
            override fun onResponse(call: Call<ProductRes>, response: Response<ProductRes>) {
               if (response.isSuccessful){
                   val productRes=response.body()!!
                   Log.d("PR77777", "onResponse: ${productRes.id}")
               }
            }

            override fun onFailure(call: Call<ProductRes>, t: Throwable) {

            }
        })
    }
}