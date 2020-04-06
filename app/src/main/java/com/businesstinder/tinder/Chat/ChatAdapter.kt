package com.businesstinder.tinder.Chat

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.businesstinder.tinder.R

/**
 * Created by manel on 10/31/2017.
 */
class ChatAdapter(private val chatList: List<ChatObject>, private val context: Context) : RecyclerView.Adapter<ChatViewHolders>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolders {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, null, false)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutView.layoutParams = lp
        return ChatViewHolders(layoutView)
    }

    override fun onBindViewHolder(holder: ChatViewHolders, position: Int) {
        holder.mMessage.text = chatList[position].message
        if (chatList[position].currentUser) {
            holder.mMessage.gravity = Gravity.END
            holder.mMessage.setTextColor(Color.parseColor("#404040"))
            holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"))
        } else {
            holder.mMessage.gravity = Gravity.START
            holder.mMessage.setTextColor(Color.parseColor("#FFFFFF"))
            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"))
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

}