package ua.nure.cbrnprotector.ui.Pv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment3EditTextsBinding

class PvFragment :
    BaseFragment<Fragment3EditTextsBinding, PvViewModel>(Fragment3EditTextsBinding::inflate) {
    override val viewModel: PvViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setDjiv(it)
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setNiv(it)
            })
            etThird.addTextChangedListener(getTextWatcher {
                viewModel.setNv(it.toInt())
            })
            viewModel.DjivState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.NivState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.NvState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Pv_title)
            tvFirst.text = getString(R.string.fragment_Pv_Djiv)
            tvSecond.text = getString(R.string.fragment_Pv_Niv)
            tvThird.text = getString(R.string.fragment_Pv_Nv)
        }
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Pv_error))
            return false
        }
        return true
    }
}