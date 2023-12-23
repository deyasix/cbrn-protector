package ua.nure.cbrnprotector.ui.RVcrit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class RvCritViewModel : BaseViewModel() {
    private val _RvState = MutableLiveData<Float?>()
    val RvState: LiveData<Float?>
        get() = _RvState
    private val _R0State = MutableLiveData<Float?>()
    val R0State: LiveData<Float?>
        get() = _R0State

    override fun getResult(): ColoredValuable? {
        val RV = _RvState.value
        val R0 = _R0State.value
        val result = if (RV != null && R0 != null) RvCritical(RV, R0) else return null
        return Risk.findByValue(result)
    }

    fun setRv(value: Float?) {
        _RvState.value = value
    }

    fun setR0(value: Float?) {
        _R0State.value = value
    }

    override fun isValuesValid(): Boolean {
        val rv = RvState.value
        val r0 = R0State.value
        if (rv == null || r0 == null) return true
        return rv >= r0
    }

    override fun clear() {
        _RvState.value = null
        _R0State.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun RvCritical(Pv: Float, Vv: Float): Float
}