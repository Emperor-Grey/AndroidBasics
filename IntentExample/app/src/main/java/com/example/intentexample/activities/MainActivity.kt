package com.example.intentexample.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentexample.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val inputText = findViewById<EditText>(R.id.nameInput)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val name = inputText.text.toString()
//            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            val i = Intent(this@MainActivity, SecondActivity::class.java)
            i.putExtra("name", name)
            startActivity(i)
        }
    }
}
