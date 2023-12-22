package ua.nure.cbrnprotector.ui.Vrhz

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class VrhzViewModel : BaseViewModel() {
    private val _N200State = MutableLiveData<String?>()
    val N200State: LiveData<String?>
        get() = _N200State
    private val _N300State = MutableLiveData<String?>()
    val N300State: LiveData<String?>
        get() = _N300State
    private val _NosState = MutableLiveData<Int?>()
    val NosState: LiveData<Int?>
        get() = _NosState

    override fun getResult(): ColoredValuable? {
        if(_N200State.value.isNullOrBlank() || _N300State.value.isNullOrBlank() || _NosState.value == null) return null
        val n200Array = parseString(_N200State.value)!!
        val n300Array = parseString(_N300State.value)!!
        val N200 = N200(n200Array, n200Array.size)
        val N300 = N300(n300Array, n300Array.size)
        val Nos = _NosState.value
        val result = if (Nos != null) Vrhz(N200, N300, Nos) else return null
        return Risk.findByValue(result)
    }

    fun setN200(value: String?) {
        _N200State.value = value
    }

    fun setN300(value: String?) {
        _N300State.value = value
    }

    fun setNos(value: Int?) {
        _NosState.value = value
    }

    override fun isValuesValid(): Boolean {
        try {
            if(_N200State.value.isNullOrBlank() || _N300State.value.isNullOrBlank() || _NosState.value == null) return true
            val n200Array = parseString(_N200State.value)!!
            val n300Array = parseString(_N300State.value)!!
            val N200 = N200(n200Array, n200Array.size)
            val N300 = N300(n300Array, n300Array.size)
            val Nos = _NosState.value ?: return true
            return (N200 + N300) < Nos
        } catch(e: Exception) {
            return false
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
        _N200State.value = null
        _N300State.value = null
        _NosState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Vrhz(N200: Int, N300: Int, Nos: Int): Float
    private external fun N200(N200: IntArray, Nrhz: Int): Int
    private external fun N300(N300: IntArray, Nrhz: Int): Int
}