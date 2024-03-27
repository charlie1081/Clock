package com.example.clock

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.clock.databinding.MainActivityBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var vb: MainActivityBinding

    private lateinit var navController: NavController

    private val navListener = NavigationBarView.OnItemSelectedListener { item ->

        when (item.itemId) {
            vb.bnvMainNav.selectedItemId -> {
                return@OnItemSelectedListener true
            }

            R.id.time -> {
                navController.navigate(R.id.actionToTimeFragment)
            }

            R.id.setting -> {
                navController.navigate(R.id.actionToSettingFragment)
            }

            else -> {
                return@OnItemSelectedListener true
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setListener()
    }

    private fun initView() {
        vb = MainActivityBinding.inflate(layoutInflater)
        setContentView(vb.root)
        navController =
            (supportFragmentManager.findFragmentById(R.id.mainFragment) as NavHostFragment).navController
    }

    private fun setListener() {
        vb.bnvMainNav.setOnItemSelectedListener(navListener)
        onBackPressedDispatcher.addCallback {
            if(isTaskRoot) {
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.main_nav).navigateUp()
    }
}