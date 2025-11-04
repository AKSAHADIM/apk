package com.cpustats

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cpuTextView: TextView
    private lateinit var ramTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cpuTextView = findViewById(R.id.cpuTextView)
        ramTextView = findViewById(R.id.ramTextView)

        cpuTextView.text = getCpuInfo()
        ramTextView.text = getRamInfo()
    }

    private fun getCpuInfo(): String {
        // Read /proc/cpuinfo and return as String (simplified)
        return try {
            val process = Runtime.getRuntime().exec("cat /proc/cpuinfo")
            val cpuInfo = process.inputStream.bufferedReader().readText()
            "CPU Info:\n${cpuInfo.lines().take(10).joinToString("\n")}"
        } catch (e: Exception) {
            "Unable to get CPU Info"
        }
    }

    private fun getRamInfo(): String {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val totalMemMB = memoryInfo.totalMem / (1024 * 1024)
        val availMemMB = memoryInfo.availMem / (1024 * 1024)
        return "RAM Info:\nTotal: ${totalMemMB}MB\nAvailable: ${availMemMB}MB"
    }
}
