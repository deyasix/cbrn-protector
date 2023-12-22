package ua.nure.cbrnprotector.ui.Whbrya

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseNumericFragment
import ua.nure.cbrnprotector.databinding.Fragment3EditTextsBinding

class WhbryaFragment :
    BaseNumericFragment<Fragment3EditTextsBinding, WhbryaViewModel>(Fragment3EditTextsBinding::inflate) {
    override val viewModel: WhbryaViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setWvo(it.toInt())
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setWpg(it.toInt())
            })
            etThird.addTextChangedListener(getTextWatcher {
                viewModel.setWln(it.toInt())
            })
            viewModel.WvoState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.WpgState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.WlnState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Whbrya_title)
            etFirst.inputType = InputType.TYPE_CLASS_NUMBER
            tvFirst.text = getString(R.string.Wvo)
            etSecond.inputType = InputType.TYPE_CLASS_NUMBER
            tvSecond.text = getString(R.string.Wpg)
            etThird.inputType = InputType.TYPE_CLASS_NUMBER
            tvThird.text = getString(R.string.Wln)
        }
    }
}