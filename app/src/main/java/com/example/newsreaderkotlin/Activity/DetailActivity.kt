package com.example.newsreaderkotlin.Activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.newsreaderkotlin.Fragment.ViewPagerItemFragment
import com.example.newsreaderkotlin.ViewModel.NewsViewModel
import com.example.newsreaderkotlin.ViewPagerAdapter.ViewPagerAdapter
import com.example.newsreaderkotlin.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val fragments: ArrayList<Fragment> = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        viewPager = binding.DetailActivityPager
        viewPagerAdapter = ViewPagerAdapter(this, fragments)
        viewPager.adapter = viewPagerAdapter

        binding.DetailProgressBar.visibility = View.VISIBLE

        val newsViewModel: NewsViewModel by viewModels()

        val toolbar: Toolbar = binding.DetailAppBar
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "News"

        newsViewModel.getLiveDataNewsEntity().observe(this, { newsEntities ->
            var viewPagerIndex = 0
            val mainActivityClickedArticle = intent.getStringExtra("ItemTitle")

            for (i in newsEntities.indices) {

                if (mainActivityClickedArticle.equals(newsEntities[i].title)) {
                    viewPagerIndex = i
                }
                val viewPagerItemFragment = ViewPagerItemFragment(newsEntities[i])
                fragments.add(viewPagerItemFragment)
            }

            val finalViewPagerIndex = viewPagerIndex

            binding.DetailProgressBar.visibility = View.GONE
            viewPagerAdapter.setFragments(fragments)
            viewPager.setCurrentItem(finalViewPagerIndex, false)
        })

        newsViewModel.getThrowableLiveData().observe(this, {
            binding.DetailProgressBar.visibility = View.GONE
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Ups, došlo je do pogreške.")
            builder.setTitle("Greška")
            builder.setPositiveButton(
                "U REDU"
            ) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
            val dialog = builder.create()
            dialog.show()
        })

        newsViewModel.getAllRecords()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}