package com.eru.service.android.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eru.service.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}