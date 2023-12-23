package ua.nure.cbrnprotector.ui.E_neo_am

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment1PopupBinding
import ua.nure.cbrnprotector.domain.Risk

class ENeoAmFragment : BaseFragment<Fragment1PopupBinding, ENeoAmViewModel>(
    Fragment1PopupBinding::inflate
) {
    override val viewModel: ENeoAmViewModel by viewModels()

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
            viewModel.R0State.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_ENeoAm_title)
            ppFirst.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.fragment_ENeoAm_R0)
        }
    }
}