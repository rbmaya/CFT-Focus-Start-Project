package com.nsu.focusstartproject.presentation.onboarding

import android.content.res.Resources
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nsu.focusstartproject.R

class ViewPagerAdapter(
    resources: Resources,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val list: List<SlideItem> = listOf(
        SlideItem(R.drawable.new_loan, resources.getString(R.string.text_onboarding_slide_one)),
        SlideItem(R.drawable.loans_state, resources.getString(R.string.text_onboarding_slide_two)),
        SlideItem(
            R.drawable.ic_account_settings,
            resources.getString(R.string.text_onboarding_slide_three)
        )
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return SlideFragment.newInstance(bundleOf("item" to list[position]))
    }

}