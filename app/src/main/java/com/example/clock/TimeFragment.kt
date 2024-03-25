package com.example.clock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clock.databinding.TimeFragmentBinding

class TimeFragment: Fragment() {
    private var _vb: TimeFragmentBinding? = null
    private val vb get() =  _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = TimeFragmentBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}