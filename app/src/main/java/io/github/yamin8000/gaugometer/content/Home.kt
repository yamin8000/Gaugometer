/*
 *     Gaugometer/Gaugometer.app.main
 *     Home.kt Copyrighted by Yamin Siahmargooei at 2024/8/11
 *     Home.kt Last modified at 2024/8/11
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

import android.Manifest
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import com.github.yamin8000.gauge.main.Gauge
import com.github.yamin8000.gauge.main.GaugeNumerics
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.github.yamin8000.gaugometer.R

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel
) {
    Surface(
        modifier = modifier,
        content = {
            PermissionRequestFeature {
                val context = LocalContext.current
                val isEnabled = vm.isEnabled.collectAsState().value
                EnableGpsFeature(isEnabled, context)
                Column {
                    val speed = vm.speed.collectAsState().value
                    Text("Access Granted!")
                    Text("Speed: $speed")
                    Text("Longitude: ${vm.longitude.collectAsState().value}")
                    Text("Latitude: ${vm.latitude.collectAsState().value}")
                    Gauge(
                        value = speed,
                        numerics = GaugeNumerics(
                            startAngle = 150,
                            sweepAngle = 240,
                            valueRange = 0f..220f,
                            smallTicksStep = 1,
                            bigTicksStep = 20
                        ),
                    )
                }
            }
        }
    )
}

@Composable
private fun EnableGpsFeature(
    isEnabled: Boolean,
    context: Context
) {
    if (!isEnabled) {
        //todo show a dialog before starting the dialog
        val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(context, settingsIntent, bundleOf())
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionRequestFeature(
    onPermissionGranted: @Composable () -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = getLocationPermissions()
    )
    if (!permissionState.allPermissionsGranted) {
        var isShowingPermissionRationalDialog by remember {
            mutableStateOf(permissionState.shouldShowRationale)
        }
        if (isShowingPermissionRationalDialog) {
            PermissionRationaleDialog(
                onRequest = { permissionState.launchMultiplePermissionRequest() },
                onDismissRequest = {
                    isShowingPermissionRationalDialog = false
                    permissionState.launchMultiplePermissionRequest()
                }
            )
        } else LaunchedEffect(Unit) { permissionState.launchMultiplePermissionRequest() }
    } else onPermissionGranted()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PermissionRationaleDialog(
    onDismissRequest: () -> Unit,
    onRequest: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Surface(
                modifier = Modifier.padding(16.dp),
                content = {
                    Column(
                        content = {
                            Text(stringResource(R.string.location_permission_rational))
                            Button(
                                content = { Text(stringResource(R.string.ok)) },
                                onClick = onRequest
                            )
                        }
                    )
                }
            )
        }
    )
}

private fun getLocationPermissions() = listOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)
