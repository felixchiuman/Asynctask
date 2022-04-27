package com.felix.asynctask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.felix.asynctask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressAsyncTask: ProgressAsyncTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        disableView(binding.btnStop, true)

        binding.btnStart.setOnClickListener {
            binding.progress.max = 60
            progressAsyncTask.execute(60)
        }

        binding.btnStop.setOnClickListener {
            progressAsyncTask.cancel(true)
        }
    }

    fun disableView(view : View, isDisabled : Boolean){
        if (isDisabled) {
            view.alpha = 0.5f
            view.isEnabled = false
        } else {
            view.alpha = 1f
            view.isEnabled = true
        }
    }

    inner class ProgressAsyncTask : AsyncTask<Int, Int, Void>(){
        override fun doInBackground(vararg p0: Int?): Void? {
            if (p0.isNotEmpty()){
                if (p0[0]!= null){
                    for(i in 0..p0[0]!!){
                        publishProgress(i)
                    }
                }
            }
            return null
        }

        override fun onPreExecute() {
            disableView(binding.btnStart,true)
            disableView(binding.btnStop,false)
            super.onPreExecute()
        }

        override fun onProgressUpdate(vararg values: Int?) {
            if (values.isNotEmpty() && values[0] != null)
            binding.progress.progress = values[0]!!
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Void?) {
            disableView(binding.btnStart,false)
            disableView(binding.btnStop,true)
            super.onPostExecute(result)
        }

        override fun onCancelled() {
            disableView(binding.btnStart,false)
            disableView(binding.btnStop,true)
            super.onCancelled()
        }
    }
}