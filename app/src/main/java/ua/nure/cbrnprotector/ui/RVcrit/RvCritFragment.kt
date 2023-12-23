package ua.nure.cbrnprotector.ui.RVcrit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class RvCritFragment : BaseFragment<Fragment2PopupsBinding, RvCritViewModel>(
    Fragment2PopupsBinding::inflate
) {
    override val viewModel: RvCritViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            ppFirst.onItemSelected = {
                viewModel.setRv(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setR0(it.value)
            }
            viewModel.RvState.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.R0State.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }
        }
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Rv_R0_error))
            return false
        }
        return true
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_RV_crit_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.fragment_RV_crit_RV)
            tvSecond.text = getString(R.string.fragment_RV_crit_R0)
        }
    }
}