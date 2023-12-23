package ua.nure.cbrnprotector.ui.Vhbrya_rhz_op

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2EditTextsBinding


class VhbryaRhzOpFragment :
    BaseFragment<Fragment2EditTextsBinding, VhbryaRhzOpViewModel>(Fragment2EditTextsBinding::inflate) {
    override val viewModel: VhbryaRhzOpViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Nur_Nos_error))
            return false
        }
        return true
    }

    private fun setListeners() {
        with(binding) {
            etFirst.doAfterTextChanged {
                if (it.isNullOrBlank()) {
                    viewModel.setNur(null)
                    return@doAfterTextChanged
                }
                viewModel.setNur(it.toString().toInt())
            }
            etSecond.doAfterTextChanged {
                if (it.isNullOrBlank()) {
                    viewModel.setNos(null)
                    return@doAfterTextChanged
                }
                viewModel.setNos(it.toString().toInt())
            }
            viewModel.NurState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.NosState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_vhbrya_rhz_op_title)
            etFirst.inputType = InputType.TYPE_CLASS_NUMBER
            tvFirst.text = getString(R.string.fragment_vhbrya_rhz_op_Nur)
            etSecond.inputType = InputType.TYPE_CLASS_NUMBER
            tvSecond.text = getString(R.string.fragment_vhbrya_rhz_op_Nos)
        }
    }
}