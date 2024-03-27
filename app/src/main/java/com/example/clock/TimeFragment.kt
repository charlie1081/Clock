package com.example.clock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clock.adapter.ClockAdapter
import com.example.clock.databinding.TimeFragmentBinding
import com.example.clock.dialog.ListDialog
import com.example.clock.extension.getNavGraphViewModel
import com.example.clock.model.Time
import com.example.clock.viewholder.StringItemViewHolder
import com.example.clock.viewmodel.TimeFragmentViewModel
import kotlinx.coroutines.launch

class TimeFragment : Fragment() {
    private var _vb: TimeFragmentBinding? = null
    private val vb get() = _vb!!

    private lateinit var vm: TimeFragmentViewModel

    private val languageDialog by lazy {
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

    private val sortDialog by lazy {
        context?.let { context ->
            ListDialog(context, object : StringItemViewHolder.OnItemClickListener {
                override fun onItemClick(text: String) {
                    vm.setSortType(text)
                    sortDataThenSubmit(getAdapterCloneList())
                    updateSortText()
                }
            }).apply {
                setList(listOf(resources.getString(R.string.asc_sort), resources.getString(R.string.desc_sort)))
            }
        }
    }

    private val clockAdapter by lazy {
        ClockAdapter()
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
        bindFlowData()
    }

    private fun bindFlowData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.timeResponseFlow.collect {
                    updateRecycleView(vm.convertToTimeData(it))
                }
            }
        }
    }

    private fun updateRecycleView(convertToTimeData: Time) {
        val cloneList = getAdapterCloneList()
        cloneList.add(convertToTimeData)
        sortDataThenSubmit(cloneList)
    }

    private fun sortDataThenSubmit(list: ArrayList<Time>) {
        if (vm.getSortType()) {
            list.sortBy { it.location }
        } else {
            list.sortByDescending { it.location }
        }
        clockAdapter.submitList(list)
    }

    private fun initView() {
        updateSortText()
        vb.rvClock.apply {
            adapter = clockAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        vm.getAllTimezoneTime()
    }

    private fun updateSortText() {
        vb.tvSortStyle.text = if (vm.getSortType()) {
            resources.getString(R.string.asc_sort)
        } else {
            resources.getString(R.string.desc_sort)
        }
    }

    private fun setListener() {
        vb.tvLanCate.setOnClickListener { openLanDialog() }
        vb.tvSortStyle.setOnClickListener { openSortDialog() }
    }

    private fun openSortDialog() {
        sortDialog?.run {
            if (!isShowing) {
                show()
            }
        }
    }

    private fun openLanDialog() {
        languageDialog?.run {
            if (!isShowing) {
                show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    private fun getAdapterCloneList(): ArrayList<Time> {
        val newList = arrayListOf<Time>()
        clockAdapter.currentList.forEach {
            newList.add(
                Time(it.time, it.timeZone, it.location)
            )
        }
        return newList
    }
}