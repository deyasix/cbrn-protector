package ua.nure.cbrnprotector.ui.Vv

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2EditTextsBinding

class VvFragment :
    BaseFragment<Fragment2EditTextsBinding, VvViewModel>(Fragment2EditTextsBinding::inflate) {
    override val viewModel: VvViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Niv_Nob_error))
            return false
        }
        return true
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setNiv(it)
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setNob(it.toInt())
            })
            viewModel.NivState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.NobState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Vv_title)
            tvFirst.text = getString(R.string.fragment_Vv_Niv)
            etSecond.inputType = InputType.TYPE_CLASS_NUMBER
            tvSecond.text = getString(R.string.fragment_Vv_Nob)
        }
    }
}