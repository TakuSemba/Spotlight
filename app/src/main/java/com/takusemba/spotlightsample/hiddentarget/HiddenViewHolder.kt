package com.takusemba.spotlightsample.hiddentarget

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.takusemba.spotlightsample.R

class HiddenViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun <T> bind(item: T) {
    itemView.findViewById<TextView>(R.id.textViewHiddenItemContent).text = (item as HiddenItem).text
  }
}
