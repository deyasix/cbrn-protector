package ua.nure.cbrnprotector.ui.Rnu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding


class RnuFragment :
    BaseFragment<Fragment2PopupsBinding, RnuViewModel>(Fragment2PopupsBinding::inflate) {

    override val viewModel: RnuViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setShowingResultLogic(binding.btnCalculate, binding.tvResult)
    }

    private fun setListeners() {
        with(binding) {
            ppRifProbability.onItemSelected = {
                viewModel.setPy(it.value)
            }
            ppRifDamageLevel.onItemSelected = {
                viewModel.setVy(it.value)
            }
            btnClearData.setOnClickListener {
                animateChangeColorAfterClear(btnCalculate)
                tvResult.visibility = View.GONE
                viewModel.clear()
            }
            viewModel.PyState.observe(viewLifecycleOwner) {
                if (it == null) ppRifProbability.clearData()
            }
            viewModel.VyState.observe(viewLifecycleOwner) {
                if (it == null) ppRifDamageLevel.clearData()
            }
        }
    }
}