package com.example.clock.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clock.adapter.StringAdapter
import com.example.clock.databinding.StringListBinding
import com.example.clock.viewholder.StringItemViewHolder

class ListDialog(context: Context, private val listener: StringItemViewHolder.OnItemClickListener) : Dialog(context) {
    private val vb by lazy {
        StringListBinding.inflate(LayoutInflater.from(context))
    }

    private val stringAdapter by lazy {
        StringAdapter(listener, this)
    }


    private fun initView() {
        vb.rvList.apply {
            adapter = stringAdapter
            layoutManager = LinearLayoutManager(context)
        }
        window?.decorView?.viewTreeObserver?.addOnGlobalLayoutListener { adjustDialog(context) }
    }

    fun setList(list: List<String>) {
        stringAdapter.submitList(list)
    }

    private fun adjustDialog(context: Context) {
        window?.run {
            setGravity(Gravity.CENTER)

            val lp = attributes

            val dm = context.resources.displayMetrics
            val maxWidth = (dm.widthPixels * 0.80).toInt()
            val maxHeight = (dm.heightPixels * 0.70).toInt()

            if (decorView.width > maxWidth) {
                lp.width = maxWidth
            }
            if (decorView.height > maxHeight) {
                lp.height = maxHeight
            }
            attributes = lp
        }
    }

    init {
        setContentView(vb.root)
        initView()
    }
}