package com.example.newsreaderkotlin.ViewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private var fragments: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    fun setFragments(fragments: List<Fragment>) {
        this.fragments = fragments
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}