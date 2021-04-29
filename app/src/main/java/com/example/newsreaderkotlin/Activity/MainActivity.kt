package com.example.newsreaderkotlin.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreaderkotlin.Constants
import com.example.newsreaderkotlin.Constants.Companion.TIME_OF_LAST_REFRESH_SHARED_PREFS
import com.example.newsreaderkotlin.Constants.Companion.TIME_OF_LAST_REFRESH_VALUE
import com.example.newsreaderkotlin.Entity.NewsEntity
import com.example.newsreaderkotlin.RecyclerView.MainActivityAdapter
import com.example.newsreaderkotlin.ViewModel.NewsViewModel
import com.example.newsreaderkotlin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainActivityAdapter
    private val list: MutableList<NewsEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        val toolbar = binding.MainAppBar
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.title = "News"

        val newsViewModel: NewsViewModel by viewModels()

        val recyclerView = binding.MainRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adapter = MainActivityAdapter(applicationContext, list, this)
        recyclerView.adapter = adapter

        val sharedPreferencesBackgroundTerminated =
            getSharedPreferences(TIME_OF_LAST_REFRESH_SHARED_PREFS, MODE_PRIVATE)
        val timeOfLastRefresh = sharedPreferencesBackgroundTerminated.getString(
            TIME_OF_LAST_REFRESH_VALUE,
            null
        )

        newsViewModel.getLiveDataNewsEntity().observe(this, { newsEntityList ->
            binding.MainProgressBar.visibility = View.GONE
            adapter.setRecords(newsEntityList)
        })

        newsViewModel.getThrowableLiveData().observe(this, {
            binding.MainProgressBar.visibility = View.GONE
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Ups, došlo je do pogreške.")
            builder.setTitle("Greška")
            builder.setPositiveButton(
                "U REDU"
            ) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
            val dialog = builder.create()
            dialog.show()
        })

        if (timeOfLastRefresh == null) {
            binding.MainProgressBar.visibility = View.VISIBLE
            newsViewModel.getNewsList(Constants.url)
        } else if (Calendar.getInstance().timeInMillis - timeOfLastRefresh.toLong() >= 300000) {
            binding.MainProgressBar.visibility = View.VISIBLE
            newsViewModel.deleteAllRecords()
        } else {
            binding.MainProgressBar.visibility = View.VISIBLE
            newsViewModel.getAllRecords()
        }
    }

    override fun onItemClick(newsItem: NewsEntity) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("ItemTitle", newsItem.title)
        }
        startActivity(intent)
    }
}