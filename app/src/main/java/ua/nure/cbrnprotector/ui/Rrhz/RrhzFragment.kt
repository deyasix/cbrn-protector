package ua.nure.cbrnprotector.ui.Rrhz

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class RrhzFragment : BaseFragment<Fragment2PopupsBinding, RrhzViewModel>(
    Fragment2PopupsBinding::inflate
) {
    override val viewModel: RrhzViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            ppFirst.onItemSelected = {
                viewModel.setPrhz(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setVrhz(it.value)
            }
            viewModel.PrhzState.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.VrhzState.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Rrhz_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.fragment_Rrhz_Prhz)
            tvSecond.text = getString(R.string.fragment_Rrhz_Vrhz)
        }
    }
}