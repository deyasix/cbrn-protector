package ua.nure.cbrnprotector.ui.EhbryaNec

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class EhbryaNecViewModel : BaseViewModel() {
    private val _R0State = MutableLiveData<Float?>()
    val R0State: LiveData<Float?>
        get() = _R0State

    override fun getResult(): ColoredValuable? {
        val R0 = _R0State.value
        val result = if (R0 != null) EhbryaNecessary(R0) else return null
        return Risk.findByValue(result)
    }

    fun setR0(value: Float?) {
        _R0State.value = value
    }

    override fun clear() {
        _R0State.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun EhbryaNecessary(R0: Float): Float
}