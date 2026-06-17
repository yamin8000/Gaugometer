/*
 *     Gaugometer/Gaugometer.gauge.main
 *     GaugeViewModel.kt Copyrighted by Yamin Siahmargooei at 2026/6/17
 *     GaugeViewModel.kt Last modified at 2026/6/17
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

import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GaugeViewModel @Inject constructor(
    private val locationManager: LocationManager,
) : ViewModel() {

    private var _state = MutableStateFlow(GaugeState())
    val state = _state.asStateFlow()

    private val locationListener = LocationListener { location ->
        _state.update {
            it.copy(
                rawSpeed = location.speed,
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    }

    init {
        _state.update { it.copy(isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) }
    }

    fun onAction(action: GaugeAction) {
        when (action) {
            GaugeAction.Refresh -> {
                if (state.value.hasLocationPermission) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0f,
                        locationListener
                    )
                }
            }
        }
    }
}