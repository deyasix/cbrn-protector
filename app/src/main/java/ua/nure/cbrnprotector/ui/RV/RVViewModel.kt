package ua.nure.cbrnprotector.ui.RV

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class RVViewModel : BaseViewModel() {
    private val _PVState = MutableLiveData<Float?>()
    val PVState: LiveData<Float?>
        get() = _PVState
    private val _VVState = MutableLiveData<Float?>()
    val VVState: LiveData<Float?>
        get() = _VVState

    override fun getResult(): ColoredValuable? {
        val PV = _PVState.value
        val VV = _VVState.value
        val result = if (PV != null && VV != null) Rv(PV, VV) else return null
        return Risk.findByValue(result)
    }

    fun setPV(value: Float?) {
        _PVState.value = value
    }

    fun setVV(value: Float?) {
        _VVState.value = value
    }

    override fun clear() {
        _PVState.value = null
        _VVState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Rv(PV: Float, VV: Float): Float
}