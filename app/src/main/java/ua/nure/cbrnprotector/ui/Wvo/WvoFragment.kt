package ua.nure.cbrnprotector.ui.Wvo

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseNumericFragment
import ua.nure.cbrnprotector.databinding.Fragment3EditTextsBinding

class WvoFragment :
    BaseNumericFragment<Fragment3EditTextsBinding, WvoViewModel>(Fragment3EditTextsBinding::inflate) {
    override val viewModel: WvoViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setWrhr(it.toInt())
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setWrhk(it.toInt())
            })
            etThird.addTextChangedListener(getTextWatcher {
                viewModel.setWln(it.toInt())
            })
            viewModel.WrhrState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.WrhkState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.WlnState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.Wvo)
            etFirst.inputType = InputType.TYPE_CLASS_NUMBER
            tvFirst.text = getString(R.string.fragment_Wvo_Wrhr)
            etSecond.inputType = InputType.TYPE_CLASS_NUMBER
            tvSecond.text = getString(R.string.fragment_Wvo_Wrhk)
            etThird.inputType = InputType.TYPE_CLASS_NUMBER
            tvThird.text = getString(R.string.fragment_Wwo_W0)
        }
    }
}