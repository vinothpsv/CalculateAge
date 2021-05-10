package com.vinoth.alivecor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.vinoth.alivecor.fragment.FragmentA

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.activity_main))

        supportFragmentManager.commit {
            add<FragmentA>(R.id.mainActivityFragment)
        }
    }
}