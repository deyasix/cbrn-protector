package ua.nure.cbrnprotector.ui.hostilesIndicator

import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.FragmentHostilesIndicatorBinding

class RiskAchievingGoalHostilesIndicatorFragment :
    BaseFragment<FragmentHostilesIndicatorBinding, RiskAchievingGoalHostilesIndicatorViewModel>(
        FragmentHostilesIndicatorBinding::inflate
    ) {
    override val viewModel: RiskAchievingGoalHostilesIndicatorViewModel by viewModels()

}