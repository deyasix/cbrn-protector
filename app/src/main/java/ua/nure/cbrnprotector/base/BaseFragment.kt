package ua.nure.cbrnprotector.base

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
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

    fun setShowingResultLogic(clickableView: View?, resultView: View?) {
        clickableView?.setOnClickListener {
            //val result = viewModel.getResult()
            val colorFrom = ContextCompat.getColor(requireContext(), R.color.light_blue)
            val colorTo = ContextCompat.getColor(requireContext(), R.color.btn_grey)
            ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
                duration = CHANGE_BUTTON_COLOR_DURATION
                addUpdateListener {
                    clickableView.backgroundTintList =
                        ColorStateList.valueOf((it.animatedValue as Int))
                }
                start()
            }
            resultView?.apply {
                visibility = View.VISIBLE
                startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        android.R.anim.slide_in_left
                    )
                )
            }
        }
    }

    companion object {
        const val CHANGE_BUTTON_COLOR_DURATION = 500L
    }

}