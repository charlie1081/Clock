package com.example.clock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clock.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var vb: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = MainActivityBinding.inflate(layoutInflater)
        setContentView(vb.root)
    }
}