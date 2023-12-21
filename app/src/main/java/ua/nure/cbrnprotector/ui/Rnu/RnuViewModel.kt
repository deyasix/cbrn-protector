package ua.nure.cbrnprotector.ui.Rnu

import androidx.lifecycle.*
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.domain.Risk

class RnuViewModel : BaseViewModel() {

    private val _PyState = MutableLiveData<Float?>()
    val PyState: LiveData<Float?>
        get() = _PyState
    private val _VyState = MutableLiveData<Float?>()
    val VyState: LiveData<Float?>
        get() = _VyState

    override fun getResult(): ColoredValuable? {
        val Py = _PyState.value
        val Vy = _VyState.value
        val result = if (Py != null && Vy != null) Rny(Py, Vy) else return null
        return Risk.findByValue(result)
    }

    fun setPy(value: Float?) {
        _PyState.value = value
    }

    fun setVy(value: Float?) {
        _VyState.value = value
    }

    override fun clear() {
        _PyState.value = null
        _VyState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Rny(Py: Float, Vy: Float): Float
}