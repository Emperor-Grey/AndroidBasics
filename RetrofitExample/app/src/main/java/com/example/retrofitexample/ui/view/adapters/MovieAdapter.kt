package com.example.retrofitexample.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitexample.R
import com.example.retrofitexample.data.model.MovieResponse

class MovieAdapter(
    private val context: Context,
    private val onItemClickListener: (MovieResponse) -> Unit,
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movieList: MutableList<MovieResponse> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        holder.title.text = movie.title
        Glide.with(context).load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .error(R.drawable.error_img).into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClickListener(movie)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateMovies(newMovieList: List<MovieResponse>) {
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movieHeading)
        val image: ImageView = itemView.findViewById(R.id.movieImage)
    }
}
