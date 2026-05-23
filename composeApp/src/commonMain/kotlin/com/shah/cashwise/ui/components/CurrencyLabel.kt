package com.shah.cashwise.ui.components

import androidx.compose.runtime.Composable
import com.shah.cashwise.domain.model.Currency
import org.jetbrains.compose.resources.stringResource

/** Human-readable currency label, e.g. "INR — Indian Rupee". */
@Composable
internal fun Currency.label(): String =
    "$code — ${stringResource(displayNameRes)}"
