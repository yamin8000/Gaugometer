/*
 *     Gaugometer/Gaugometer.gauge.main
 *     PermissionRationaleDialog.kt Copyrighted by Yamin Siahmargooei at 2026/11/10
 *     PermissionRationaleDialog.kt Last modified at 2026/11/10
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

package io.github.yamin8000.gaugometer.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.yamin8000.gaugometer.core.ui.theme.AppTheme
import io.github.yamin8000.gaugometer.guage.R

@Preview
@Composable
private fun Preview() {
    AppTheme {
        PermissionRationaleDialog(
            onDismissRequest = {},
            onRequest = {}
        )
    }
}

@Composable
internal fun PermissionRationaleDialog(
    onDismissRequest: () -> Unit,
    onRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicEnableRequestDialog(
        modifier = modifier,
        notice = stringResource(R.string.location_permission_rational),
        onRequest = onRequest,
        onDismissRequest = onDismissRequest
    )
}