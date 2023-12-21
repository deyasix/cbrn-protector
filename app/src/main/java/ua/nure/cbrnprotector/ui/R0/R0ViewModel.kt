package ua.nure.cbrnprotector.ui.R0

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class R0ViewModel : BaseViewModel() {
    private val _V0State = MutableLiveData<Float?>()
    val V0State: LiveData<Float?>
        get() = _V0State
    private val _P0State = MutableLiveData<Float?>()
    val P0State: LiveData<Float?>
        get() = _P0State

    override fun getResult(): ColoredValuable? {
        val V0 = _V0State.value
        val P0 = _P0State.value
        val result = if (V0 != null && P0 != null) R0(P0, V0) else return null
        return Risk.findByValue(result)
    }

    fun setV0(value: Float?) {
        _V0State.value = value
    }

    fun setP0(value: Float?) {
        _P0State.value = value
    }

    override fun clear() {
        _V0State.value = null
        _P0State.value = null
    }

    override fun isValuesValid(): Boolean {
        val V0 = V0State.value
        val P0 = P0State.value
        if (V0 == null || P0 == null) return true
        if (V0 < 0 || V0 > 1 || P0 < 0 || P0 > 1) return false
        return true
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun R0(P0: Float, V0: Float): Float
}