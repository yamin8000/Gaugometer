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

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.yamin8000.gaugometer.core.domain.repository.PermissionCheckerRepository
import io.github.yamin8000.gaugometer.core.util.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GaugeViewModel @Inject constructor(
    private val locationManager: LocationManager,
    private val permissionChecker: PermissionCheckerRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log(throwable.stackTraceToString())
        scope.launch { errorChannel.send(throwable) }
    }

    private val scope: CoroutineScope = CoroutineScope(
        SupervisorJob() + viewModelScope.coroutineContext + exceptionHandler
    )

    private var _state = MutableStateFlow(GaugeState())
    val state = _state.asStateFlow()

    private var eventChannel = Channel<GaugeEvent>()
    val eventChannelFlow = eventChannel.receiveAsFlow()

    private var errorChannel = Channel<Throwable>()
    val errorChannelFlow = errorChannel.receiveAsFlow()

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
        if (!state.value.isGpsEnabled) {
            scope.launch { eventChannel.send(GaugeEvent.RequestEnablingLocation) }
        }
    }

    fun onAction(action: GaugeAction) {
        when (action) {
            GaugeAction.Refresh -> {
                if (state.value.hasLocationPermission) {
                    if (
                        permissionChecker.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
                        permissionChecker.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {
                        refreshLocation()
                    } else {
                        scope.launch { eventChannel.send(GaugeEvent.LocationPermissionNeeded) }
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun refreshLocation() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                500,
                .5f,
                locationListener
            )
        } catch (e: Exception) {
            scope.launch { errorChannel.send(e) }
        }
    }
}