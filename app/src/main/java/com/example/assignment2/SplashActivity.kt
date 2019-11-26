package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val anim = AnimationUtils.loadAnimation(
            this@SplashActivity,
            R.anim.splash_anim
        )

        img.startAnimation(anim)





        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                var intent = Intent()
                intent.setClass(this@SplashActivity, ScrollingActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {
            }


        }

        )


    }
}
