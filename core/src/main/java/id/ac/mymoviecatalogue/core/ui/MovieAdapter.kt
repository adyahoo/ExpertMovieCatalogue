package id.ac.mymoviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.mymoviecatalogue.core.BuildConfig
import id.ac.mymoviecatalogue.core.R
import id.ac.mymoviecatalogue.core.databinding.ItemDataBinding
import id.ac.mymoviecatalogue.core.domain.Movie

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(movieList: List<Movie>?) {
        if (movieList == null) return
        listData.clear()
        listData.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val moviePoster = BuildConfig.IMAGE_BASE_URL+movie.poster
            with(binding) {
                tvTitle.text = movie.title
                tvDate.text = itemView.context.getString(R.string.release_date_rv, movie.releaseDate)
                Glide.with(itemView.context)
                    .load(moviePoster)
                    .into(civPoster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}