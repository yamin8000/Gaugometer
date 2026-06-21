/*
 *     Gaugometer/Gaugometer.gauge.main
 *     Gauge.kt Copyrighted by Yamin Siahmargooei at 2026/6/17
 *     Gauge.kt Last modified at 2026/6/17
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

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yamin8000.gauge.main.Gauge
import com.github.yamin8000.gauge.main.GaugeNumerics
import io.github.yamin8000.gaugometer.core.ui.theme.AppTheme
import io.github.yamin8000.gaugometer.core.util.ObserverEvent

@Composable
fun GaugeScreen(
    modifier: Modifier = Modifier,
    vm: GaugeViewModel = hiltViewModel()
) {
    val state = vm.state.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    ObserverEvent(vm.eventChannelFlow) { event ->
        when (event) {
            GaugeEvent.LocationPermissionNeeded -> {

            }

            GaugeEvent.RequestEnablingLocation -> {
                //todo show a dialog before starting the dialog
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
    }

    ObserverEvent(vm.errorChannelFlow) { error ->

    }

    AppGauge(
        modifier = modifier,
        rawSpeed = state.getSpeedKmh()
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        AppGauge(
            rawSpeed = 50f
        )
    }
}

@Composable
internal fun AppGauge(
    modifier: Modifier = Modifier,
    rawSpeed: Float
) {
    Surface(
        modifier = modifier,
        content = {
            Column(
                content = {
                    Text("Speed: $rawSpeed")
                    Gauge(
                        value = rawSpeed,
                        numerics = GaugeNumerics(
                            startAngle = 150,
                            sweepAngle = 240,
                            valueRange = 0f..220f,
                            smallTicksStep = 1,
                            bigTicksStep = 20
                        )
                    )
                }
            )
        }
    )
}