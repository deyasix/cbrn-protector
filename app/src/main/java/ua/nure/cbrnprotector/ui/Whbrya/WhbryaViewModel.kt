package ua.nure.cbrnprotector.ui.Whbrya

import androidx.lifecycle.*
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable

class WhbryaViewModel : BaseViewModel() {
    private val _WvoState = MutableLiveData<Int?>()
    val WvoState: LiveData<Int?>
        get() = _WvoState
    private val _WpgState = MutableLiveData<Int?>()
    val WpgState: LiveData<Int?>
        get() = _WpgState
    private val _WlnState = MutableLiveData<Int?>()
    val WlnState: LiveData<Int?>
        get() = _WlnState

    override fun getResult(): ColoredValuable? {
        val Wvo = _WvoState.value
        val Wpg = _WpgState.value
        val Wln = _WlnState.value
        val result =
            if (Wvo != null && Wpg != null && Wln != null) WHBRYA(Wvo, Wpg, Wln) else return null
        return object : ColoredValuable {
            override val color = R.color.result_low
            override val value = result.toFloat()
            override val nameResource: Int = 0

        }
    }

    fun setWvo(value: Int?) {
        _WvoState.value = value
    }

    fun setWpg(value: Int?) {
        _WpgState.value = value
    }

    fun setWln(value: Int?) {
        _WlnState.value = value
    }

    override fun clear() {
        _WvoState.value = null
        _WpgState.value = null
        _WlnState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun WHBRYA(Wvo: Int, Wpg: Int, Wln: Int): Int
}