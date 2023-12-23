package ua.nure.cbrnprotector.ui.Pvam

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk
import ua.nure.cbrnprotector.utills.toIntArray

class PvamViewModel : BaseViewModel() {
    private val _DjivState = MutableLiveData<String?>()
    val DjivState: LiveData<String?>
        get() = _DjivState
    private val _NzvState = MutableLiveData<String?>()
    val NzvState: LiveData<String?>
        get() = _NzvState
    private val _NState = MutableLiveData<Int?>()
    val NState: LiveData<Int?>
        get() = _NState
    private val _NhvState = MutableLiveData<Int?>()
    val NhvState: LiveData<Int?>
        get() = _NhvState

    override fun getResult(): ColoredValuable? {
        val Djiv = _DjivState.value?.toIntArray()
        val Nzv = _NzvState.value?.toIntArray()
        val N = _NState.value
        val Nhv = _NhvState.value

        val result =
            if (Djiv != null && Nzv != null && N != null && Nhv != null) PvAM(
                Djiv,
                Nzv,
                Djiv.size,
                N,
                Nhv
            ) else return null
        return Risk.findByValue(result)
    }

    override fun isValuesValid(): Boolean {
        val Djiv = _DjivState.value?.toIntArray()
        val Nzv = _NzvState.value?.toIntArray()
        if (Djiv == null || Nzv == null) return true
        if (Djiv.size != Nzv.size) return false
        Djiv.forEachIndexed { index, item ->
            if (item > Nzv[index]) return false
        }
        return true
    }

    fun setDjiv(value: String?) {
        _DjivState.value = value
    }

    fun setNzv(value: String?) {
        _NzvState.value = value
    }

    fun setN(value: Int?) {
        _NState.value = value
    }

    fun setNhv(value: Int?) {
        _NhvState.value = value
    }

    override fun clear() {
        _DjivState.value = null
        _NzvState.value = null
        _NState.value = null
        _NhvState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun PvAM(Djiv: IntArray, Nzv: IntArray, Nv: Int, N: Int, Nhv: Int): Float
}