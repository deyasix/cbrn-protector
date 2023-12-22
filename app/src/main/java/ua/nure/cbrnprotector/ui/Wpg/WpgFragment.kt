package ua.nure.cbrnprotector.ui.Wpg

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseNumericFragment
import ua.nure.cbrnprotector.databinding.Fragment3EditTextsBinding

class WpgFragment :
    BaseNumericFragment<Fragment3EditTextsBinding, WpgViewModel>(Fragment3EditTextsBinding::inflate) {
    override val viewModel: WpgViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setWop(it.toInt())
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setWzikz(it.toInt())
            })
            etThird.addTextChangedListener(getTextWatcher {
                viewModel.setWpoz(it.toInt())
            })
            viewModel.WopState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.WzikzState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.WpozState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.Wpg)
            etFirst.inputType = InputType.TYPE_CLASS_NUMBER
            tvFirst.text = getString(R.string.fragment_Wpg_Wop)
            etSecond.inputType = InputType.TYPE_CLASS_NUMBER
            tvSecond.text = getString(R.string.fragment_Wpg_Wzikz)
            etThird.inputType = InputType.TYPE_CLASS_NUMBER
            tvThird.text = getString(R.string.fragment_Wpg_Wpoz)
        }
    }
}