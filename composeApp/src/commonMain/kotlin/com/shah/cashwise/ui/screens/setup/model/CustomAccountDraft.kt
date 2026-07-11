package com.shah.cashwise.ui.screens.setup.model

import com.shah.cashwise.domain.model.CustomAccountIcon

/**
 * The form result emitted when the user confirms the add-custom-account form.
 * [startingBalance] is the raw text typed (may be blank — the field is optional).
 */
data class CustomAccountDraft(
    val name: String,
    val icon: CustomAccountIcon,
    val startingBalance: String,
)
