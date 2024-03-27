package com.example.clock.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.clock.databinding.StringItemBinding
import com.example.clock.dialog.ListDialog
import timber.log.Timber

class StringItemViewHolder(private val vb: StringItemBinding, private val listener: OnItemClickListener, private val dialog: ListDialog? = null) : RecyclerView.ViewHolder(vb.root) {

    interface OnItemClickListener {
        fun onItemClick(text: String)
    }

    fun bindView(text: String) {
        Timber.d("text $text")
        vb.tvText.text = text
        vb.root.setOnClickListener {
            dialog?.dismiss()
            listener.onItemClick(text)
        }
    }
}