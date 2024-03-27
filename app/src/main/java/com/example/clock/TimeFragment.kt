package com.example.clock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.clock.databinding.TimeFragmentBinding
import com.example.clock.dialog.ListDialog
import com.example.clock.extension.getNavGraphViewModel
import com.example.clock.viewholder.StringItemViewHolder
import com.example.clock.viewmodel.TimeFragmentViewModel

class TimeFragment : Fragment() {
    private var _vb: TimeFragmentBinding? = null
    private val vb get() = _vb!!

    private lateinit var vm: TimeFragmentViewModel

    protected var navController: NavController? = null

    private val dialog by lazy {
        context?.let { context ->
            ListDialog(context, object : StringItemViewHolder.OnItemClickListener {
                override fun onItemClick(text: String) {
                    //call vm to change language
                }
            }).apply {
                setList(listOf("en", "zh-TW"))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm =
            getNavGraphViewModel(R.id.main_nav) { TimeFragmentViewModel(activity?.application as App) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = TimeFragmentBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListener()
        navController = findNavController()
    }

    private fun initView() {
        vb.tvSortStyle.text = vm.getSortType()
    }

    private fun setListener() {
        vb.tvLanCate.setOnClickListener { tvLanCateOnClick() }
    }

    private fun tvLanCateOnClick() {
        openLanDialog()
    }

    private fun openLanDialog() {
        dialog?.run {
            if (!isShowing) {
                dialog?.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}