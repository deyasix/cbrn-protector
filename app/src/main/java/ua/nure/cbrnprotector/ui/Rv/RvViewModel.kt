package ua.nure.cbrnprotector.ui.Rv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class RvViewModel : BaseViewModel() {
    private val _PvState = MutableLiveData<Float?>()
    val PvState: LiveData<Float?>
        get() = _PvState
    private val _VvState = MutableLiveData<Float?>()
    val VvState: LiveData<Float?>
        get() = _VvState

    override fun getResult(): ColoredValuable? {
        val PV = _PvState.value
        val VV = _VvState.value
        val result = if (PV != null && VV != null) Rv(PV, VV) else return null
        return Risk.findByValue(result)
    }

    fun setPv(value: Float?) {
        _PvState.value = value
    }

    fun setVv(value: Float?) {
        _VvState.value = value
    }

    override fun clear() {
        _PvState.value = null
        _VvState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Rv(Pv: Float, Vv: Float): Float
}