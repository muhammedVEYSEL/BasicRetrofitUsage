package com.veys.retrofitkotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.veys.retrofitkotlin.R
import com.veys.retrofitkotlin.adapter.RecycleAdapter
import com.veys.retrofitkotlin.databinding.ActivityMainBinding
import com.veys.retrofitkotlin.model.CryptoModel
import com.veys.retrofitkotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private lateinit var cryptoAdapter : RecycleAdapter
    //private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var job: Job

    var exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}") // coroutine içinde hata olduğu zaman nedenini gösterir
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        job = Job()
       // compositeDisposable = CompositeDisposable()
        loadData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())//verilerin düzgün şekilde gelmesi için
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = retrofit.getData()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoModels?.let {
                            cryptoAdapter = RecycleAdapter(cryptoModels!!)
                            binding.recyclerView.adapter = cryptoAdapter
                        }
                    }
                }
            }
        }





        /*
        // RXJAVA ile veri çekmek
        compositeDisposable.add(retrofit.getData()
            .subscribeOn(Schedulers.io()) // verileri çekme işlemi nerede yapılıcak (veriye nerede abone olucaz)
            .observeOn(AndroidSchedulers.mainThread()) // nerede gözleneği
            .subscribe(this::handleResponse))
        */




        /*
        // API içinde call olduğu versiyon
        //val service = retrofit.create(CryptoAPI::class.java)
        //val call = service.getData()

        retrofit.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(p0: Call<List<CryptoModel>>, p1: Response<List<CryptoModel>>) {
                if(p1.isSuccessful){
                    p1.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoAdapter = RecycleAdapter(cryptoModels)
                        binding.recyclerView.adapter = cryptoAdapter
                    }
                }
            }
            override fun onFailure(p0: Call<List<CryptoModel>>, p1: Throwable) {
               p1.printStackTrace()
            }
        })*/


    }

    fun handleResponse(cryptomodel : List<CryptoModel>){
        cryptoModels = ArrayList(cryptomodel)
        cryptoModels?.let {
            cryptoAdapter = RecycleAdapter(cryptoModels!!)
            binding.recyclerView.adapter = cryptoAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //compositeDisposable.clear()
        job.cancel()
    }
}