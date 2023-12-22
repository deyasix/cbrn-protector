package ua.nure.cbrnprotector.ui.Prhz

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class PrhzViewModel : BaseViewModel() {
    private val _DjirhzState = MutableLiveData<String?>()
    val DjirhzState: LiveData<String?>
        get() = _DjirhzState

    private val _NirhzState = MutableLiveData<String?>()
    val NirhzState: LiveData<String?>
        get() = _NirhzState

    override fun getResult(): ColoredValuable? {
        val Djirhz = parseString(_DjirhzState.value.orEmpty())
        val Nirhz = parseString(_NirhzState.value.orEmpty())
        val result = if (Djirhz != null && Nirhz != null) Prhz(Djirhz, Nirhz, Djirhz.size) else return null
        return Risk.findByValue(result)
    }

    fun setDjirhz(djirhz: String) {
        _DjirhzState.value = djirhz
    }

    fun setNirhz(nirhz: String) {
        _NirhzState.value = nirhz
    }

    override fun isValuesValid(): Boolean {
        return try {
            val val1 = parseString(_DjirhzState.value)
            val val2 = parseString(_NirhzState.value)
            val isSizeEqually = val1?.size == val2?.size
            val1?.forEachIndexed { index, item ->
                if (val2?.get(index) != null) {
                    if (item >= val2[index])
                        return false
                } else {
                    return false
                }
            }
            return isSizeEqually
        } catch (e: Exception) {
            false
        }
    }

    fun parseString(str: String?): IntArray? {
        if (str.isNullOrBlank()) return null
        val string = str.split(",")
        var values = intArrayOf()
        string.forEach {
            if (it.isDigitsOnly()) values += it.toInt() else throw Exception()
        }
        return values
    }

    override fun clear() {
        _DjirhzState.value = null
        _NirhzState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Prhz(Prhz: IntArray, Vrhz: IntArray, Nrhz: Int): Float
}