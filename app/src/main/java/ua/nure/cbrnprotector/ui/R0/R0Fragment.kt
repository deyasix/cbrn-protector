package ua.nure.cbrnprotector.ui.R0

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2EditTextBinding

class R0Fragment :
    BaseFragment<Fragment2EditTextBinding, R0ViewModel>(
        Fragment2EditTextBinding::inflate
    ) {
    override val viewModel: R0ViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setShowingResultLogic(binding.btnCalculate, binding.tvResult)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher { viewModel.setV0(it) })
            etSecond.addTextChangedListener(getTextWatcher { viewModel.setP0(it) })
            btnClearData.setOnClickListener {
                animateChangeColorAfterClear(btnCalculate)
                tvResult.visibility = View.GONE
                viewModel.clear()
            }
            viewModel.V0State.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.P0State.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
        }
    }
}