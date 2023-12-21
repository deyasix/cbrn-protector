package ua.nure.cbrnprotector.ui.V0

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseFragment
import ua.nure.cbrnprotector.databinding.Fragment2EditTextBinding


class V0Fragment :
    BaseFragment<Fragment2EditTextBinding, V0ViewModel>(Fragment2EditTextBinding::inflate) {
    override val viewModel: V0ViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        setResultLogic(binding.btnCalculate, binding.tvResult, binding.btnClearData)
    }

    override fun isValid(): Boolean {
        if (!viewModel.isValuesValid()) {
            showErrorSnackBar(getString(R.string.Nivt_Nos_error))
            return false
        }
        return true
    }

    private fun setListeners() {
        with(binding) {
            etFirst.addTextChangedListener(getTextWatcher {
                viewModel.setNivt(it.toInt())
            })
            etSecond.addTextChangedListener(getTextWatcher {
                viewModel.setNos(it.toInt())
            })
            viewModel.NivtState.observe(viewLifecycleOwner) {
                if (it == null) etFirst.text.clear()
            }
            viewModel.NosState.observe(viewLifecycleOwner) {
                if (it == null) etSecond.text.clear()
            }
        }
    }

    private fun init() {
        with(binding) {
            tvTitle.text = getString(R.string.fragment_v0_title)
            etFirst.inputType = InputType.TYPE_CLASS_NUMBER
            tvFirst.text = getString(R.string.fragment_v0_Nivt)
            etSecond.inputType = InputType.TYPE_CLASS_NUMBER
            tvSecond.text = getString(R.string.fragment_v0_Nos)
        }
    }
}