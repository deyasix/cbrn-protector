package ua.nure.cbrnprotector.base

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.*
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import ua.nure.cbrnprotector.R

abstract class BaseFragment<VBinding : ViewBinding, VM : BaseViewModel>(
    private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) :
    Fragment() {

    private var _binding: VBinding? = null
    val binding get() = requireNotNull(_binding)
    protected abstract val viewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterMethod.invoke(inflater, container, false)
        return binding.root
    }

    fun setShowingResultLogic(clickableView: View?, resultView: TextView?) {
        clickableView?.setOnClickListener {
            val result = viewModel.getResult()
            if (result == null) {
                showErrorSnackBar()
            } else {
                val colorFrom = ContextCompat.getColor(requireContext(), R.color.light_blue)
                val colorTo = ContextCompat.getColor(requireContext(), R.color.btn_grey)
                animateChangeColor(colorFrom, colorTo, clickableView)
                resultView?.apply {
                    visibility = View.VISIBLE
                    startAnimation(
                        AnimationUtils.loadAnimation(
                            context,
                            android.R.anim.slide_in_left
                        )
                    )
                }
                resultView?.text = context?.getString(result.nameResource)
                val color = context?.getColor(result.color)
                color?.let {
                    resultView?.backgroundTintList = ColorStateList.valueOf(color)
                }
            }
        }
    }

    private fun animateChangeColor(colorFrom: Int, colorTo: Int, view: View) {
        ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
            duration = CHANGE_BUTTON_COLOR_DURATION
            addUpdateListener {
                view.backgroundTintList =
                    ColorStateList.valueOf((it.animatedValue as Int))
            }
            start()
        }
    }

    protected fun animateChangeColorAfterClear(view: View) {
        val colorTo = ContextCompat.getColor(requireContext(), R.color.light_blue)
        val colorFrom = ContextCompat.getColor(requireContext(), R.color.btn_grey)
        animateChangeColor(colorFrom, colorTo, view)
    }

    protected fun showErrorSnackBar(text: String? = getString(R.string.error_not_enough_data)) {
        val errorLayout = layoutInflater.inflate(R.layout.error_snackbar, null)
        errorLayout.findViewById<TextView>(R.id.tv_snackbar).text = text
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT)
        (snackBar.view as SnackbarLayout).apply {
            setPadding(0, 0, 0, 0)
            addView(errorLayout, 0)
        }
        snackBar.show()
    }

    protected fun getTextWatcher(onChangedText: (Float) -> Unit) : TextWatcher {
        return object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    try {
                        val input = s.toString().toFloat()
                        if (input < 0 || input > 1) showErrorSnackBar("Некоректні дані. Ймовірність не може бути від'ємною або більше одиниці.")
                        else onChangedText(input)

                    } catch (e: NumberFormatException) {
                        showErrorSnackBar(getString(R.string.number_format_error))
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        }
    }

    companion object {
        const val CHANGE_BUTTON_COLOR_DURATION = 500L
    }

}