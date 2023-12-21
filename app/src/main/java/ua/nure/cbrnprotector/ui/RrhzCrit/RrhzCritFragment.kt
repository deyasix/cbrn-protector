package ua.nure.cbrnprotector.ui.RrhzCrit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class RrhzCritFragment : BaseFragment<Fragment2PopupsBinding, RrhzCritViewModel>(
    Fragment2PopupsBinding::inflate
) {
    override val viewModel: RrhzCritViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.R0_Rn_error))
            return false
        }
        return true
    }

    private fun setListeners() {
        with(binding) {
            ppFirst.onItemSelected = {
                viewModel.setRrhz(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setR0(it.value)
            }
            viewModel.RrhzState.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.R0State.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_RrhzCrit_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.fragment_RrhzCrit_Rrhz)
            tvSecond.text = getString(R.string.fragment_RrhzCrit_R0)
        }
    }
}