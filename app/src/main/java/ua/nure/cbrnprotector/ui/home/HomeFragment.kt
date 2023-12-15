package ua.nure.cbrnprotector.ui.home

import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.FragmentHomeBinding

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModels()

}