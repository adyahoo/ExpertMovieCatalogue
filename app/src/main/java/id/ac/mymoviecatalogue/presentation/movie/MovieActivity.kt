package id.ac.mymoviecatalogue.presentation.movie

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ac.mymoviecatalogue.R
import id.ac.mymoviecatalogue.core.ui.MovieAdapter
import id.ac.mymoviecatalogue.core.vo.Status
import id.ac.mymoviecatalogue.databinding.ActivityMainBinding
import id.ac.mymoviecatalogue.presentation.detail.DetailActivity

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, selectedData.movieId)
            startActivity(intent)
        }

        viewModel.getMovies().observe(this, { movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> binding.progbarMovie.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progbarMovie.visibility = View.GONE
                        movieAdapter.setData(movies.data)
                    }
                    Status.ERROR -> {
                        binding.progbarMovie.visibility = View.GONE
                        Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite_section) {
            val uri = Uri.parse("moviecatalogue://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        return super.onOptionsItemSelected(item)
    }
}