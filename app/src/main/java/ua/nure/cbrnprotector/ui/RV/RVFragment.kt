package ua.nure.cbrnprotector.ui.RV

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding
import ua.nure.cbrnprotector.domain.Risk

class RVFragment : BaseFragment<Fragment2PopupsBinding, RVViewModel>(
    Fragment2PopupsBinding::inflate
) {
    override val viewModel: RVViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            ppFirst.onItemSelected = {
                viewModel.setPV(it.value)
            }
            ppSecond.onItemSelected = {
                viewModel.setVV(it.value)
            }
            viewModel.PVState.observe(viewLifecycleOwner) {
                if (it == null) ppFirst.clearData()
            }
            viewModel.VVState.observe(viewLifecycleOwner) {
                if (it == null) ppSecond.clearData()
            }

        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_RV_title)
            ppFirst.setConditionsList(Risk.entries)
            ppSecond.hint = getString(R.string.choose_probability)
            ppSecond.setConditionsList(Risk.entries)
            tvFirst.text = getString(R.string.fragment_RV_PV)
            tvSecond.text = getString(R.string.fragment_RV_VV)
        }
    }
}