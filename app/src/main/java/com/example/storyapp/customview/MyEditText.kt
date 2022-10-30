package com.example.storyapp.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class MyEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var showPassword: Drawable
    private lateinit var hidePassword: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        showPassword =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility) as Drawable
        hidePassword =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_off) as Drawable
//        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val buttonStart: Float
            val buttonEnd: Float
            var isButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                buttonEnd = (hidePassword.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < buttonEnd -> isButtonClicked = true
                }
            } else {
                buttonStart = (width - paddingEnd - hidePassword.intrinsicWidth).toFloat()
                when {
                    event.x > buttonStart -> isButtonClicked = true
                }
            }
            if (isButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_BUTTON_PRESS -> {
                        showPassword = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_visibility
                        ) as Drawable
                        showPasswordButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        hidePassword = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_visibility_off
                        ) as Drawable
                        hidePasswordButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

    private fun showPasswordButton() {
        setButtonDrawables(endOfTheText = showPassword)
    }

    private fun hidePasswordButton() {
        setButtonDrawables(endOfTheText = hidePassword)
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

}