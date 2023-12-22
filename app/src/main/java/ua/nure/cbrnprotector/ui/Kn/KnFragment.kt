package ua.nure.cbrnprotector.ui.Kn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseNumericFragment
import ua.nure.cbrnprotector.databinding.Fragment3EditTextsBinding

class KnFragment :
    BaseNumericFragment<Fragment3EditTextsBinding, KnViewModel>(Fragment3EditTextsBinding::inflate) {
    override val viewModel: KnViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setWi(it)
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setAi(it)
            })
            etThird.addTextChangedListener(getTextWatcher {
                viewModel.setNium(it)
            })
            viewModel.WiState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.AiState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.NiumState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Kn_title)
            tvFirst.text = getString(R.string.fragment_Kn_Wi)
            tvSecond.text = getString(R.string.fragment_Kn_Ai)
            tvThird.text = getString(R.string.fragment_Kn_Nium)
        }
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Kn_error))
            return false
        }
        return true
    }
}