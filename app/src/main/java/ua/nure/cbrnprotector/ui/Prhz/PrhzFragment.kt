package ua.nure.cbrnprotector.ui.Prhz

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2EditTextsBinding

class PrhzFragment : BaseFragment<Fragment2EditTextsBinding, PrhzViewModel>(
    Fragment2EditTextsBinding::inflate
) {
    override val viewModel: PrhzViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.doOnTextChanged { text, _, _, _ ->
                viewModel.setDjirhz(text.toString())
            }
            etSecond.doOnTextChanged { text, _, _, _ ->
                viewModel.setNirhz(text.toString())
            }
            viewModel.DjirhzState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.NirhzState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Prhz_title)
            etFirst.hint = getString(R.string.et_hint)
            etSecond.hint = getString(R.string.et_hint)
            tvFirst.text = getString(R.string.fragment_Prhz_djirhz)
            tvSecond.text = getString(R.string.fragment_Prhz_nirhz)
        }
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Djirhz_Nirhz_error))
            return false
        }
        return true
    }
}