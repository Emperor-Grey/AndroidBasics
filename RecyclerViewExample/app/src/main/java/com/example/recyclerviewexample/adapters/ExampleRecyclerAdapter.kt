package com.example.recyclerviewexample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.R
import com.example.recyclerviewexample.models.ExampleList

class ExampleRecyclerAdapter(private val exampleList: ArrayList<ExampleList>) :
    RecyclerView.Adapter<ExampleRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.example_card_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // All logic related things should go here
        val currentItem = exampleList[position]

        holder.heading.text = currentItem.title
        holder.description.text = currentItem.description
        holder.image.setImageResource(currentItem.image)

        // handling click events
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, currentItem.title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heading: TextView = itemView.findViewById(R.id.heading)
        var description: TextView = itemView.findViewById(R.id.description)
        var image: ImageView = itemView.findViewById(R.id.image)
    }
}
