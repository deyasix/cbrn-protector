package ua.nure.cbrnprotector.ui.E0am

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class E0amViewModel : BaseViewModel() {
    private val _RvState = MutableLiveData<Float?>()
    val RvState: LiveData<Float?>
        get() = _RvState
    private val _RvCritState = MutableLiveData<Float?>()
    val RvCritState: LiveData<Float?>
        get() = _RvCritState

    override fun getResult(): ColoredValuable? {
        val Rv = _RvState.value
        val RvCrit = _RvCritState.value
        val result = if (Rv != null && RvCrit != null) E0am(RvCrit, Rv) else return null
        return Risk.findByValue(result)
    }

    override fun isValuesValid(): Boolean {
        val Rv = _RvState.value
        val RvCrit = _RvCritState.value
        if (Rv == null || RvCrit == null) return true
        return Rv >= RvCrit
    }

    fun setRv(value: Float?) {
        _RvState.value = value
    }

    fun setRvCrit(value: Float?) {
        _RvCritState.value = value
    }

    override fun clear() {
        _RvState.value = null
        _RvCritState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun E0am(RvCrit: Float, Rv: Float): Float
}