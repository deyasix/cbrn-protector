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
        setShowingResultLogic(binding.btnCalculate, binding.tvResult)
        binding.btnClearData.setOnClickListener {
            binding.ppRifDamageLevel.clearData()
            binding.ppRifProbability.clearData()
        }
    }
}