package ua.nure.cbrnprotector.ui.Kn

import androidx.lifecycle.*
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.base.BaseViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable
import ua.nure.cbrnprotector.utills.toIntArray

class KnViewModel : BaseViewModel() {
    private val _WiState = MutableLiveData<String?>()
    val WiState: LiveData<String?>
        get() = _WiState
    private val _AiState = MutableLiveData<String?>()
    val AiState: LiveData<String?>
        get() = _AiState
    private val _NiumState = MutableLiveData<String?>()
    val NiumState: LiveData<String?>
        get() = _NiumState

    override fun getResult(): ColoredValuable? {
        val Wi = _WiState.value?.toIntArray()
        val Ai = _AiState.value?.toIntArray()
        val Nium = _NiumState.value?.toIntArray()

        val result =
            if (Wi != null && Ai != null && Nium != null) Kn(Wi, Ai, Nium) else return null
        return object : ColoredValuable {
            override val color = R.color.result_low
            override val value = result.toFloat()
            override val nameResource: Int = 0
        }
    }

    override fun isValuesValid(): Boolean {
        val Wi = _WiState.value?.toIntArray()
        val Ai = _AiState.value?.toIntArray()
        val Nium = _NiumState.value?.toIntArray()
        if (Wi == null || Ai == null || Nium == null) return true
        if (!(Wi.size == Ai.size && Wi.size == Nium.size)) return false
        Wi.forEachIndexed { index, item ->
            if (item < Ai[index] || Nium[index] > (item / Ai[index])) return false
        }
        return true
    }

    fun setWi(value: String?) {
        _WiState.value = value
    }

    fun setAi(value: String?) {
        _AiState.value = value
    }

    fun setNium(value: String?) {
        _NiumState.value = value
    }

    override fun clear() {
        _WiState.value = null
        _AiState.value = null
        _NiumState.value = null
    }

    init {
        System.loadLibrary("cbrnprotector")
    }

    private external fun Kn(Wi: IntArray, Ai: IntArray, Nium: IntArray): Int
}