package ua.nure.cbrnprotector.ui.V0

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class V0ViewModel : BaseViewModel() {
    private val _NivtState = MutableLiveData<Int?>()
    val NivtState: LiveData<Int?>
        get() = _NivtState
    private val _NosState = MutableLiveData<Int?>()
    val NosState: LiveData<Int?>
        get() = _NosState

    override fun getResult(): ColoredValuable? {
        val Nivt = _NivtState.value
        val Nos = _NosState.value
        val result = if (Nivt != null && Nos != null) V0(Nivt, Nos) else return null
        return Risk.findByValue(result)
    }

    fun setNivt(value: Int?) {
        _NivtState.value = value
    }

    fun setNos(value: Int?) {
        _NosState.value = value
    }

    override fun isValuesValid(): Boolean {
        val Nivt = _NivtState.value
        val Nos = _NosState.value
        if (Nivt == null || Nos == null) return true
        return Nos >= Nivt
    }

    override fun clear() {
        _NivtState.value = null
        _NosState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun V0(Nivt: Int, Nos: Int): Float
}