package com.veys.retrofitkotlin.service

import com.veys.retrofitkotlin.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {


    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //fun getData(): Observable<List<CryptoModel>> // observable verileri alan ve veriler geldkten sonra paylaşım yapan gözlemlenebilir obejedir
    //fun getData():Call<List<CryptoModel>>
    suspend fun getData(): Response<List<CryptoModel>>

}