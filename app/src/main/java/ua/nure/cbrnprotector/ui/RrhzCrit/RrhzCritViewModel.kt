package ua.nure.cbrnprotector.ui.RrhzCrit

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class RrhzCritViewModel : BaseViewModel() {
    private val _RrhzState = MutableLiveData<Float?>()
    val RrhzState: LiveData<Float?>
        get() = _RrhzState
    private val _R0State = MutableLiveData<Float?>()
    val R0State: LiveData<Float?>
        get() = _R0State

    override fun getResult(): ColoredValuable? {
        val Rrhz = _RrhzState.value
        val R0 = _R0State.value
        val result = if (Rrhz != null && R0 != null) RrhzCritical(Rrhz, R0) else return null
        return Risk.findByValue(result)
    }

    override fun isValuesValid(): Boolean {
        val Rrhz = _RrhzState.value
        val R0 = _R0State.value
        if (Rrhz == null || R0 == null) return true
        return Rrhz >= R0
    }

    fun setRrhz(value: Float?) {
        _RrhzState.value = value
    }

    fun setR0(value: Float?) {
        _R0State.value = value
    }

    override fun clear() {
        _RrhzState.value = null
        _R0State.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun RrhzCritical(Rrhz: Float, R0: Float): Float
}