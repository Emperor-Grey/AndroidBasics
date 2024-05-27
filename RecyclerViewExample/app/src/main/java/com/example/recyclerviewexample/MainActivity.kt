package com.example.recyclerviewexample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.adapters.ExampleRecyclerAdapter
import com.example.recyclerviewexample.models.ExampleList

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
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.exampleList)

        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        // Data Source
        val exampleList: ArrayList<ExampleList> = ArrayList()
        for (i in 0..20) {
            val example = ExampleList(
                title = "Example Title $i",
                description = "Example description cuz i don't know what to say but i will keep on writing because i love typing",
                image = R.drawable.ic_launcher_foreground
            )
            exampleList.add(example)
        }

        // setting up the adapter
        recyclerView.adapter = ExampleRecyclerAdapter(exampleList = exampleList)
    }
}
