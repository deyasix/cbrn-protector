package ua.nure.cbrnprotector.ui.Ehbrya0

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class Ehbrya0Fragment : BaseFragment<Fragment2PopupsBinding, Ehbrya0ViewModel>(
    Fragment2PopupsBinding::inflate
) {
    override val viewModel: Ehbrya0ViewModel by viewModels()

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
                viewModel.setRrhzCrit(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setRrhz(it.value)
            }
            viewModel.RrhzCritState.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.RrhzState.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }

        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Ehbrya0_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.RrhzCrit)
            tvSecond.text = getString(R.string.Rrhz)
        }
    }
}