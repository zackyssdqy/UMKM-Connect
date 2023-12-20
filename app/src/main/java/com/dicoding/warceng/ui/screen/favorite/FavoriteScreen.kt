package com.dicoding.warceng.ui.screen.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.warceng.R
import com.dicoding.warceng.di.Injection
import com.dicoding.warceng.model.Umkm
import com.dicoding.warceng.ui.ViewModelFactory
import com.dicoding.warceng.ui.common.UiState
import com.dicoding.warceng.ui.components.CategoryMenuItem
import com.dicoding.warceng.ui.screen.category.CategoryContent
import com.dicoding.warceng.ui.screen.detail.DetailContent
import com.dicoding.warceng.ui.screen.ui.theme.SubmissionJetpackComposeTheme
import com.dicoding.warceng.ui.theme.WarcengAppTheme

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())

    ),
    navigateToDetail: (Long) -> Unit,
    navigateBack: () -> Unit

    ) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteUmkm()
            }

            is UiState.Success -> {
                FavoriteContent(
                    umkm = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onBackClick = navigateBack,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    umkm: List<Umkm>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onBackClick: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        if (umkm.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum Ada UMKM Favorite",
                        fontSize = 20.sp
                    )
                }
            }
        } else {
            items(umkm) { data ->
                CategoryMenuItem(
                    image = data.image,
                    title = data.title,
                    location = data.location,
                    modifier = modifier.clickable {
                        navigateToDetail(data.id)
                    },
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FavoriteContenttPrev() {
//    WarcengAppTheme {
//        FavoriteContent(
//
//        )
//    }
//}