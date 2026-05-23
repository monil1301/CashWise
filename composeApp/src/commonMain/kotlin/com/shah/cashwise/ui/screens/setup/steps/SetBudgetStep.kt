package com.shah.cashwise.ui.screens.setup.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_budget_alert_caption
import cashwise.composeapp.generated.resources.setup_budget_category_label
import cashwise.composeapp.generated.resources.setup_budget_limit_label
import cashwise.composeapp.generated.resources.setup_budget_limit_placeholder
import com.shah.cashwise.domain.model.BudgetCategory
import com.shah.cashwise.ui.components.LabeledField
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.components.CategoryChip
import com.shah.cashwise.ui.screens.setup.model.icon
import com.shah.cashwise.ui.screens.setup.model.nameRes
import org.jetbrains.compose.resources.stringResource

/**
 * Step 4 of setup — "Set your first budget": a [CategoryChip] flow, the monthly
 * limit field, and the alert caption. Buttons live in [BudgetStepFooter] so
 * they stay pinned at the bottom of the layout.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SetBudgetStep(
    selectedCategory: BudgetCategory,
    monthlyLimit: String,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        LabeledField(label = stringResource(Res.string.setup_budget_category_label).uppercase()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BudgetCategory.entries.forEach { category ->
                    CategoryChip(
                        icon = category.icon(),
                        label = stringResource(category.nameRes()),
                        selected = category == selectedCategory,
                        onClick = { onAction(SetupAction.BudgetCategorySelected(category)) },
                    )
                }
            }
        }

        LabeledField(label = stringResource(Res.string.setup_budget_limit_label).uppercase()) {
            OutlinedTextField(
                value = monthlyLimit,
                onValueChange = { onAction(SetupAction.BudgetLimitChanged(it)) },
                leadingIcon = {
                    Text(
                        text = "₹",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                placeholder = {
                    Text(text = stringResource(Res.string.setup_budget_limit_placeholder))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(Res.string.setup_budget_alert_caption),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
