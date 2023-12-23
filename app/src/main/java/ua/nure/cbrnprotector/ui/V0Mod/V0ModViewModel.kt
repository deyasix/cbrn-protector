package ua.nure.cbrnprotector.ui.V0Mod

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk
import ua.nure.cbrnprotector.utills.toIntArray

class V0ModViewModel : BaseViewModel() {
    private val _N0ivState = MutableLiveData<String?>()
    val N0ivState: LiveData<String?>
        get() = _N0ivState
    private val _NobState = MutableLiveData<Int?>()
    val NobState: LiveData<Int?>
        get() = _NobState

    override fun getResult(): ColoredValuable? {
        val N0iv = _N0ivState.value?.toIntArray()
        val Nob = _NobState.value
        val result = if (N0iv != null && Nob != null) V0Mod(N0iv, Nob) else return null
        return Risk.findByValue(result)
    }

    fun setN0iv(value: String?) {
        _N0ivState.value = value
    }

    fun setNob(value: Int?) {
        _NobState.value = value
    }

    override fun isValuesValid(): Boolean {
        val N0iv = _N0ivState.value?.toIntArray()
        val Nob = _NobState.value
        if (N0iv == null || Nob == null) return true
        return N0iv.sum() <= Nob
    }

    override fun clear() {
        _N0ivState.value = null
        _NobState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun V0Mod(Nivt: IntArray, Nos: Int): Float
}