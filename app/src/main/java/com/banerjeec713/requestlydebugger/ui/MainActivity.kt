package com.banerjeec713.requestlydebugger.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.banerjeec713.requestlydebugger.App
import com.banerjeec713.requestlydebugger.IPreferenceHelper.SharedPreferencesHelper
import com.banerjeec713.requestlydebugger.util.Constants.TAG
import com.banerjeec713.requestlydebugger.data.DataManager
import com.banerjeec713.requestlydebugger.R
import com.banerjeec713.requestlydebugger.ui.base.BaseActivity
import com.banerjeec713.requestlydebugger.util.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var relativeLayout: RelativeLayout
    private lateinit var likeBtn: ImageView
    private lateinit var dislikeBtn: ImageView
    private var likeCheck = false
    private var dislikeCheck = false
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)

        relativeLayout = findViewById(R.id.relative_layout)
        likeBtn = findViewById(R.id.like_btn)
        dislikeBtn = findViewById(R.id.dislike_btn)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this@MainActivity)


        val imageView = findViewById<ImageView>(R.id.meme_img)
        val shimmer =
            Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        viewModel!!.memes.observe(this, {
            val imageUrl = it.image
            Glide.with(this@MainActivity)
                .load(imageUrl)
                .placeholder(shimmerDrawable)
                .error(ContextCompat.getDrawable(this@MainActivity, R.drawable.warning))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        defaultIcons()
                        displaySnackbar(false, "Could not load image")
                        nextMeme()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        defaultIcons()
                        Log.d(TAG, "onResourceReady: ${sharedPreferencesHelper.times}")
                        sharedPreferencesHelper.incrementTimes()
                        if (sharedPreferencesHelper.times > 10) {
                            showBottomDialog()
                            sharedPreferencesHelper.times = 0
                            sharedPreferencesHelper.likes = 0
                            sharedPreferencesHelper.dislikes = 0
                        }
                        return false
                    }

                })
                .into(imageView)
        })

        viewModel!!.error.observe(this, {
            if (it) {
                displaySnackbar(true, "Problem occurred")
            }
        })

        likeBtn.setOnClickListener {
            if (!Util.isNetworkAvailable(this@MainActivity)) {
                displaySnackbar(true, "No Internet connection")
                return@setOnClickListener
            }

            if (!likeCheck) {
                likeCheck = true
                likeBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.filled_heart))
                sharedPreferencesHelper.incrementLikes()
            } else {
                likeCheck = false
                likeBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.empty_heart))
            }
            nextMeme()
        }

        dislikeBtn.setOnClickListener {
            if (!Util.isNetworkAvailable(this@MainActivity)) {
                displaySnackbar(true, "No Internet connection")
                return@setOnClickListener
            }

            if (!dislikeCheck) {
                dislikeCheck = true
                dislikeBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.filled_dislike
                    )
                )
                sharedPreferencesHelper.incrementDislikes()
            } else {
                dislikeCheck = false
                dislikeBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.empty_dislike
                    )
                )
            }
            nextMeme()
        }

        if (sharedPreferencesHelper.times >= 10) {
            showBottomDialog()
            sharedPreferencesHelper.times = 0
            sharedPreferencesHelper.likes = 0
            sharedPreferencesHelper.dislikes = 0
        }
        else{
            viewModel!!.loadMeme()
        }

    }

    private fun showBottomDialog() {
        val dialog = BottomSheetDialog(this@MainActivity)
        val view = layoutInflater.inflate(R.layout.fragment_main, null)
        val playBtn = view.findViewById<Button>(R.id.play_again)
        val likeText = view.findViewById<TextView>(R.id.like_text)
        val dislikeText = view.findViewById<TextView>(R.id.dislike_text)
        playBtn.setOnClickListener {
            dialog.dismiss()
            nextMeme()
        }

        likeText.text = "Number of liked posts: ${sharedPreferencesHelper.likes}"
        dislikeText.text = "Number of disliked posts: ${sharedPreferencesHelper.dislikes}"

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun nextMeme() {
        likeBtn.isEnabled = false
        dislikeBtn.isEnabled = false
        viewModel!!.loadMeme()
    }

    private fun defaultIcons() {
        likeCheck = false
        dislikeCheck = false
        likeBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.empty_heart))
        dislikeBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.empty_dislike))
        likeBtn.isEnabled = true
        dislikeBtn.isEnabled = true
    }

    override fun createViewModel(): MainViewModel {
        val factory = MainViewModelFactory(DataManager.getInstance(App.instance))
        return ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun displaySnackbar(isError: Boolean, message: String) {
        Util.showSnack(relativeLayout, isError, message)
    }

}