package com.dima.tradechart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dima.tradechart.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttun1.setOnClickListener {}
        btnLeft.setOnClickListener { }
        btnRight.setOnClickListener {}
    }
}
