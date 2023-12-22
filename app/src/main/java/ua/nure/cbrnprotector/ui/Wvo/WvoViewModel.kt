package ua.nure.cbrnprotector.ui.Wvo

import androidx.lifecycle.*
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable

class WvoViewModel : BaseViewModel() {
    private val _WrhrState = MutableLiveData<Int?>()
    val WrhrState: LiveData<Int?>
        get() = _WrhrState
    private val _WrhkState = MutableLiveData<Int?>()
    val WrhkState: LiveData<Int?>
        get() = _WrhkState
    private val _WlnState = MutableLiveData<Int?>()
    val WlnState: LiveData<Int?>
        get() = _WlnState

    override fun getResult(): ColoredValuable? {
        val Wrhr = _WrhrState.value
        val Wrhk = _WrhkState.value
        val Wln = _WlnState.value
        val result =
            if (Wrhr != null && Wrhk != null && Wln != null) WVO(Wrhr, Wrhk, Wln) else return null
        return object : ColoredValuable {
            override val color = R.color.result_low
            override val value = result.toFloat()
            override val nameResource: Int = 0
        }
    }

    fun setWrhr(value: Int?) {
        _WrhrState.value = value
    }

    fun setWrhk(value: Int?) {
        _WrhkState.value = value
    }

    fun setWln(value: Int?) {
        _WlnState.value = value
    }

    override fun clear() {
        _WrhrState.value = null
        _WrhkState.value = null
        _WlnState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun WVO(Wrhr: Int, Wrhk: Int, W0: Int): Int
}