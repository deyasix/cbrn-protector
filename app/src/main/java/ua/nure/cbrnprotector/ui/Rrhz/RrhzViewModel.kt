package ua.nure.cbrnprotector.ui.Rrhz

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class RrhzViewModel : BaseViewModel() {
    private val _PrhzState = MutableLiveData<Float?>()
    val PrhzState: LiveData<Float?>
        get() = _PrhzState
    private val _VrhzState = MutableLiveData<Float?>()
    val VrhzState: LiveData<Float?>
        get() = _VrhzState

    override fun getResult(): ColoredValuable? {
        val Prhz = _PrhzState.value
        val Vrhz = _VrhzState.value
        val result = if (Prhz != null && Vrhz != null) Rrhz(Prhz, Vrhz) else return null
        return Risk.findByValue(result)
    }

    fun setPrhz(value: Float?) {
        _PrhzState.value = value
    }

    fun setVrhz(value: Float?) {
        _VrhzState.value = value
    }

    override fun clear() {
        _VrhzState.value = null
        _PrhzState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Rrhz(Prhz: Float, Vrhz: Float): Float
}