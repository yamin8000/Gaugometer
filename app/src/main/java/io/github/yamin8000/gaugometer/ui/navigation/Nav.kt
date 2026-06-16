package io.github.yamin8000.gaugometer.ui.navigation

object Nav {
    sealed interface Route {
        data object Home : Route
        data object Gaugeometer : Route

        operator fun invoke() = this.toString()
    }
}