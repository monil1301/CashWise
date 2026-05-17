package com.shah.cashwise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun <T> AppPager(
    pages: List<T>,
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    userScrollEnabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(),
    pageSpacing: Dp = 0.dp,
    pageContent: @Composable (item: T) -> Unit,
) {
    if (pages.isEmpty()) return

    val initialPage = currentPage.coerceIn(0, pages.lastIndex)
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { pages.size },
    )

    LaunchedEffect(currentPage, pages.size) {
        val safePage = currentPage.coerceIn(0, pages.lastIndex)
        if (pagerState.currentPage != safePage) {
            pagerState.animateScrollToPage(safePage)
        }
    }

    LaunchedEffect(pagerState, pages.size) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                onPageChange(page)
            }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = userScrollEnabled,
        contentPadding = contentPadding,
        pageSpacing = pageSpacing,
    ) { page ->
        pageContent(pages[page])
    }
}

@Preview
@Composable
private fun AppPagerPreview() {
    CashWiseTheme {
        AppPager(
            pages = listOf("Track expenses", "Set budgets", "See insights"),
            currentPage = 0,
            onPageChange = {},
            modifier = Modifier.fillMaxSize(),
        ) { title ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}
