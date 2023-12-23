package ua.nure.cbrnprotector.ui.Cn

import androidx.lifecycle.*
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.utills.toIntArray

class CnViewModel : BaseViewModel() {
    private val _CiState = MutableLiveData<String?>()
    val CiState: LiveData<String?>
        get() = _CiState
    private val _NiState = MutableLiveData<String?>()
    val NiState: LiveData<String?>
        get() = _NiState

    override fun getResult(): ColoredValuable? {
        val Ci = _CiState.value?.toIntArray()
        val Ni = _NiState.value?.toIntArray()

        val result =
            if (Ci != null && Ni != null) Cn(Ci, Ni) else return null
        return object : ColoredValuable {
            override val color = R.color.result_low
            override val value = result.toFloat()
            override val nameResource: Int = 0
        }
    }

    override fun isValuesValid(): Boolean {
        val Ci = _CiState.value?.toIntArray()
        val Ni = _NiState.value?.toIntArray()
        if (Ci == null || Ni == null) return true
        return Ci.size == Ni.size
    }

    fun setCi(value: String?) {
        _CiState.value = value
    }

    fun setNi(value: String?) {
        _NiState.value = value
    }

    override fun clear() {
        _CiState.value = null
        _NiState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Cn(Ci: IntArray, Ni: IntArray): Int
}