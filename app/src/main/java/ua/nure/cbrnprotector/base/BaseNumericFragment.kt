package ua.nure.cbrnprotector.base

import android.content.res.ColorStateList
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import ua.nure.cbrnprotector.R

abstract class BaseNumericFragment<VBinding : ViewBinding, VM : BaseViewModel>(
    inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) : BaseFragment<VBinding, VM>(inflaterMethod) {
    override fun setResultLogic(
        buttonToCalculate: Button,
        resultView: TextView,
        buttonToClear: Button
    ) {
        buttonToCalculate.setOnClickListener {
            if (isValid()) {
                val result = viewModel.getResult()
                if (result == null) {
                    showErrorSnackBar()
                } else {
                    val colorFrom = ContextCompat.getColor(requireContext(), R.color.light_blue)
                    val colorTo = ContextCompat.getColor(requireContext(), R.color.btn_grey)
                    animateChangeColor(colorFrom, colorTo, buttonToCalculate)
                    resultView.apply {
                        visibility = View.VISIBLE
                        startAnimation(
                            AnimationUtils.loadAnimation(
                                context,
                                android.R.anim.slide_in_left
                            )
                        )
                    }
                    resultView.text = result.value.toInt().toString()
                    val color = context?.getColor(result.color)
                    color?.let {
                        resultView.backgroundTintList = ColorStateList.valueOf(color)
                    }
                }
            }
        }
        buttonToClear.setOnClickListener {
            if (resultView.visibility == View.VISIBLE) {
                animateChangeColorAfterClear(buttonToCalculate)
                resultView.visibility = View.GONE
            }
            viewModel.clear()
        }
    }

}