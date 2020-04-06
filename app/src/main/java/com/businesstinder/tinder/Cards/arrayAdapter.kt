package com.businesstinder.tinder.Cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.businesstinder.tinder.R

/**
 * Created by manel on 9/5/2017.
 */
class arrayAdapter(context: Context?, resourceId: Int, items: List<cards?>?) : ArrayAdapter<cards?>(context!!, resourceId, items!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val card_item = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false)
        }
        val name = convertView!!.findViewById<View>(R.id.name) as TextView
        val image = convertView.findViewById<View>(R.id.image) as ImageView
        name.text = card_item!!.name
        when (card_item.profileImageUrl) {
            "default" -> Glide.with(convertView.context).load(R.mipmap.ic_launcher).into(image)
            else -> {
                Glide.clear(image)
                Glide.with(convertView.context).load(card_item.profileImageUrl).into(image)
            }
        }
        return convertView
    }
}