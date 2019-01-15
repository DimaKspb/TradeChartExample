package com.dima.tradechart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dima.tradechart.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttun1.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                surface?.initRandomData()
            }
        }
//        for (i in 0..10) {
//            Thread.sleep(1000)
//            Log.d("Thread", "runing")
//        }
    }
}
