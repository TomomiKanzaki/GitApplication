package com.example.gitapplication.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gitapplication.R

/**
 * Add Fragment to host
 * @param containerId layoutId host fragment will show
 * @param fragment target fragment
 * @param isAddToBackStack check whether fragment add to back stack
 */
fun AppCompatActivity.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    isAddToBackStack: Boolean = true
) {
    try {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.name)

        fragmentTransaction.add(containerId, fragment, fragment::class.java.name)
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Add Fragment to host
 * @param containerId layoutId host fragment will show
 * @param fragment target fragment
 * @param isAddToBackStack check whether fragment add to back stack
 */
fun Fragment.addFragment(
    @IdRes containerId: Int, fragment: Fragment,
    isAddToBackStack: Boolean = true,
    addFromActivity: Boolean = false
) {
    try {
        val fm = if (addFromActivity) (activity as AppCompatActivity).supportFragmentManager else childFragmentManager
        val fragmentTransaction = fm.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.name)

        fragmentTransaction.add(containerId, fragment, fragment::class.java.name)
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.runCardAnimationClickListener( onClick: ((view: View) -> Unit)? = null,
                                        handleDoneAnimation: (() -> Unit)? = null) {
    setOnClickListener {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.85f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.85f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        animator.repeatCount = 1
        animator.duration = 80
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                isEnabled = false
                onClick?.invoke(this@runCardAnimationClickListener)
            }

            override fun onAnimationEnd(animation: Animator?) {
                handleDoneAnimation?.invoke()
                postDelayed({ isEnabled = true }, 300)
            }
        })

        animator.start()
    }
}

