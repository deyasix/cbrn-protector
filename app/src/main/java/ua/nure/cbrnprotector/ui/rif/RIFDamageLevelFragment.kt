package ua.nure.cbrnprotector.ui.rif

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2PopupsBinding


class RIFDamageLevelFragment :
    BaseFragment<Fragment2PopupsBinding, RIFDamageLevelViewModel>(Fragment2PopupsBinding::inflate) {

    override val viewModel: RIFDamageLevelViewModel by viewModels()

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