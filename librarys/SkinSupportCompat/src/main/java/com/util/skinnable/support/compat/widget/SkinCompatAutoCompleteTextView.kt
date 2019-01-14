package com.util.skinnable.support.compat.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.util.skin.library.helpers.SkinHelper
import com.util.skin.library.helpers.SkinHelper.Companion.INVALID_ID
import com.util.skin.library.widget.SkinSupportable
import com.util.skinnable.support.compat.R
import com.util.skinnable.support.compat.helpers.SkinCompatBackgroundHelper
import com.util.skinnable.support.compat.helpers.SkinCompatTextHelper
import com.util.skinnable.support.compat.res.SkinCompatVectorResources


class SkinCompatAutoCompleteTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.autoCompleteTextViewStyle
) : AppCompatAutoCompleteTextView(context, attrs, defStyleAttr), SkinSupportable {
    private var mDropDownBackgroundResId = INVALID_ID
    private val mTextHelper: SkinCompatTextHelper?
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?

    init {
        val a = context.obtainStyledAttributes(attrs, TINT_ATTRS, defStyleAttr, 0)
        if (a.hasValue(0)) {
            mDropDownBackgroundResId = a.getResourceId(0, INVALID_ID)
        }
        a.recycle()
        applyDropDownBackgroundResource()
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr)
        mTextHelper = SkinCompatTextHelper.create(this)
        mTextHelper.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun setDropDownBackgroundResource(@DrawableRes resId: Int) {
        super.setDropDownBackgroundResource(resId)
        mDropDownBackgroundResId = resId
        applyDropDownBackgroundResource()
    }

    private fun applyDropDownBackgroundResource() {
        if (SkinHelper.checkResourceIdValid(mDropDownBackgroundResId)) {
            val drawable = SkinCompatVectorResources.getDrawableCompat(context, mDropDownBackgroundResId)
            if (drawable != null) {
                setDropDownBackgroundDrawable(drawable)
            }
        }
    }

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        mBackgroundTintHelper?.onSetBackgroundResource(resId)
    }

    override fun setTextAppearance(resId: Int) {
        setTextAppearance(context, resId)
    }

    override fun setTextAppearance(context: Context, resId: Int) {
        super.setTextAppearance(context, resId)
        mTextHelper?.onSetTextAppearance(context, resId)
    }

    override fun setCompoundDrawablesRelativeWithIntrinsicBounds(
        @DrawableRes start: Int, @DrawableRes top: Int, @DrawableRes end: Int, @DrawableRes bottom: Int
    ) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
        mTextHelper?.onSetCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        @DrawableRes left: Int, @DrawableRes top: Int, @DrawableRes right: Int, @DrawableRes bottom: Int
    ) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        mTextHelper?.onSetCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun applySkin() {
        mBackgroundTintHelper?.applySkin()
        mTextHelper?.applySkin()
        applyDropDownBackgroundResource()
    }

    companion object {
        private val TINT_ATTRS = intArrayOf(android.R.attr.popupBackground)
    }

}