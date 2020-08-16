package com.punzila.punzilateacherparent.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.punzila.punzilateacherparent.R


@BindingAdapter("hideIfFalse")
fun hideIfFalse(view: View, boolean: Boolean) {
    if (boolean) view.visibility = View.VISIBLE else view.visibility = View.GONE
}

@BindingAdapter("hideIfEmpty")
fun hideIfEmpty(textView: TextView, error: String) {
    if (error.isEmpty()) {
        textView.visibility = View.INVISIBLE
    } else {
        textView.visibility = View.VISIBLE
        textView.error = error
    }
}

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, url: String) {
    if (!url.isEmpty()) {
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.placeholder)
    }
}