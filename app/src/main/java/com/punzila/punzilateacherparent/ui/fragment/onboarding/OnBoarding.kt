package com.punzila.punzilateacherparent.ui.fragment.onboarding

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.punzila.punzilateacherparent.R
import com.punzila.punzilateacherparent.adapter.SliderAdapter
import com.punzila.punzilateacherparent.common.BaseFragment
import com.punzila.punzilateacherparent.databinding.FragmentOnBoardingBinding
import com.punzila.punzilateacherparent.utils.Prefs
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import kotlin.math.abs


class OnBoarding : BaseFragment() {
    private var sliderAdapter: SliderAdapter = SliderAdapter()
    val viewmodel: OnBoardingViewModel by viewModels<OnBoardingViewModel>()
    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     //   setStatusBarWhite(requireActivity())
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.onBoardingViewModel = viewmodel
        binding.lifecycleOwner = this

        viewmodel.dataSet.observe(viewLifecycleOwner, Observer {
            viewpager2.adapter = sliderAdapter
            sliderAdapter.setItems(it)
            dots.setViewPager2(viewpager2)

        })

        viewmodel.startNavigation.observe(viewLifecycleOwner, Observer {
            if (it) {
              this.findNavController()
                  .navigate(OnBoardingDirections.actionOnBoardingToAuthFragment())
                Prefs.getInstance(requireContext())!!.hasCompleteWalkThrough = false
                viewmodel.doneNavigation()
            }
        })

        binding.viewpager2.registerOnPageChangeCallback(pagerCallBack)
        return binding.root
    }

    val pagerCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewmodel.dataSet.observe(viewLifecycleOwner, Observer {
                setConstrain(position == it.size - 1)
                if (position == it.size - 1) {
                    tvNext.text = getString(R.string.onboarding_get_started)
                } else {
                    tvNext.text = getString(R.string.onboarding_next)
                }
            })
        }
    }

    private fun onNext() {
        tvNext.setOnClickListener {
            viewpager2.setCurrentItem(getNextPossibleItemIndex(1), true)
        }
    }

    private fun getNextPossibleItemIndex(change: Int  ): Int {
        val currentIndex = viewpager2.currentItem
        val total = viewpager2.adapter!!.itemCount

        if (currentIndex + change < 0) {
            return 0
        }
        return abs((currentIndex + change) % total)
    }

    private fun setConstrain(b: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constrainIntro)
        if (b) {
            constraintSet.connect(
                R.id.llBottom1,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                0
            )
            constraintSet.connect(
                R.id.btnStart,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                resources.getDimension(R.dimen.app_spacing_standard_new).toInt()
            )
        } else {
            constraintSet.connect(
                R.id.llBottom1,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                0
            )
            constraintSet.clear(R.id.btnStart, ConstraintSet.END)
        }
        TransitionManager.beginDelayedTransition(constrainIntro)
        constraintSet.applyTo(constrainIntro)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewpager2.unregisterOnPageChangeCallback(pagerCallBack)
    }

}


