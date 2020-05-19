package th.co.cdgs.mobile.floatlabel

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.BufferType
import androidx.annotation.Nullable
import com.airbnb.paris.extensions.style
import kotlinx.android.synthetic.main.float_label.view.*


class FloatLabel(context: Context, attrs: AttributeSet?, defStyle: Int) :
    FrameLayout(context, attrs, defStyle) {
    /**
     * Returns the EditText portion of this View
     *
     * @return the EditText portion of this View
     */
    /**
     * Reference to the EditText
     */
    var editText: EditText? = null
        private set

    /**
     * When init is complete, child views can no longer be added
     */
    private var mInitComplete = false

    /**
     * Returns the label portion of this View
     *
     * @return the label portion of this View
     */
    /**
     * Reference to the TextView used as the label
     */
    var label: TextView? = null
        private set

    /**
     * LabelAnimator that animates the appearance and disappearance of the label TextView
     */
    private var mLabelAnimator: LabelAnimator = DefaultLabelAnimator()

    /**
     * True if the TextView label is showing (alpha 1f)
     */
    private var mLabelShowing = false

    /**
     * Holds saved state if any is waiting to be restored
     */
    private var mSavedState: Bundle? = null

    /**
     * True when any setTextWithoutAnimation method is called and then immediately turned false
     * once the text update has finished.
     */
    private var mSkipAnimation = false

    /**
     * Interface for providing custom animations to the label TextView.
     */
    interface LabelAnimator {
        /**
         * Called when the label should become visible
         *
         * @param label TextView to animate to visible
         */
        fun onDisplayLabel(label: View?)

        /**
         * Called when the label should become invisible
         *
         * @param label TextView to animate to invisible
         */
        fun onHideLabel(label: View?)
    }

    interface DialogListener {
        fun onOpen(view: View?)
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun addView(child: View?) {
        if (mInitComplete) {
            throw UnsupportedOperationException("You cannot add child views to a FloatLabel")
        } else {
            super.addView(child)
        }
    }

    override fun addView(child: View?, index: Int) {
        if (mInitComplete) {
            throw UnsupportedOperationException("You cannot add child views to a FloatLabel")
        } else {
            super.addView(child, index)
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (mInitComplete) {
            throw UnsupportedOperationException("You cannot add child views to a FloatLabel")
        } else {
            super.addView(child, index, params)
        }
    }

    override fun addView(child: View?, width: Int, height: Int) {
        if (mInitComplete) {
            throw UnsupportedOperationException("You cannot add child views to a FloatLabel")
        } else {
            super.addView(child, width, height)
        }
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (mInitComplete) {
            throw UnsupportedOperationException("You cannot add child views to a FloatLabel")
        } else {
            super.addView(child, params)
        }
    }

    /**
     * Sets the text to be displayed above the EditText if the EditText is
     * nonempty or as the EditText hint if it is empty
     *
     * @param resid
     * int String resource ID
     */
    fun setLabel(resid: Int) {
        setLabel(context.getString(resid))
    }

    /**
     * Sets the text to be displayed above the EditText if the EditText is
     * nonempty or as the EditText hint if it is empty
     *
     * @param hint
     * CharSequence to set as the label
     */
    fun setLabel(hint: CharSequence?) {
        editText!!.hint = hint
        label!!.text = hint
    }

    /**
     * Specifies a new LabelAnimator to handle calls to show/hide the label
     *
     * @param labelAnimator LabelAnimator to use; null causes use of the default LabelAnimator
     */
    fun setLabelAnimator(labelAnimator: LabelAnimator?) {
        mLabelAnimator = labelAnimator ?: DefaultLabelAnimator()
    }

    /**
     * Sets the EditText's text with animation
     *
     * @param resid int String resource ID
     */
    fun setText(resid: Int) {
        editText!!.setText(resid)
    }

    /**
     * Sets the EditText's text with label animation
     *
     * @param text char[] text
     * @param start int start of char array to use
     * @param len int characters to use from the array
     */
    fun setText(text: CharArray?, start: Int, len: Int) {
        editText!!.setText(text, start, len)
    }

    /**
     * Sets the EditText's text with label animation
     *
     * @param resid int String resource ID
     * @param type TextView.BufferType
     */
    fun setText(resid: Int, type: BufferType?) {
        editText!!.setText(resid, type)
    }

    /**
     * Sets the EditText's text with label animation
     *
     * @param text CharSequence to set
     */
    fun setText(text: CharSequence?) {
        editText!!.setText(text)
    }

    /**
     * Sets the EditText's text with label animation
     *
     * @param text CharSequence to set
     * @param type TextView.BufferType
     */
    fun setText(text: CharSequence?, type: BufferType?) {
        editText!!.setText(text, type)
    }

    /**
     * Sets the EditText's text without animating the label
     *
     * @param resid int String resource ID
     */
    fun setTextWithoutAnimation(resid: Int) {
        mSkipAnimation = true
        editText!!.setText(resid)
    }

    /**
     * Sets the EditText's text without animating the label
     *
     * @param text char[] text
     * @param start int start of char array to use
     * @param len int characters to use from the array
     */
    fun setTextWithoutAnimation(text: CharArray?, start: Int, len: Int) {
        mSkipAnimation = true
        editText!!.setText(text, start, len)
    }

    /**
     * Sets the EditText's text without animating the label
     *
     * @param resid int String resource ID
     * @param type TextView.BufferType
     */
    fun setTextWithoutAnimation(resid: Int, type: BufferType?) {
        mSkipAnimation = true
        editText!!.setText(resid, type)
    }

    /**
     * Sets the EditText's text without animating the label
     *
     * @param text CharSequence to set
     */
    fun setTextWithoutAnimation(text: CharSequence?) {
        mSkipAnimation = true
        editText!!.setText(text)
    }

    /**
     * Sets the EditText's text without animating the label
     *
     * @param text CharSequence to set
     * @param type TextView.BufferType
     */
    fun setTextWithoutAnimation(text: CharSequence?, type: BufferType?) {
        mSkipAnimation = true
        editText!!.setText(text, type)
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        val childLeft = paddingLeft
        val childRight = right - left - paddingRight
        val childTop = paddingTop
        val childBottom = bottom - top - paddingBottom
        layoutChild(label, childLeft, childTop, childRight, childBottom)
        layoutChild(
            editText,
            childLeft,
            childTop + label!!.measuredHeight,
            childRight,
            childBottom
        )
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun layoutChild(
        child: View?,
        parentLeft: Int,
        parentTop: Int,
        parentRight: Int,
        parentBottom: Int
    ) {
        if (child!!.visibility != View.GONE) {
            val lp = child.layoutParams as LayoutParams
            val width = child.measuredWidth
            val height = child.measuredHeight
            val childLeft: Int
            val childTop = parentTop + lp.topMargin
            var gravity = lp.gravity
            if (gravity == -1) {
                gravity = Gravity.TOP or Gravity.START
            }
            val layoutDirection: Int = layoutDirection
            val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)
            childLeft = when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                Gravity.CENTER_HORIZONTAL -> parentLeft + (parentRight - parentLeft - width) / 2 + lp.leftMargin - lp.rightMargin
                Gravity.END -> parentRight - width - lp.rightMargin
                Gravity.START -> parentLeft + lp.leftMargin
                else -> parentLeft + lp.leftMargin
            }
            child.layout(childLeft, childTop, childLeft + width, childTop + height)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Restore any state that's been pending before measuring
        if (mSavedState != null) {
            var childState = mSavedState!!.getParcelable<Parcelable>(SAVE_STATE_KEY_EDIT_TEXT)
            editText!!.onRestoreInstanceState(childState)
            childState = mSavedState!!.getParcelable(SAVE_STATE_KEY_LABEL)
            label!!.onRestoreInstanceState(childState)
            if (mSavedState!!.getBoolean(SAVE_STATE_KEY_FOCUS, false)) {
                editText!!.requestFocus()
            }
            mSavedState = null
        }
        measureChild(editText, widthMeasureSpec, heightMeasureSpec)
        measureChild(label, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            if (state.getBoolean(SAVE_STATE_TAG, false)) {
                // Save our state for later since children will have theirs restored after this
                // and having more than one FloatLabel in an Activity or Fragment means you have
                // multiple views of the same ID
                mSavedState = state
                super.onRestoreInstanceState(state.getParcelable(SAVE_STATE_PARENT))
                return
            }
        }
        super.onRestoreInstanceState(state)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val saveState = Bundle()
        saveState.putParcelable(
            SAVE_STATE_KEY_EDIT_TEXT,
            editText!!.onSaveInstanceState()
        )
        saveState.putParcelable(
            SAVE_STATE_KEY_LABEL,
            label!!.onSaveInstanceState()
        )
        saveState.putBoolean(SAVE_STATE_KEY_FOCUS, editText!!.isFocused)
        saveState.putBoolean(SAVE_STATE_TAG, true)
        saveState.putParcelable(SAVE_STATE_PARENT, superState)
        return saveState
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec)
        var result = 0
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = editText!!.measuredHeight + label!!.measuredHeight
            result += paddingTop + paddingBottom
            result = result.coerceAtLeast(suggestedMinimumHeight)
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }
        return result
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec)
        var result = 0
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = editText!!.measuredWidth.coerceAtLeast(label!!.measuredWidth)
            result = result.coerceAtLeast(suggestedMinimumWidth)
            result += paddingLeft + paddingRight
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }
        return result
    }

    /**
     * Initializes the view's default values and values from attrs, if not null
     *
     * @param context Context to access styled attributes
     * @param attrs AttributeSet from constructor or null
     * @param defStyle int resource ID of style to use for defaults
     */
    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        // Load custom attributes
        val layout: Int
        var editTextId: Int = R.id.edit_text
        var floatLabelId: Int = R.id.float_label
        val text: CharSequence?
        val hint: CharSequence?
        val hintColor: ColorStateList?
        val floatLabelColor: Int
        val imeOptions: Int
        val inputType: Int
        val nextFocusDownId: Int
        val nextFocusForwardId: Int
        val nextFocusLeftId: Int
        val nextFocusRightId: Int
        val nextFocusUpId: Int
        val isDialog: Boolean
        val theme: Int
        if (attrs == null) {
            layout = R.layout.float_label
            text = null
            hint = null
            hintColor = null
            floatLabelColor = 0
            imeOptions = 0
            inputType = 0
            nextFocusDownId = View.NO_ID
            nextFocusForwardId = View.NO_ID
            nextFocusLeftId = View.NO_ID
            nextFocusRightId = View.NO_ID
            nextFocusUpId = View.NO_ID
            isDialog = false
            theme = View.NO_ID
        } else {
            val a: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.FloatLabel, defStyle, 0)

            // Main attributes
            layout = a.getResourceId(R.styleable.FloatLabel_android_layout, R.layout.float_label)
            editTextId = a.getResourceId(R.styleable.FloatLabel_editTextId, R.id.edit_text)
            floatLabelId = a.getResourceId(R.styleable.FloatLabel_labelId, R.id.float_label)
            text = a.getText(R.styleable.FloatLabel_android_text)
            hint = a.getText(R.styleable.FloatLabel_android_hint)
            hintColor = a.getColorStateList(R.styleable.FloatLabel_android_textColorHint)
            floatLabelColor = a.getColor(R.styleable.FloatLabel_floatLabelColor, 0)
            imeOptions = a.getInt(R.styleable.FloatLabel_android_imeOptions, 0)
            inputType =
                a.getInt(R.styleable.FloatLabel_android_inputType, InputType.TYPE_CLASS_TEXT)

            // Next focus views
            nextFocusDownId = a.getResourceId(
                R.styleable.FloatLabel_android_nextFocusDown,
                View.NO_ID
            )
            nextFocusForwardId = a.getResourceId(
                R.styleable.FloatLabel_android_nextFocusForward,
                View.NO_ID
            )
            nextFocusLeftId = a.getResourceId(
                R.styleable.FloatLabel_android_nextFocusLeft,
                View.NO_ID
            )
            nextFocusRightId = a.getResourceId(
                R.styleable.FloatLabel_android_nextFocusRight,
                View.NO_ID
            )
            nextFocusUpId =
                a.getResourceId(R.styleable.FloatLabel_android_nextFocusUp, View.NO_ID)
            isDialog = a.getBoolean(R.styleable.FloatLabel_isDialog, false)
            theme = a.getResourceId(R.styleable.FloatLabel_android_theme, View.NO_ID)
            // Done with TypedArray
            a.recycle()
        }
        View.inflate(context, layout, this)
        editText = findViewById<View>(editTextId) as EditText
        if (editText == null) {
            // fallback to default value
            editText = findViewById<View>(R.id.edit_text) as EditText
        }
        if (editText == null) {
            throw RuntimeException(
                "Your layout must have an EditText whose ID is @id/edit_text"
            )
        }
        if (editTextId != R.id.edit_text) {
            editText!!.id = editTextId
        }
        editText!!.hint = hint
        editText!!.setText(text)
        if (hintColor != null) {
            editText!!.setHintTextColor(hintColor)
        }
        if (imeOptions != 0) {
            editText!!.imeOptions = imeOptions
        }
        if (inputType != 0) {
            editText!!.inputType = inputType
        }
        // Set all next focus views
        editText!!.nextFocusDownId = nextFocusDownId
        editText!!.nextFocusForwardId = nextFocusForwardId
        editText!!.nextFocusLeftId = nextFocusLeftId
        editText!!.nextFocusRightId = nextFocusRightId
        editText!!.nextFocusUpId = nextFocusUpId

        if (isDialog) {
            editText!!.setCompoundDrawables(
                null,
                null,
                context.getDrawable(R.drawable.ic_next),
                null
            )
            editText!!.isFocusableInTouchMode = false
        }

        // Set up the label view
        label = findViewById<View>(floatLabelId) as TextView
        if (label == null) {
            // fallback to default value
            label = findViewById<View>(R.id.float_label) as TextView
        }
        if (label == null) {
            throw RuntimeException(
                "Your layout must have a TextView whose ID is @id/float_label"
            )
        }
        if (floatLabelId != R.id.float_label) {
            label!!.id = floatLabelId
        }
        label!!.text = editText!!.hint
        if (floatLabelColor != 0) label!!.setTextColor(floatLabelColor)

        // Listen to EditText to know when it is empty or nonempty
        editText!!.addTextChangedListener(EditTextWatcher())

        // Check current state of EditText
        if (editText!!.text.isEmpty()) {
            label!!.alpha = 0f
            mLabelShowing = false
        } else {
            label!!.visibility = View.VISIBLE
            mLabelShowing = true
        }

        if (theme != View.NO_ID) {
            editText!!.style(theme)
        }

        // Mark init as complete to prevent accidentally breaking the view by
        // adding children
        mInitComplete = true
    }

    /**
     * LabelAnimator that uses the traditional float label Y shift and fade.
     *
     * @author Ian G. Clifton
     */
    private class DefaultLabelAnimator : LabelAnimator {
        override fun onDisplayLabel(label: View?) {
            val offset = (label?.height?.div(2))?.toFloat()
            val currentY = label?.y
            if (currentY != offset) {
                if (offset != null) {
                    label.y = offset
                }
            }
            label?.animate()?.alpha(1F)?.y(0F)
        }

        override fun onHideLabel(label: View?) {
            val offset = (label?.height?.div(2))?.toFloat()
            val currentY = label?.y
            if (currentY != 0f) {
                label?.y = 0F
            }
            offset?.let { label.animate()?.alpha(0F)?.y(it) }
        }
    }

    /**
     * TextWatcher that notifies FloatLabel when the EditText changes between
     * having text and not having text or vice versa.
     *
     * @author Ian G. Clifton
     */
    private inner class EditTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (mSkipAnimation) {
                mSkipAnimation = false
                if (s.isEmpty()) {
                    // TextView label should be gone
                    if (mLabelShowing) {
                        label!!.alpha = 0f
                        mLabelShowing = false
                    }
                } else if (!mLabelShowing) {
                    // TextView label should be visible
                    label!!.alpha = 1f
                    label!!.y = 0f
                    mLabelShowing = true
                }
                return
            }
            if (s.isEmpty()) {
                // Text is empty; TextView label should be invisible
                if (mLabelShowing) {
                    mLabelAnimator.onHideLabel(label)
                    mLabelShowing = false
                }
            } else if (!mLabelShowing) {
                // Text is nonempty; TextView label should be visible
                mLabelShowing = true
                mLabelAnimator.onDisplayLabel(label)
            }
        }

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            // Ignored
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            // Ignored
        }
    }

    fun setOnDialogListener(@Nullable l: DialogListener){
        editText!!.setOnClickListener {
            l.onOpen(it)
        }
    }


    companion object {
        private const val SAVE_STATE_KEY_EDIT_TEXT = "saveStateEditText"
        private const val SAVE_STATE_KEY_LABEL = "saveStateLabel"
        private const val SAVE_STATE_PARENT = "saveStateParent"
        private const val SAVE_STATE_TAG = "saveStateTag"
        private const val SAVE_STATE_KEY_FOCUS = "saveStateFocus"
    }

    init {
        init(context, attrs, defStyle)
    }
}