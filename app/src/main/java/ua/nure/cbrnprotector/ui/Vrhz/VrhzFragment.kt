package ua.nure.cbrnprotector.ui.Vrhz

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment3EditTextsBinding

class VrhzFragment :
    BaseFragment<Fragment3EditTextsBinding, VrhzViewModel>(Fragment3EditTextsBinding::inflate) {
    override val viewModel: VrhzViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.N200_N300_error))
            return false
        }
        return true
    }

    private fun setListeners() {
        with(binding) {
            etFirst.doAfterTextChanged { text ->
                viewModel.setN200(text.toString())
            }
            etSecond.doAfterTextChanged { text ->
                viewModel.setN300(text.toString())
            }
            etThird.doAfterTextChanged { text ->
                if (text.isNullOrBlank()) {
                    viewModel.setNos(null)
                    return@doAfterTextChanged
                }

                viewModel.setNos(text.toString().toInt())
            }
            viewModel.N200State.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.N300State.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.NosState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_vhrz_title)
            tvFirst.text = getString(R.string.fragment_vhrz_N200)
            tvSecond.text = getString(R.string.fragment_vhrz_N300)
            etThird.inputType = InputType.TYPE_CLASS_NUMBER
            tvThird.text = getString(R.string.fragment_vhrz_Nos)
        }
    }
}