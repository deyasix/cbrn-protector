package ua.nure.cbrnprotector.ui.Vv

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk
import ua.nure.cbrnprotector.utills.toIntArray

class VvViewModel : BaseViewModel() {
    private val _NivState = MutableLiveData<String?>()
    val NivState: LiveData<String?>
        get() = _NivState
    private val _NobState = MutableLiveData<Int?>()
    val NobState: LiveData<Int?>
        get() = _NobState

    override fun getResult(): ColoredValuable? {
        val N0iv = _NivState.value?.toIntArray()
        val Nob = _NobState.value
        val result = if (N0iv != null && Nob != null) Vv(N0iv, Nob) else return null
        return Risk.findByValue(result)
    }

    fun setNiv(value: String?) {
        _NivState.value = value
    }

    fun setNob(value: Int?) {
        _NobState.value = value
    }

    override fun isValuesValid(): Boolean {
        val N0iv = _NivState.value?.toIntArray()
        val Nob = _NobState.value
        if (N0iv == null || Nob == null) return true
        return N0iv.sum() <= Nob
    }

    override fun clear() {
        _NivState.value = null
        _NobState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Vv(Niv: IntArray, Nob: Int): Float
}