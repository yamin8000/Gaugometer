/*
 *     Gaugometer/Gaugometer.gauge.main
 *     GaugeState.kt Copyrighted by Yamin Siahmargooei at 2026/6/17
 *     GaugeState.kt Last modified at 2026/6/17
 *     This file is part of Gaugometer/Gaugometer.gauge.main.
 *     Copyright (C) 2026  Yamin Siahmargooei
 *
 *     Gaugometer/Gaugometer.gauge.main is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gaugometer/Gaugometer.gauge.main is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Gaugometer.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.yamin8000.gaugometer.core.ui

data class GaugeState(
    val isGpsEnabled: Boolean = false,
    val rawSpeed: Float = 0f,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val hasLocationPermission: Boolean = false
)
