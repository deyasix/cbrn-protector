package ua.nure.cbrnprotector.ui.Wln

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseNumericFragment
import ua.nure.cbrnprotector.databinding.Fragment3EditTextsBinding

class WlnFragment :
    BaseNumericFragment<Fragment3EditTextsBinding, WlnViewModel>(Fragment3EditTextsBinding::inflate) {
    override val viewModel: WlnViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setWos(it.toInt())
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setWovt(it.toInt())
            })
            etThird.addTextChangedListener(getTextWatcher {
                viewModel.setWm(it.toInt())
            })
            viewModel.WosState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.WovtState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.WmState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.Wln)
            etFirst.inputType = InputType.TYPE_CLASS_NUMBER
            tvFirst.text = getString(R.string.fragment_Wln_Wos)
            etSecond.inputType = InputType.TYPE_CLASS_NUMBER
            tvSecond.text = getString(R.string.fragment_Wln_Wovt)
            etThird.inputType = InputType.TYPE_CLASS_NUMBER
            tvThird.text = getString(R.string.fragment_Wln_Wm)
        }
    }
}