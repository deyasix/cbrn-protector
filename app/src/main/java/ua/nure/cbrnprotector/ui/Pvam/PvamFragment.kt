package ua.nure.cbrnprotector.ui.Pvam

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment4EditTextsBinding

class PvamFragment :
    BaseFragment<Fragment4EditTextsBinding, PvamViewModel>(Fragment4EditTextsBinding::inflate) {
    override val viewModel: PvamViewModel by viewModels()
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
                viewModel.setNzv(it)
            })
            etThird.addTextChangedListener(getTextWatcher {
                viewModel.setN(it.toInt())
            })
            etFourth.addTextChangedListener(getTextWatcher {
                viewModel.setNhv(it.toInt())
            })
            viewModel.DjivState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.NzvState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
            viewModel.NState.observe(viewLifecycleOwner) {
                if (it == null) etThird.text.clear()
            }
            viewModel.NhvState.observe(viewLifecycleOwner) {
                if (it == null) etFourth.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Pvam_title)
            tvFirst.text = getString(R.string.fragment_Pvam_Djiv)
            tvSecond.text = getString(R.string.fragment_Pvam_Nzv)
            tvThird.text = getString(R.string.fragment_Pvam_N)
            tvFourth.text = getString(R.string.fragment_Pvam_Nhv)
        }
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Pvam_error))
            return false
        }
        return true
    }
}