package ua.nure.cbrnprotector.ui.Ehbrya0

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class Ehbrya0ViewModel : BaseViewModel() {
    private val _RrhzCritState = MutableLiveData<Float?>()
    val RrhzCritState: LiveData<Float?>
        get() = _RrhzCritState
    private val _RrhzState = MutableLiveData<Float?>()
    val RrhzState: LiveData<Float?>
        get() = _RrhzState

    override fun getResult(): ColoredValuable? {
        val Rrhz = _RrhzState.value
        val RrhzCrit = _RrhzCritState.value
        val result = if (Rrhz != null && RrhzCrit != null) Ehbrya0(RrhzCrit, Rrhz) else return null
        return Risk.findByValue(result)
    }

    override fun isValuesValid(): Boolean {
        val Rrhz = _RrhzState.value
        val RrhzCrit = _RrhzCritState.value
        if (Rrhz == null || RrhzCrit == null) return true
        return Rrhz >= RrhzCrit
    }

    fun setRrhz(value: Float?) {
        _RrhzState.value = value
    }

    fun setRrhzCrit(value: Float?) {
        _RrhzCritState.value = value
    }

    override fun clear() {
        _RrhzState.value = null
        _RrhzCritState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Ehbrya0(RrhzCrit: Float, Rrhz: Float): Float
}