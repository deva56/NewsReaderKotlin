package com.example.newsreaderkotlin.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsreaderkotlin.Entity.NewsEntity
import com.example.newsreaderkotlin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerItemFragment(private val newsEntity: NewsEntity) : Fragment() {

    private lateinit var viewPagerItemAuthorTextView: TextView
    private lateinit var viewPagerItemTitleTextView: TextView
    private lateinit var viewPagerItemDescriptionTextView: TextView
    private lateinit var viewPagerItemUrlTextView: TextView
    private lateinit var viewPagerItemImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_pager_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerItemAuthorTextView = view.findViewById(R.id.ViewPagerItemAuthorTextView)
        viewPagerItemTitleTextView = view.findViewById(R.id.ViewPagerItemTitleTextView)
        viewPagerItemDescriptionTextView = view.findViewById(R.id.ViewPagerItemDescriptionTextView)
        viewPagerItemUrlTextView = view.findViewById(R.id.ViewPagerItemUrlTextView)
        viewPagerItemImageView = view.findViewById(R.id.ViewPagerItemImageView)

        loadValues()
    }

    private fun loadValues() {
        Glide.with(requireActivity()).load(newsEntity.urlToImage).centerCrop()
            .placeholder(R.drawable.ic_no_image_available).into(viewPagerItemImageView)
        viewPagerItemAuthorTextView.text = newsEntity.author
        viewPagerItemTitleTextView.text = newsEntity.title
        viewPagerItemDescriptionTextView.text = newsEntity.description
        viewPagerItemUrlTextView.text = newsEntity.url
    }
}