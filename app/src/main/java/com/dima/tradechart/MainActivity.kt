package com.dima.tradechart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.text.traderchart.chart.model.data
import com.text.traderchart.chart.series.LineSeries
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
            svChart.setSeries(LineSeries().apply {
                addAllPoint(data())
            })
        }

        btnLeft.setOnClickListener {
            svChart.moveToLeft()
        }
        btnRight.setOnClickListener {}
    }
}
