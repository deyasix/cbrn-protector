package ua.nure.cbrnprotector.ui.Ehbrya

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class EhbryaViewModel : BaseViewModel() {
    private val _RrhzCritOchState = MutableLiveData<Float?>()
    val RrhzCritOchState: LiveData<Float?>
        get() = _RrhzCritOchState
    private val _RrhzOchState = MutableLiveData<Float?>()
    val RrhzOchState: LiveData<Float?>
        get() = _RrhzOchState

    override fun getResult(): ColoredValuable? {
        val Rrhz = _RrhzOchState.value
        val RrhzCrit = _RrhzCritOchState.value
        val result = if (Rrhz != null && RrhzCrit != null) Ehbrya(RrhzCrit, Rrhz) else return null
        return Risk.findByValue(result)
    }

    override fun isValuesValid(): Boolean {
        val Rrhz = _RrhzOchState.value
        val RrhzCrit = _RrhzCritOchState.value
        if (Rrhz == null || RrhzCrit == null) return true
        return Rrhz >= RrhzCrit
    }

    fun setRrhz(value: Float?) {
        _RrhzOchState.value = value
    }

    fun setRrhzCrit(value: Float?) {
        _RrhzCritOchState.value = value
    }

    override fun clear() {
        _RrhzOchState.value = null
        _RrhzCritOchState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Ehbrya(RrhzCrit: Float, Rrhz: Float): Float
}