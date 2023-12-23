package ua.nure.cbrnprotector.ui.Kiob

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseNumericFragment
import ua.nure.cbrnprotector.databinding.Fragment2EditTextsBinding

class KiobFragment :
    BaseNumericFragment<Fragment2EditTextsBinding, KiobViewModel>(Fragment2EditTextsBinding::inflate) {
    override val viewModel: KiobViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setLif(it.replace(',', '.').toFloat())
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setLiob(it.replace(',', '.').toFloat())
            })
            viewModel.LifState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.LiobState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_Kiob_title)
            tvFirst.text = getString(R.string.fragment_Kiob_Lif)
            tvSecond.text = getString(R.string.fragment_Kiob_Liob)
        }
    }

}