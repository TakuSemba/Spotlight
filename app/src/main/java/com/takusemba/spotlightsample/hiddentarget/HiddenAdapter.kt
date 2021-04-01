package com.takusemba.spotlightsample.hiddentarget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.takusemba.spotlightsample.R

class HiddenAdapter<T> : ListAdapter<T, HiddenViewHolder>(HiddenDiffUtil()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiddenViewHolder {
    val context = parent.context
    val view = LayoutInflater.from(context).inflate(R.layout.hidden_item, parent, false)
    return HiddenViewHolder(view)
  }

  override fun onBindViewHolder(holder: HiddenViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}

class HiddenDiffUtil<T> : DiffUtil.ItemCallback<T>() {
  override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
    return false
  }

  override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
    return false
  }
}
