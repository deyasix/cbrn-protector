package ua.nure.cbrnprotector.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    open fun getResult(): Float {
        return 0f
    }
}