/*
 *     Gaugometer/Gaugometer.gauge.main
 *     BasicEnableRequestDialog.kt Copyrighted by Yamin Siahmargooei at 2026/11/10
 *     BasicEnableRequestDialog.kt Last modified at 2026/11/10
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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.yamin8000.gaugometer.core.ui.theme.AppTheme
import io.github.yamin8000.gaugometer.core.ui.theme.Sizes
import io.github.yamin8000.gaugometer.guage.R

@Preview
@Composable
private fun Preview() {
    AppTheme {
        BasicEnableRequestDialogContent(
            onRequest = {},
            notice = { Text("Hello") }
        )
    }
}

@Composable
private fun BasicEnableRequestDialogContent(
    onRequest: () -> Unit,
    modifier: Modifier = Modifier,
    notice: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.padding(Sizes.Large),
        content = {
            Column(
                content = {
                    notice()
                    Button(
                        content = { Text(stringResource(R.string.ok)) },
                        onClick = onRequest
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BasicEnableRequestDialog(
    onDismissRequest: () -> Unit,
    onRequest: () -> Unit,
    modifier: Modifier = Modifier,
    notice: @Composable () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        content = {
            BasicEnableRequestDialogContent(
                onRequest = onRequest,
                notice = notice
            )
        }
    )
}

@Composable
internal fun BasicEnableRequestDialog(
    notice: String,
    onDismissRequest: () -> Unit,
    onRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicEnableRequestDialog(
        onDismissRequest = onDismissRequest,
        onRequest = onRequest,
        modifier = modifier,
        notice = {
            Text(
                text = notice
            )
        }
    )
}