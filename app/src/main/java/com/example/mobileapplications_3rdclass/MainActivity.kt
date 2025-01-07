package com.example.mobileapplications_3rdclass

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private var fragmentFlag = true
    private val button: MaterialButton
        get() = findViewById(R.id.main_button_change_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, FragmentA())
                .commit()
        }

        button.setOnClickListener {
            val frag = if (fragmentFlag) FragmentB() else FragmentA()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, frag)
                .commit()
            fragmentFlag = !fragmentFlag

        }
    }
}