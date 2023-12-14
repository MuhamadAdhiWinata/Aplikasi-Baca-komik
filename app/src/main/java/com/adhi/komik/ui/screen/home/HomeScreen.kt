package com.adhi.komik.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adhi.komik.model.Komik
import com.adhi.komik.ui.common.UiState
import com.adhi.komik.ui.components.EmptyContent
import com.adhi.komik.ui.components.ItemKomik
import com.adhi.komik.ui.components.SearchView
import com.adhi.komik.ui.theme.KomikTheme
import com.adhi.komik.R


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listKomik = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateKomikPlace(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listKomik: List<Komik>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        SearchView(
            query = query,
            onQueryChange = onQueryChange
        )
        if (listKomik.isNotEmpty()) {
            ListKomik(
                listKomik = listKomik,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyContent(
                contentText = stringResource(R.string.empty_data),
                modifier = Modifier
                    .testTag("empty_data")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListKomik(
    listKomik: List<Komik>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = contentPaddingTop),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_list")
    ) {
        items(listKomik, key = { it.id }) { item ->
            ItemKomik(
                id = item.id,
                photoUrl = item.photoUrl,
                title = item.name,
                rating = item.rating,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    KomikTheme {
        HomeContent(
            query = "",
            onQueryChange = {},
            listKomik = emptyList(),
            onFavoriteIconClicked = { _, _ ->  },
            navigateToDetail = {}
        )
    }
}