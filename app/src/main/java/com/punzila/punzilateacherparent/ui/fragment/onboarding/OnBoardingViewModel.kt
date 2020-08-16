package com.punzila.punzilateacherparent.ui.fragment.onboarding

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.punzila.punzilateacherparent.R
import com.punzila.punzilateacherparent.model.SlideContent
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import kotlin.math.abs

class OnBoardingViewModel(application: Application): AndroidViewModel(application) {
    private val list = listOf(
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.bg_slide1)!!,
            "Nzelu+",
            "Be part of the high quality tutors platform in Zambia"
        ),
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.bg_slide2)!!,
            "Find Students",
            "Find students and get paid for each teaching session"
        ),
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.bg_slide3)!!,
            "Total Flexibility",
            "Work from home with who you want. Instant payments"
        ),
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.bg_slide1)!!,
            "The Best Tech",
            "Find, call and message students, automated scheduling and payments, virtual classroom with recordings and more"
        )
    )

    private val _dataSet = MutableLiveData<List<SlideContent>>().apply { value = list }
    val dataSet: LiveData<List<SlideContent>>
        get() = _dataSet

    private val _buttonVisibility = MutableLiveData<Boolean>().apply { value = false }
    val buttonVisibility: LiveData<Boolean>
        get() = _buttonVisibility


    private val _startNavigation = MutableLiveData<Boolean>().apply { value = false }
    val startNavigation: LiveData<Boolean>
        get() = _startNavigation
    fun navigateToAuth() {
        _startNavigation.value = true
    }

    fun doneNavigation() {
        _startNavigation.value = false
    }



}