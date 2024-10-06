package com.veys.retrofitkotlin.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
   // @SerializedName("currency")// netten gelecek olan parametre yazılır ve altındaki değişkene eşitlenmesi sağlanır
    val currency: String,

    //@SerializedName("price")
    val price:String
)