package com.example.openinapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun ChartGraph(
    xData: List<Float>,
    yData: List<Float>,
    dataLabel: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = { context ->
            val chart = LineChart(context)

            val entries: List<Entry> = xData.zip(yData) { x, y -> Entry(x, y) }
            val dataSet = LineDataSet(entries, dataLabel).apply {
                color = Color(0xFF0E6FFF).toArgb()
                setDrawCircles(false)
                setDrawValues(false)
                lineWidth = 2f
                fillColor = Color(0xFF0E6FFF).toArgb()
                setDrawFilled(true)
                fillAlpha = 30
            }

            chart.apply {
                data = LineData(dataSet)
                setTouchEnabled(true)
                isDragEnabled = true
                isScaleXEnabled = true
                isScaleYEnabled = false



                description.isEnabled = false
                legend.isEnabled = false

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textColor = Color(0xFF999CA0).toArgb()
                    setDrawGridLines(true)
                    gridColor = Color(0xFF999CA0).toArgb()
                    valueFormatter = object : ValueFormatter() {
                        private val months = arrayOf(
                            "Jan",
                            "Feb",
                            "Mar",
                            "Apr",
                            "May",
                            "Jun",
                            "Jul",
                            "Aug",
                            "Sep",
                            "Oct",
                            "Nov",
                            "Dec"
                        )

                        override fun getFormattedValue(value: Float): String {
                            return months.getOrElse(value.toInt()) { value.toString() }
                        }
                    }
                }

                axisRight.apply {
                    setDrawAxisLine(false)
                    setDrawGridLines(false)
                    isEnabled = false
                }

                axisLeft.apply {
                    setDrawAxisLine(false)
                    setDrawGridLines(true)
                    textColor = Color(0xFF999CA0).toArgb()
                    gridColor = Color(0xFF999CA0).toArgb()
                }

            }

            chart.invalidate()
            chart
        }
    )
}