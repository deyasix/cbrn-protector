package ua.nure.cbrnprotector.base.popupplaceholders

import android.content.Context
import ua.nure.cbrnprotector.R

sealed class BaseConditions {

    abstract val value: Float
    abstract val name: String

    sealed class DamageDegree : BaseConditions() {
        data class MinorDamage(val context: Context) : DamageDegree() {
            override val value = 0.1f
            override val name = context.resources.getString(R.string.minor_damage)
        }

        data class BorderlineDamage(val context: Context) : DamageDegree() {
            override val value = 0.4f
            override val name = context.resources.getString(R.string.borderline_damage)
        }

        data class CriticalDamage(val context: Context) : DamageDegree() {
            override val value = 0.7f
            override val name = context.resources.getString(R.string.critical_damage)
        }

        data class CatastrophicDamage(val context: Context) : DamageDegree() {
            override val value = 1f
            override val name = context.resources.getString(R.string.catastrophic_damage)
        }
    }

    sealed class Probability : BaseConditions() {
        data class UnlikelyProbability(val context: Context) : Probability() {
            override val value = 0.2f
            override val name = context.resources.getString(R.string.unlikely_probability)
        }

        data class RareProbability(val context: Context) : Probability() {
            override val value = 0.4f
            override val name = context.resources.getString(R.string.rare_probability)
        }

        data class SometimesProbability(val context: Context) : Probability() {
            override val value = 0.6f
            override val name = context.resources.getString(R.string.sometimes_probability)
        }

        data class ProbableProbability(val context: Context) : Probability() {
            override val value = 0.8f
            override val name = context.resources.getString(R.string.probable_probability)
        }

        data class FrequentProbability(val context: Context) : Probability() {
            override val value = 1f
            override val name = context.resources.getString(R.string.frequent_probability)
        }
    }
}

fun String.getCondition(context: Context): BaseConditions {
    context.let {
        return when (this) {
            it.getString(R.string.minor_damage) -> BaseConditions.DamageDegree.MinorDamage(it)
            it.getString(R.string.borderline_damage) -> BaseConditions.DamageDegree.BorderlineDamage(it)
            it.getString(R.string.critical_damage) -> BaseConditions.DamageDegree.CriticalDamage(it)
            it.getString(R.string.catastrophic_damage) -> BaseConditions.DamageDegree.CatastrophicDamage(it)
            it.getString(R.string.unlikely_probability) -> BaseConditions.Probability.UnlikelyProbability(it)
            it.getString(R.string.rare_probability) -> BaseConditions.Probability.RareProbability(it)
            it.getString(R.string.sometimes_probability) -> BaseConditions.Probability.SometimesProbability(it)
            it.getString(R.string.probable_probability) -> BaseConditions.Probability.ProbableProbability(it)
            it.getString(R.string.frequent_probability) -> BaseConditions.Probability.FrequentProbability(it)
            else -> throw RuntimeException("No such condition")
        }
    }
}