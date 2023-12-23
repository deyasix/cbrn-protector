package ua.nure.cbrnprotector.ui.Kiob

import androidx.lifecycle.*
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable

class KiobViewModel : BaseViewModel() {
    private val _LifState = MutableLiveData<Float?>()
    val LifState: LiveData<Float?>
        get() = _LifState
    private val _LiobState = MutableLiveData<Float?>()
    val LiobState: LiveData<Float?>
        get() = _LiobState

    override fun getResult(): ColoredValuable? {
        val Lif = _LifState.value
        val Lob = _LiobState.value

        val result =
            if (Lif != null && Lob != null) Kiob(Lif, Lob) else return null
        return object : ColoredValuable {
            override val color: Int = R.color.result_low
            override val value: Float = result
            override val nameResource: Int = 0

        }
    }

    fun setLif(value: Float?) {
        _LifState.value = value
    }

    fun setLiob(value: Float?) {
        _LiobState.value = value
    }

    override fun clear() {
        _LifState.value = null
        _LiobState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Kiob(Lif: Float, Liob: Float): Float
}