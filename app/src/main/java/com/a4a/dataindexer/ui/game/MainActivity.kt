package com.a4a.dataindexer.ui.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import com.a4a.dataindexer.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
    }

    private fun initializeUi() {
        supportFragmentManager.transaction{replace(R.id.fragment_holder,ListGameFragment())}
    }
}
