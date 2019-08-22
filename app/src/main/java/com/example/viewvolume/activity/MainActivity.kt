package com.example.viewvolume.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewvolume.R
import com.example.viewvolume.view.ComboSpinner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_get.setOnClickListener {
            button_get.text = ComboSpinner(this, null).getVolume().toString()
        }

        button_set.setOnClickListener {
            ComboSpinner(this, null).setVolume(33f)
        }


    }
}
