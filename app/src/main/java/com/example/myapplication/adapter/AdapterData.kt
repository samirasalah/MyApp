package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBinding
import com.example.myapplication.model.ItemApp


/**
 * Created by Samira Salah
 */
private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemApp>() {

    override fun areItemsTheSame(oldItem: ItemApp, newItem: ItemApp) =
      oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ItemApp, newItem: ItemApp
    ) = oldItem == newItem
}
class AdapterData(private val callback: (ItemApp) -> Unit) :PagedListAdapter<ItemApp, AdapterData.ViewHolder>(DIFF_CALLBACK){

    inner class ViewHolder(val binding:ItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ItemApp, position: Int){
            binding.item=item
            binding.position=position
            binding.root.setOnClickListener { callback(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder=
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it,position)
        }


    }

}