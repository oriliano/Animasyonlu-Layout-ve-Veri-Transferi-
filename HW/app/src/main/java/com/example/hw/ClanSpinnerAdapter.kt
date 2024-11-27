package com.example.hw

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ClanSpinnerAdapter(
    context: Context,
    private val clans: List<Clan>
) : ArrayAdapter<Clan>(context, 0, clans) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_clan_spinner, parent, false)
        val clan = getItem(position)

        val imageView = itemView.findViewById<ImageView>(R.id.clanImage)
        val textView = itemView.findViewById<TextView>(R.id.clanName)

        clan?.let {
            imageView.setImageResource(it.imageRes)
            textView.text = it.name
        }

        return itemView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
