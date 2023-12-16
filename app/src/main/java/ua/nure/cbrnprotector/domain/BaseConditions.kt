package ua.nure.cbrnprotector.domain

import android.content.Context
import timber.log.Timber
import ua.nure.cbrnprotector.R

interface Valuable {
    val value: Float
    val nameResource: Int
    fun equals(other: Valuable): Boolean {
        return other.value == this.value && other.nameResource == this.nameResource
    }
}

interface ColoredValuable : Valuable {
    val color: Int
}

enum class DamageDegree : Valuable {
    MINOR_DAMAGE {
        override val value = 0.1f
        override val nameResource = R.string.minor_damage
    },
    BORDERLINE_DAMAGE {
        override val value = 0.4f
        override val nameResource = R.string.borderline_damage
    },
    CRITICAL_DAMAGE {
        override val value = 0.7f
        override val nameResource = R.string.critical_damage
    },
    CATASTROPHIC_DAMAGE {
        override val value = 1f
        override val nameResource = R.string.catastrophic_damage
    }
}

enum class Probability : Valuable {
    UNLIKELY_PROBABILITY {
        override val value = 0.2f
        override val nameResource = R.string.unlikely_probability
    },
    RARE_PROBABILITY {
        override val value = 0.4f
        override val nameResource = R.string.rare_probability
    },
    SOMETIMES_PROBABILITY {
        override val value = 0.6f
        override val nameResource = R.string.sometimes_probability
    },
    PROBABLE_PROBABILITY {
        override val value = 0.8f
        override val nameResource = R.string.probable_probability
    },
    FREQUENT_PROBABILITY {
        override val value = 1f
        override val nameResource = R.string.frequent_probability
    }
}

enum class Risk : ColoredValuable {
    LOW_RISK {
        override val value = 0.4f
        override val nameResource = R.string.low_level
        override val color = R.color.result_low
    },
    MEDIUM_RISK {
        override val value = 0.6f
        override val nameResource = R.string.medium_level
        override val color = R.color.result_medium
    },
    HIGH_RISK {
        override val value = 0.8f
        override val nameResource = R.string.high_level
        override val color = R.color.result_high
    },
    VERY_HIGH_RISK {
        override val value = 1f
        override val nameResource = R.string.very_high_level
        override val color = R.color.result_very_high
    };

    companion object {
        fun findByValue(value: Float): Risk? {
            return Risk.values().find {
                it.value >= value
            }
        }
    }
}

fun String.getCondition(context: Context): Valuable {
    Timber.d(this)
    mutableListOf<Valuable>().apply {
        addAll(DamageDegree.values())
        addAll(Probability.values())
        addAll(Risk.values())
        find { context.getString(it.nameResource) == this@getCondition }?.let { return it }
        throw RuntimeException("No such condition")
    }
}