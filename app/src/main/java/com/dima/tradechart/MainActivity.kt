package com.dima.tradechart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttun1.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    delay(1500)
                    svChart.initRandomData()
                }
            }
        }

        btnLeft.setOnClickListener { }
        btnRight.setOnClickListener {}
    }
}
