package ua.nure.cbrnprotector.ui.Wpg

import androidx.lifecycle.*
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable

class WpgViewModel : BaseViewModel() {
    private val _WopState = MutableLiveData<Int?>()
    val WopState: LiveData<Int?>
        get() = _WopState
    private val _WzikzState = MutableLiveData<Int?>()
    val WzikzState: LiveData<Int?>
        get() = _WzikzState
    private val _WpozState = MutableLiveData<Int?>()
    val WpozState: LiveData<Int?>
        get() = _WpozState

    override fun getResult(): ColoredValuable? {
        val Wop = _WopState.value
        val Wzikz = _WzikzState.value
        val Wpoz = _WpozState.value
        val result =
            if (Wop != null && Wzikz != null && Wpoz != null) WPJ(Wop, Wzikz, Wpoz) else return null
        return object : ColoredValuable {
            override val color = R.color.result_low
            override val value = result.toFloat()
            override val nameResource: Int = 0
        }
    }

    fun setWop(value: Int?) {
        _WopState.value = value
    }

    fun setWzikz(value: Int?) {
        _WzikzState.value = value
    }

    fun setWpoz(value: Int?) {
        _WpozState.value = value
    }

    override fun clear() {
        _WpozState.value = null
        _WopState.value = null
        _WzikzState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun WPJ(Wop: Int, Wzikz: Int, Wpoz: Int): Int
}