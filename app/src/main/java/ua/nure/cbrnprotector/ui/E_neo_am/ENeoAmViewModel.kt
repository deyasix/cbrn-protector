package ua.nure.cbrnprotector.ui.E_neo_am

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class ENeoAmViewModel : BaseViewModel() {
    private val _R0State = MutableLiveData<Float?>()
    val R0State: LiveData<Float?>
        get() = _R0State

    override fun getResult(): ColoredValuable? {
        val Rv = _R0State.value
        val result = if (Rv != null) ENeoAm(Rv) else return null
        return Risk.findByValue(result)
    }

    fun setRv(value: Float?) {
        _R0State.value = value
    }

    override fun clear() {
        _R0State.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun ENeoAm(R0: Float): Float
}