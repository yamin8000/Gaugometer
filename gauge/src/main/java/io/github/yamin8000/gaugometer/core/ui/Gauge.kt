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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yamin8000.gauge.main.Gauge
import com.github.yamin8000.gauge.main.GaugeNumerics
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.github.yamin8000.gaugometer.core.ui.components.GpsEnableRequestDialog
import io.github.yamin8000.gaugometer.core.ui.components.PermissionRationaleDialog
import io.github.yamin8000.gaugometer.core.ui.theme.AppTheme
import io.github.yamin8000.gaugometer.core.ui.theme.Sizes
import io.github.yamin8000.gaugometer.core.util.ObserverEvent
import io.github.yamin8000.gaugometer.core.util.Utility.getLocationPermissions
import kotlin.random.Random
import kotlin.random.nextInt

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GaugeScreen(
    modifier: Modifier = Modifier,
    vm: GaugeViewModel = hiltViewModel()
) {
    val state = vm.state.collectAsStateWithLifecycle().value


    val permissionState = rememberMultiplePermissionsState(
        permissions = getLocationPermissions()
    )
    if (!permissionState.allPermissionsGranted) {
        vm.onAction(GaugeAction.OnChangePermissionDialogState(permissionState.shouldShowRationale))
        var isShowingPermissionRationalDialog by remember {
            mutableStateOf(permissionState.shouldShowRationale)
        }
        if (!isShowingPermissionRationalDialog) {
            LaunchedEffect(Unit) {
                permissionState.launchMultiplePermissionRequest()
            }
        }
    } else vm.onAction(GaugeAction.OnPermissionGranted)

    val context = LocalContext.current
    ObserverEvent(vm.eventChannelFlow) { event ->
        when (event) {
            GaugeEvent.LocationPermissionNeeded -> {
                vm.onAction(GaugeAction.OnChangePermissionDialogState(true))
            }

            GaugeEvent.RequestEnablingLocation -> {
                vm.onAction(GaugeAction.OnChangeGpsDialogState(true))
            }
        }
    }

    ObserverEvent(vm.errorChannelFlow) { error ->

    }

    if (state.isShowingPermissionRequestDialog) {
        PermissionRationaleDialog(
            onDismissRequest = { vm.onAction(GaugeAction.OnChangePermissionDialogState(false)) },
            onRequest = { permissionState.launchMultiplePermissionRequest() }
        )
    }

    if (state.isShowingGpsEnableRequestDialog) {
        GpsEnableRequestDialog(
            onDismissRequest = { vm.onAction(GaugeAction.OnChangeGpsDialogState(false)) },
            onRequest = { context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
        )
    }

    GaugeContent(
        modifier = modifier,
        rawSpeed = state.getSpeedKmh(),
        hasLocationPermissions = state.hasLocationPermission,
        isGpsOn = state.isGpsEnabled,
        location = "${state.latitude}, ${state.longitude}",
        onRefresh = { vm.onAction(GaugeAction.Refresh) }
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        GaugeContent(
            rawSpeed = Random.nextInt(0..250).toFloat(),
            isGpsOn = Random.nextBoolean(),
            hasLocationPermissions = Random.nextBoolean(),
            location = "",
            onRefresh = {}
        )
    }
}

@Composable
internal fun GaugeContent(
    rawSpeed: Float,
    isGpsOn: Boolean,
    hasLocationPermissions: Boolean,
    location: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier.padding(Sizes.Medium),
                content = {
                    Text(
                        text = "GPS: $isGpsOn"
                    )
                    Text(
                        text = "Permission: $hasLocationPermissions"
                    )
                    Text(
                        text = location
                    )
                    Button(
                        onClick = onRefresh,
                        content = { Text(text = "Refresh") }
                    )
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