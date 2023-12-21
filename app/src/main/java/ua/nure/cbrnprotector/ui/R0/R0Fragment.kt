package ua.nure.cbrnprotector.ui.R0

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class R0Fragment :
    BaseFragment<Fragment2PopupsBinding, R0ViewModel>(
        Fragment2PopupsBinding::inflate
    ) {
    override val viewModel: R0ViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            ppFirst.onItemSelected = {
                viewModel.setV0(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setP0(it.value)
            }
            viewModel.V0State.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.P0State.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_r0_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.fragment_r0_v0)
            tvSecond.text = getString(R.string.fragment_r0_p0)
        }
    }
}