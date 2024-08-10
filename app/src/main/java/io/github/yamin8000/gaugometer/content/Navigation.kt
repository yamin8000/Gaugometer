/*
 *     Gaugometer/Gaugometer.app.main
 *     Navigation.kt Copyrighted by Yamin Siahmargooei at 2024/8/11
 *     Navigation.kt Last modified at 2024/8/11
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

import android.content.Context
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.yamin8000.gaugometer.Nav

@Composable
internal fun MainNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val locationManager = remember {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    val homeVM: HomeViewModel = viewModel(factory = viewModelFactory {
        initializer {
            HomeViewModel(
                locationManager = locationManager
            )
        }
    })

    NavHost(
        navController = navController,
        startDestination = Nav.Route.Home(),
        builder = {
            composable(Nav.Route.Home()) {
                HomeScreen(
                    modifier = modifier,
                    vm = homeVM
                )
            }
        }
    )
}