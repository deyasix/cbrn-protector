package ua.nure.cbrnprotector.ui.En

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class EnViewModel : BaseViewModel() {
    private val _R0State = MutableLiveData<Float?>()
    val R0State: LiveData<Float?>
        get() = _R0State
    private val _RnState = MutableLiveData<Float?>()
    val RnState: LiveData<Float?>
        get() = _RnState

    override fun getResult(): ColoredValuable? {
        val R0 = _R0State.value
        val Rn = _RnState.value
        val result = if (R0 != null && Rn != null) En(Rn, R0) else return null
        return Risk.findByValue(result)
    }

    fun setR0(value: Float?) {
        _R0State.value = value
    }

    fun setRn(value: Float?) {
        _RnState.value = value
    }

    override fun isValuesValid(): Boolean {
        val Rn = _RnState.value
        val R0 = _R0State.value
        if (Rn == null || R0 == null) return true
        return Rn >= R0
    }

    override fun clear() {
        _R0State.value = null
        _RnState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun En(Rn: Float, R0: Float): Float
}