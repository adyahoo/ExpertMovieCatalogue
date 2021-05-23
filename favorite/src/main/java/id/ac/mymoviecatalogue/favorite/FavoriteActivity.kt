package id.ac.mymoviecatalogue.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.EntryPointAccessors
import id.ac.mymoviecatalogue.di.FavoriteModuleDependencies
import id.ac.mymoviecatalogue.core.ui.MovieAdapter
import id.ac.mymoviecatalogue.favorite.databinding.ActivityFavoriteBinding
import id.ac.mymoviecatalogue.favorite.di.DaggerFavoriteComponent
import id.ac.mymoviecatalogue.presentation.detail.DetailActivity
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var movieAdapter: MovieAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite Movie Section"

        binding.progbarFav.visibility = View.VISIBLE
        movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, selectedData.movieId)
            startActivity(intent)
        }

        viewModel.getFavoriteMovies().observe(this, { favMovies ->
            if (favMovies != null) {
                binding.progbarFav.visibility = View.GONE
                movieAdapter.setData(favMovies)
            }
        })

        with(binding.rvFav) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}