package com.nsu.focusstartproject.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.OboardingSlideFragmentBinding

class SlideFragment : Fragment(R.layout.oboarding_slide_fragment) {
    private val binding: OboardingSlideFragmentBinding by viewBinding()

    companion object {
        fun newInstance(bundle: Bundle?): SlideFragment {
            val slide = SlideFragment()
            slide.arguments = bundle
            return slide
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            arguments?.let {
                val item: SlideItem? = it.getParcelable("item")
                item?.let { slideItem ->
                    image.setImageResource(slideItem.drawableRes)
                    text.text = slideItem.text
                }
            }
        }
    }

}