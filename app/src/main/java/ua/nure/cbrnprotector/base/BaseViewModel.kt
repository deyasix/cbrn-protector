package ua.nure.cbrnprotector.base

import androidx.lifecycle.ViewModel
import ua.nure.cbrnprotector.domain.ColoredValuable

abstract class BaseViewModel : ViewModel() {
    abstract fun getResult(): ColoredValuable?
}