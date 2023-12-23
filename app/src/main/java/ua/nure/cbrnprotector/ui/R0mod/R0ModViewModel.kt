package ua.nure.cbrnprotector.ui.R0mod

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class R0ModViewModel : BaseViewModel() {
    private val _P0State = MutableLiveData<Float?>()
    val P0State: LiveData<Float?>
        get() = _P0State
    private val _V0State = MutableLiveData<Float?>()
    val V0State: LiveData<Float?>
        get() = _V0State

    override fun getResult(): ColoredValuable? {
        val P0 = _P0State.value
        val V0 = _V0State.value
        val result = if (V0 != null && P0 != null) R0Mod(P0, V0) else return null
        return Risk.findByValue(result)
    }

    fun setP0(value: Float?) {
        _P0State.value = value
    }

    fun setV0(value: Float?) {
        _V0State.value = value
    }

    override fun clear() {
        _P0State.value = null
        _V0State.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun R0Mod(P0: Float, V0: Float): Float
}