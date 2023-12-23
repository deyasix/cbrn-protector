package ua.nure.cbrnprotector.ui.E0am

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class E0amFragment : BaseFragment<Fragment2PopupsBinding, E0amViewModel>(
    Fragment2PopupsBinding::inflate
) {
    override val viewModel: E0amViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.RrhzCrit_Rrhz_error))
            return false
        }
        return true
    }

    private fun setListeners() {
        with(binding) {
            ppFirst.onItemSelected = {
                viewModel.setRv(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setRvCrit(it.value)
            }
            viewModel.RvState.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.RvCritState.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }

        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_E0am_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.Rv)
            tvSecond.text = getString(R.string.fragment_E0am_RvCrit)
        }
    }
}