package ua.nure.cbrnprotector.ui.Cn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseNumericFragment
import ua.nure.cbrnprotector.databinding.Fragment2EditTextsBinding

class CnFragment :
    BaseNumericFragment<Fragment2EditTextsBinding, CnViewModel>(Fragment2EditTextsBinding::inflate) {
    override val viewModel: CnViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setCi(it)
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setNi(it)
            })
            viewModel.CiState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.NiState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Cn_title)
            tvFirst.text = getString(R.string.fragment_Cn_Ci)
            tvSecond.text = getString(R.string.fragment_Cn_Ni)
        }
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Cn_error))
            return false
        }
        return true
    }
}