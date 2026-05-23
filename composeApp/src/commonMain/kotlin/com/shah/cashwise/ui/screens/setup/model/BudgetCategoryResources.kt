package com.shah.cashwise.ui.screens.setup.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_budget_category_bills
import cashwise.composeapp.generated.resources.setup_budget_category_food
import cashwise.composeapp.generated.resources.setup_budget_category_groceries
import cashwise.composeapp.generated.resources.setup_budget_category_shopping
import cashwise.composeapp.generated.resources.setup_budget_category_transport
import com.shah.cashwise.domain.model.BudgetCategory
import org.jetbrains.compose.resources.StringResource

/** Localized display name for a [BudgetCategory] chip. */
fun BudgetCategory.nameRes(): StringResource = when (this) {
    BudgetCategory.FoodAndDining -> Res.string.setup_budget_category_food
    BudgetCategory.Groceries -> Res.string.setup_budget_category_groceries
    BudgetCategory.Transport -> Res.string.setup_budget_category_transport
    BudgetCategory.Shopping -> Res.string.setup_budget_category_shopping
    BudgetCategory.Bills -> Res.string.setup_budget_category_bills
}

/** Glyph shown alongside a [BudgetCategory] chip. */
fun BudgetCategory.icon(): ImageVector = when (this) {
    BudgetCategory.FoodAndDining -> Icons.Outlined.Restaurant
    BudgetCategory.Groceries -> Icons.Outlined.ShoppingCart
    BudgetCategory.Transport -> Icons.Outlined.DirectionsCar
    BudgetCategory.Shopping -> Icons.Outlined.ShoppingBag
    BudgetCategory.Bills -> Icons.Outlined.Receipt
}
