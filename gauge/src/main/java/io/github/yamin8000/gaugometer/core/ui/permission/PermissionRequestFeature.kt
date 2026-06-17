/*
 *     Gaugometer/Gaugometer.gauge.main
 *     PermissionRequestFeature.kt Copyrighted by Yamin Siahmargooei at 2026/6/17
 *     PermissionRequestFeature.kt Last modified at 2026/6/17
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

package io.github.yamin8000.gaugometer.core.ui.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun PermissionRequestFeature(
    onPermissionGranted: () -> Unit
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

private fun getLocationPermissions() = listOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)