package ua.nure.cbrnprotector.base.popupplaceholders

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.domain.Valuable
import ua.nure.cbrnprotector.domain.getCondition

open class BasePopupPlaceholder(context: Context, attributes: AttributeSet? = null) :
    LinearLayout(context, attributes) {

    private var placeholder: TextView

    private val adapter: ConditionsAdapter by lazy {
        ConditionsAdapter(::onItemClickListener)
    }

    private val popupWindow by lazy {
        createPopup()
    }

    private var value: Valuable? = null
    var onItemSelected: (Valuable) -> Unit = {}

    init {
        inflate(context, R.layout.placeholder, this)
        placeholder = findViewById(R.id.placeholder)

        val typedArray =
            context.obtainStyledAttributes(attributes, R.styleable.BasePopupPlaceholder)
        val hint = typedArray.getString(R.styleable.BasePopupPlaceholder_hint)
        val stringArrayResId =
            typedArray.getResourceId(R.styleable.BasePopupPlaceholder_popup_values, 0)
        val stringArray =
            if (stringArrayResId != 0) resources.getStringArray(stringArrayResId) else null
        adapter.submitList(stringArray?.map { it.getCondition(context) })
        typedArray.recycle()

        hint?.let { placeholder.hint = it }

        this.setOnClickListener {
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
                placeholder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0)
            } else {
                val location = IntArray(2)
                getLocationOnScreen(location)
                popupWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, location[1] + height)
                placeholder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_collapse, 0)
            }
        }
    }

    var hint: CharSequence = placeholder.hint
        set(value) {
            placeholder.hint = value
            field = value
        }

    private fun createPopup(): PopupWindow {
        val popupView = LayoutInflater.from(context).inflate(R.layout.base_popup_layout, null)
        val popupWindow = PopupWindow(
            popupView,
            this.width,
            LayoutParams.WRAP_CONTENT
        )
        val recycler = popupView.findViewById<RecyclerView>(R.id.rv_items)
        recycler.adapter = adapter
        popupWindow.setOnDismissListener {
            placeholder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0)
        }

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        return popupWindow
    }

    fun setConditionsList(list: List<Valuable>) {
        adapter.submitList(list)
    }

    fun clearData() {
        value = null
        placeholder.text = null
    }

    fun getValue(): Valuable? = value

    private fun onItemClickListener(condition: Valuable) {
        placeholder.text = context.resources.getString(condition.nameResource)
        value = condition
        popupWindow.dismiss()
        onItemSelected(condition)
        placeholder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.name = value?.nameResource?.let { context.resources.getString(it) }
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            state.name?.let {
                value = it.getCondition(context)
                placeholder.text = state.name
            }
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    internal class SavedState : BaseSavedState {

        var name: String? = null

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            name = parcel.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(name)
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}