package com.businesstinder.tinder.Chat

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.businesstinder.tinder.R

/**
 * Created by manel on 10/31/2017.
 */
class ChatViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var mMessage: TextView
    var mContainer: LinearLayout
    override fun onClick(view: View) {}

    init {
        itemView.setOnClickListener(this)
        mMessage = itemView.findViewById(R.id.message)
        mContainer = itemView.findViewById(R.id.container)
    }
}