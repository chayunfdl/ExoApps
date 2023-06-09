package com.chayun.jetpackcompose.exoapplication.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chayun.jetpackcompose.exoapplication.data.ExosRepository
import com.chayun.jetpackcompose.exoapplication.ui.component.CardItems
import com.chayun.jetpackcompose.exoapplication.ui.component.MemberHeader
import com.chayun.jetpackcompose.exoapplication.ui.component.ScrollTopButton
import com.chayun.jetpackcompose.exoapplication.ui.component.SearchsBar
import com.chayun.jetpackcompose.exoapplication.ui.theme.ExoApplicationTheme
import com.chayun.jetpackcompose.exoapplication.viewmodel.HomeViewModel
import com.chayun.jetpackcompose.exoapplication.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(
        ExosRepository()
    )),
    navigateToDetail: (String) -> Unit,
) {
    val groupedExos by viewModel.groupedExos.collectAsState()
    val query by viewModel.query
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                SearchsBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                )
            }
            groupedExos.forEach { (initial, exos) ->
                stickyHeader {
                    MemberHeader(initial)
                }
                items(exos, key = { it.id }) { exo ->
                    CardItems(
                        id = exo.id,
                        name = exo.name,
                        photoUrl = exo.photoUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100)),
                        navigateToDetail = navigateToDetail
                    )

                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ExoApplicationTheme {
        HomeScreen(navigateToDetail = {})
    }
}