/*
 *     Gaugometer/Gaugometer.core.main
 *     Utility.kt Copyrighted by Yamin Siahmargooei at 2026/6/17
 *     Utility.kt Last modified at 2026/6/17
 *     This file is part of Gaugometer/Gaugometer.core.main.
 *     Copyright (C) 2026  Yamin Siahmargooei
 *
 *     Gaugometer/Gaugometer.core.main is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gaugometer/Gaugometer.core.main is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Gaugometer.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.yamin8000.gaugometer.core.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import io.github.yamin8000.gaugometer.core.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/** Prints [message] to logcat if app is in debug build */
fun log(
    message: String
) {
    if (BuildConfig.DEBUG) {
        Log.d(Constants.LOG_TAG, message)
    }
}

@Composable
fun <T> ObserverEvent(
    flow: Flow<T>,
    onEvent: suspend (T) -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}