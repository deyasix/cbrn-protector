package ua.nure.cbrnprotector.ui.En

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class EnFragment : BaseFragment<Fragment2PopupsBinding, EnViewModel>(
    Fragment2PopupsBinding::inflate
) {
    override val viewModel: EnViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            ppFirst.onItemSelected = {
                viewModel.setR0(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setRn(it.value)
            }
            viewModel.R0State.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.RnState.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_En_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.fragment_En_r0)
            tvSecond.text = getString(R.string.fragment_En_Rn)
        }
    }
}