package com.pr7.kotlin_retrofit_fakeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pr7.kotlin_retrofit_fakeapi.model.response.ProductModelItem
import com.pr7.kotlin_retrofit_fakeapi.retrofit.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var productAdapter: ProductAdapter
    lateinit var progressBar: ProgressBar
    var arraylist=ArrayList<ProductModelItem>()
    lateinit var swipeRefresh:SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.recyclerview)
        progressBar=findViewById(R.id.progressbar1)
        swipeRefresh=findViewById(R.id.swiperefresh)


        swipeRefresh.setOnRefreshListener {
            Log.i("PR77777", "onRefresh called from SwipeRefreshLayout")

        }


    }





    fun getAllProducts(){
        val call:Call<ArrayList<ProductModelItem>> = api.getAllProducts()
        progressBar.visibility= View.VISIBLE
        call.enqueue(object :Callback<ArrayList<ProductModelItem>>{
            override fun onResponse(
                call: Call<ArrayList<ProductModelItem>>,
                response: Response<ArrayList<ProductModelItem>>
            ) {

                if (response.isSuccessful){
                    progressBar.visibility= View.INVISIBLE
                    arraylist=response.body()!!
                    Log.d("PR77777", "onResponse: ${response.body()?.get(0)?.title}")
                    recyclerView.layoutManager=GridLayoutManager(this@MainActivity,3)
                    productAdapter= ProductAdapter(this@MainActivity,arraylist)
                    recyclerView.adapter=productAdapter

                    swipeRefresh.isRefreshing=false
                }else{
                    progressBar.visibility= View.INVISIBLE
                    swipeRefresh.isRefreshing=false

                }
            }

            override fun onFailure(call: Call<ArrayList<ProductModelItem>>, t: Throwable) {

            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            // Check if user triggered a refresh:
            R.id.menu_refresh -> {
                Log.i("LOG_TAG", "Refresh menu item selected")

                // Signal SwipeRefreshLayout to start the progress indicator
                swipeRefresh.isRefreshing = true

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.


                return true
            }
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item)
    }
}