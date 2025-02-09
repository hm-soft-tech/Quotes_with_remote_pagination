package com.example.mks.quotes_app_with_pagination_in_kotlin.data.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mks.quotes_app_with_pagination_in_kotlin.R

class LoadAdapter :LoadStateAdapter<LoadAdapter.myholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): myholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_progress_bar_show_layout,parent,false)
        return myholder(view)
    }


    override fun onBindViewHolder(holder: myholder, loadState: LoadState) {
        holder.bind(loadState)
    }






   class myholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_circular)

       fun bind(loadState: LoadState){
           when(loadState){
               is LoadState.Error -> {
                progressBar.visibility =  View.GONE
               }
               LoadState.Loading -> {
                   progressBar.visibility =  View.VISIBLE
               }
               is LoadState.NotLoading -> {
                   progressBar.visibility =  View.GONE
               }
           }


       }

    }




}