package com.shamlou.featureone.ui.posts.posts

import com.shamlou.bases_android.recyclerview.AdapterBase
import com.shamlou.featureone.R
import com.shamlou.featureone.model.posts.ResponsePostView

class AdapterPosts : AdapterBase<ResponsePostView>(
        areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
) {

    override fun getItemViewType(position: Int): Int = R.layout.item_post
}