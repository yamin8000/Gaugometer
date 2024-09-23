/*
 *     Gaugometer/Gaugometer.app.main
 *     HomeViewModel.kt Copyrighted by Yamin Siahmargooei at 2024/8/11
 *     HomeViewModel.kt Last modified at 2024/8/11
 *     This file is part of Gaugometer/Gaugometer.app.main.
 *     Copyright (C) 2024  Yamin Siahmargooei
 *
 *     Gaugometer/Gaugometer.app.main is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gaugometer/Gaugometer.app.main is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Gaugometer.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.yamin8000.gaugometer.content

import android.annotation.SuppressLint
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("MissingPermission")
internal class HomeViewModel(
    locationManager: LocationManager,
) : ViewModel() {

    private var _isEnabled = MutableStateFlow(false)
    val isEnabled = _isEnabled.asStateFlow()

    private var _speed = MutableStateFlow(0f)
    val speed = _speed.asStateFlow()

    private var _latitude = MutableStateFlow(0.0)
    val latitude = _latitude.asStateFlow()

    private var _longitude = MutableStateFlow(0.0)
    val longitude = _longitude.asStateFlow()

    private val locationListener = LocationListener { location ->
        _speed.value = location.speed
        _latitude.value = location.latitude
        _longitude.value = location.longitude
    }

    init {
        _isEnabled.value = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )
    }
}