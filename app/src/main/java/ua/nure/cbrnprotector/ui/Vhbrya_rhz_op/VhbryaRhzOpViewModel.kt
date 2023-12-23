package ua.nure.cbrnprotector.ui.Vhbrya_rhz_op

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class VhbryaRhzOpViewModel : BaseViewModel() {
    private val _NurState = MutableLiveData<Int?>()
    val NurState: LiveData<Int?>
        get() = _NurState
    private val _NosState = MutableLiveData<Int?>()
    val NosState: LiveData<Int?>
        get() = _NosState

    override fun getResult(): ColoredValuable? {
        val Nivt = _NurState.value
        val Nos = _NosState.value
        val result = if (Nivt != null && Nos != null) VhbryaRhzOp(Nivt, Nos) else return null
        return Risk.findByValue(result)
    }

    fun setNur(value: Int?) {
        _NurState.value = value
    }

    fun setNos(value: Int?) {
        _NosState.value = value
    }

    override fun isValuesValid(): Boolean {
        val Nivt = _NurState.value
        val Nos = _NosState.value
        if (Nivt == null || Nos == null) return true
        return Nos >= Nivt
    }

    override fun clear() {
        _NurState.value = null
        _NosState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun VhbryaRhzOp(Nur: Int, Nos: Int): Float
}