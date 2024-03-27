package com.example.clock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clock.adapter.CheckboxAdapter
import com.example.clock.databinding.SettingFragmentBinding
import com.example.clock.dialog.ListDialog
import com.example.clock.extension.getNavGraphViewModel
import com.example.clock.model.CheckboxItem
import com.example.clock.model.Mode
import com.example.clock.viewholder.StringItemViewHolder
import com.example.clock.viewmodel.SettingFragmentViewModel
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {
    private var _vb: SettingFragmentBinding? = null
    private val vb get() = _vb!!

    private lateinit var vm: SettingFragmentViewModel

    private val dialog by lazy {
        context?.let { context ->
            ListDialog(context, object : StringItemViewHolder.OnItemClickListener {
                override fun onItemClick(text: String) {
                    refreshList(text)
                }
            })
        }
    }

    private val checkboxAdapter by lazy {
        CheckboxAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm =
            getNavGraphViewModel(R.id.main_nav) { SettingFragmentViewModel(activity?.application as App) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = SettingFragmentBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        bindFlowData()
        initView()
    }

    private fun initView() {
        vb.rvList.apply {
            adapter = checkboxAdapter
            layoutManager = LinearLayoutManager(context)
            checkboxAdapter.submitList(vm.getAvailableTimeZonesFromPref())
        }
    }

    private fun setListener() {
        vb.tvAdd.setOnClickListener {
            vm.getAvailableTimeZonesFrom()
        }
        vb.tvEdit.setOnClickListener {
            changeToEditMode()
        }
        vb.tvDelete.setOnClickListener {
            changeToDisplayMode()
        }
    }

    private fun changeToDisplayMode() {
        val currentList = getAdapterCloneList()
        val newList = arrayListOf<CheckboxItem>()
        currentList.forEach {
            if (!it.isChecked) {
                newList.add(CheckboxItem(it.timeZone))
            }
        }
        checkboxAdapter.submitList(newList)
        changeModeUI(Mode.Display)
        vm.saveTimezones(newList)
    }

    private fun changeToEditMode() {
        val currentList = getAdapterCloneList()
        currentList.forEach {
            it.mode = Mode.Edit
        }
        checkboxAdapter.submitList(currentList)
        changeModeUI(Mode.Edit)
        vm.saveTimezones(currentList)
    }

    private fun changeModeUI(mode: Mode) {
        when (mode) {
            Mode.Display -> {
                vb.tvEdit.isVisible = true
                vb.tvAdd.isVisible = true
                vb.tvDelete.isVisible = false
            }

            else -> {
                vb.tvEdit.isVisible = false
                vb.tvAdd.isVisible = false
                vb.tvDelete.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    private fun showAvailableTimeZonesDialog(timeZones: List<String>) {
        dialog?.run {
            if (!isShowing) {
                setList(timeZones)
                show()
            }
        }
    }

    private fun refreshList(text: String) {
        val currentList = getAdapterCloneList()
        vm.checkAlreadyAddInList(currentList, text).let { isAddIt ->
            if (isAddIt) {
                showToast()
            } else {
                currentList.add(CheckboxItem(text))
                checkboxAdapter.submitList(currentList)
            }
        }
        vm.saveTimezones(currentList)
    }

    private fun showToast() {
        context?.let { context ->
            Toast.makeText(context, "already add it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindFlowData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.availableTimeZones.collect { it ->
                    showAvailableTimeZonesDialog(it)
                }
            }
        }
    }

    private fun getAdapterCloneList(): ArrayList<CheckboxItem> {
        val newList = arrayListOf<CheckboxItem>()
        checkboxAdapter.currentList.forEach {
            newList.add(
                CheckboxItem(it.timeZone, it.isChecked, it.mode)
            )
        }
        return newList
    }
}