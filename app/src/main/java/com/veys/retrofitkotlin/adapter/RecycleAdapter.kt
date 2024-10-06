package com.veys.retrofitkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.veys.retrofitkotlin.databinding.RecycleRowBinding
import com.veys.retrofitkotlin.model.CryptoModel

class RecycleAdapter(val cryptoList : List<CryptoModel>) :RecyclerView.Adapter<RecycleAdapter.CryptoHolder> (){

    private var colors = arrayOf("#ff4500","#6e8b3d","#ccccff","#405e74","#ffb169","#55294c","#0a8270","#354230","#a98772","#d8dece")
    class CryptoHolder(val binding: RecycleRowBinding):RecyclerView.ViewHolder(binding.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val binding = RecycleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.binding.nameText.text = cryptoList.get(position).currency
        holder.binding.priceText.text = cryptoList.get(position).price
//        holder.binding.nameText.setBackgroundColor(Color.parseColor(colors[position % 10]))
//        holder.binding.priceText.setBackgroundColor(Color.parseColor(colors[position % 10]))
        holder.binding.linear.setBackgroundColor(Color.parseColor(colors[position % 10]))



    }
    override fun getItemCount(): Int {
       return cryptoList.size
    }
}