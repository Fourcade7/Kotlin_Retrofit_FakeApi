package com.pr7.kotlin_retrofit_fakeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.pr7.kotlin_retrofit_fakeapi.databinding.ActivityMain3Binding
import com.pr7.kotlin_retrofit_fakeapi.model.response.ProductModelItem
import com.pr7.kotlin_retrofit_fakeapi.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.apply {
           buttonrequestid.setOnClickListener {
               if (edittextproductid.text.isNotEmpty()){

                   //getproductwithid(edittextproductid.text.toString().toInt())
                   getproductwithlimit(edittextproductid.text.toString().toInt())

               }else{
                   edittextproductid.setError("Error")
               }
           }
       }



    }

    fun getproductwithlimit(limit:Int){
        val call:Call<ArrayList<ProductModelItem>> =RetrofitInstance.api.getproductsLimit(limit)
        call.enqueue(object :Callback<ArrayList<ProductModelItem>> {
            override fun onResponse(
                call: Call<ArrayList<ProductModelItem>>,
                response: Response<ArrayList<ProductModelItem>>
            ) {
               if (response.isSuccessful){
                   val arrayList:ArrayList<ProductModelItem> =response.body()!!
                   Log.d("PR77777", "onResponse: ${arrayList.size}")
               }

            }

            override fun onFailure(call: Call<ArrayList<ProductModelItem>>, t: Throwable) {

            }
        })
    }

    fun getproductwithid(id:Int){
        binding.progressbarproductwithid.visibility=View.VISIBLE
        binding.linearlaywithid.visibility=View.GONE


        val call: Call<ProductModelItem> = RetrofitInstance.api.getproductwithpid(id)
        call.enqueue(object : Callback<ProductModelItem> {
            override fun onResponse(
                call: Call<ProductModelItem>,
                response: Response<ProductModelItem>
            ) {
                if (response.isSuccessful){

                    val productModelItem: ProductModelItem =response.body()!!
                    binding.apply {
                        binding.progressbarproductwithid.visibility=View.GONE
                        linearlaywithid.visibility=View.VISIBLE
                        textviewopenpreviewname3.text=productModelItem.title
                        textviewopenpreviewdescription3.text=productModelItem.description
                        textviewopenpreviewprice3.text=productModelItem.price.toString()
                        raitingbar3.rating=productModelItem.rating.rate.toFloat()
                        Glide.with(this@MainActivity3).load(productModelItem.image).into(imageviewopenpreview3)
                    }
                }else{
                    binding.progressbarproductwithid.visibility=View.GONE
                    binding.linearlaywithid.visibility=View.GONE


                }
            }

            override fun onFailure(call: Call<ProductModelItem>, t: Throwable) {

            }
        })
    }
}