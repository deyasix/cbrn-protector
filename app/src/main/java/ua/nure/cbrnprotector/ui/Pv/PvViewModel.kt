package ua.nure.cbrnprotector.ui.Pv

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk
import ua.nure.cbrnprotector.utills.toIntArray

class PvViewModel : BaseViewModel() {
    private val _DjivState = MutableLiveData<String?>()
    val DjivState: LiveData<String?>
        get() = _DjivState
    private val _NivState = MutableLiveData<String?>()
    val NivState: LiveData<String?>
        get() = _NivState
    private val _NvState = MutableLiveData<Int?>()
    val NvState: LiveData<Int?>
        get() = _NvState

    override fun getResult(): ColoredValuable? {
        val Djiv = _DjivState.value?.toIntArray()
        val Niv = _NivState.value?.toIntArray()
        val Nv = _NvState.value

        val result =
            if (Djiv != null && Niv != null && Nv != null) Pv(Djiv, Niv, Nv) else return null
        return Risk.findByValue(result)
    }

    override fun isValuesValid(): Boolean {
        val Djiv = _DjivState.value?.toIntArray()
        val Niv = _NivState.value?.toIntArray()
        val Nv = _NvState.value
        if (Djiv == null || Niv == null || Nv == null) return true
        if (Djiv.size != Niv.size) return false
        Djiv.forEachIndexed { index, item ->
            if (item < Niv[index]) return false
        }
        return true
    }

    fun setDjiv(value: String?) {
        _DjivState.value = value
    }

    fun setNiv(value: String?) {
        _NivState.value = value
    }

    fun setNv(value: Int?) {
        _NvState.value = value
    }

    override fun clear() {
        _DjivState.value = null
        _NivState.value = null
        _NvState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Pv(Djiv: IntArray, Niv: IntArray, Nv: Int): Float
}