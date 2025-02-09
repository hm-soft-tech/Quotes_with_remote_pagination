package com.example.mks.quotes_app_with_pagination_in_kotlin.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mks.quotes_app_with_pagination_in_kotlin.R
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.presentation.interfaces_.Images_interface


class Imageadapter(private val context: Context,private val imagesInterface: Images_interface) :PagingDataAdapter<Images,Imageadapter.myholder>(
    Comparator) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myholder {
       val view = LayoutInflater.from(context).inflate(R.layout.custom_layout,parent,false)
       return myholder(view)

    }

    override fun onBindViewHolder(holder: myholder, position: Int) {

        val data = getItem(position)
          data?.let {
              Glide.with(context).load(data.imageurl).into(holder.imageview)
              holder.imageview.setOnClickListener {
                  imagesInterface.move_to_nextactivity(data.imageurl)
              }
               }




    }

    class myholder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageview:ImageView =itemView.findViewById(R.id.image)



    }

    companion object {
   private val Comparator = object : DiffUtil.ItemCallback<Images>() {
       override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
           return oldItem.id == newItem.id
       }

       override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem == newItem
       }


   }








}}