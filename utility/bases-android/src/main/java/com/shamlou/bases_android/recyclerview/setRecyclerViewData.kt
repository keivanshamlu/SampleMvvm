package com.shamlou.bases_android.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@Suppress("UNCHECKED_CAST")
@BindingAdapter("binding:setData")
fun <T> RecyclerView.setRecyclerViewData(data: List<T>?) {
        data?.let {
            when(adapter){
                is AdapterBase<*> -> (adapter as AdapterBase<T>).submitList(it)
                else -> {}
            }
    }
}