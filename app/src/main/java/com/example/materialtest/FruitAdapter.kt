package com.example.materialtest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.materialtest.databinding.FruitItemBinding

class FruitAdapter(val context: Context,val fruitList:List<Fruit>):RecyclerView.Adapter<FruitAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: FruitItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FruitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit=fruitList[position]
        holder.binding.apply {
            fruitName.text=fruit.name

        }
        Glide.with(context).load(fruit.imageId).into(holder.binding.fruitImage)
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, FruitActivity::class.java).apply {
                putExtra("cnmname", fruit.name)
                putExtra("cnmimageid",fruit.imageId)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = fruitList.size


}