package ua.nure.cbrnprotector.ui.Wln

import androidx.lifecycle.*
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable

class WlnViewModel : BaseViewModel() {
    private val _WosState = MutableLiveData<Int?>()
    val WosState: LiveData<Int?>
        get() = _WosState
    private val _WovtState = MutableLiveData<Int?>()
    val WovtState: LiveData<Int?>
        get() = _WovtState
    private val _WmState = MutableLiveData<Int?>()
    val WmState: LiveData<Int?>
        get() = _WmState

    override fun getResult(): ColoredValuable? {
        val Wos = _WosState.value
        val Wovt = _WovtState.value
        val Wm = _WmState.value
        val result =
            if (Wos != null && Wovt != null && Wm != null) WLN(Wos, Wovt, Wm) else return null
        return object : ColoredValuable {
            override val color = R.color.result_low
            override val value = result.toFloat()
            override val nameResource: Int = 0
        }
    }

    fun setWos(value: Int?) {
        _WosState.value = value
    }

    fun setWovt(value: Int?) {
        _WovtState.value = value
    }

    fun setWm(value: Int?) {
        _WmState.value = value
    }

    override fun clear() {
        _WosState.value = null
        _WovtState.value = null
        _WmState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun WLN(Wos: Int, Wovt: Int, Wm: Int): Int
}