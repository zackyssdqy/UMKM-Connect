package com.dicoding.warceng.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.warceng.di.Injection
import com.dicoding.warceng.model.OrderMenu
import com.dicoding.warceng.ui.ViewModelFactory
import com.dicoding.warceng.ui.common.UiState
import com.dicoding.warceng.R
import com.dicoding.warceng.ui.components.CategoriesItem
import com.dicoding.warceng.ui.components.MenuItem
import com.dicoding.warceng.ui.components.SearchBar
import com.dicoding.warceng.ui.components.SectionText
import com.dicoding.warceng.ui.theme.coffeeColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
    navigateToCategory: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getAllMenu()
            }
            is UiState.Error -> {}
            is UiState.Success -> {
                HomeContent(
                    orderMenu = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    navigateToCategory = navigateToCategory
                )
            }
        }
    }
}

@Composable
fun HeaderHome(
    image: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(top = 20.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "avater",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(MaterialTheme.shapes.extraLarge)
        )
        Column(
            modifier = Modifier
                .padding(
                    vertical = 5.dp,
                    horizontal = 5.dp
                )
        ) {
            Text(text = "Welcome back")
            Text(
                text = "Hasan Albanna",
                color = coffeeColor,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}

@Composable
fun HomeContent(
    orderMenu: List<OrderMenu>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    navigateToCategory: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 24.dp
            )
    ) {
        HeaderHome(R.drawable.hasanalbana)
        SearchBar(query = "", onQueryChange = {String}, modifier = Modifier.padding(top = 15.dp))
        SectionText(title = "Recomendation"){
            Column(
                Modifier.padding(top = 15.dp)
            ) {
                Row{
                    CategoriesItem(image = R.drawable.souvenir, title = "Kuliner", modifier = Modifier.clickable {
                        navigateToCategory("Drink")
                    })
                    Spacer(modifier = Modifier.width(5.dp))
                    CategoriesItem(image = R.drawable.fashion, title = "Fashion", modifier = Modifier.clickable {
                        navigateToCategory("Dessert")
                    })
                }
                Row(Modifier.padding(top = 10.dp)){
                    CategoriesItem(image = R.drawable.otomotif, title = "Otomotif", modifier = Modifier.clickable {
                        navigateToCategory("Food")
                    })
                    Spacer(modifier = Modifier.width(5.dp))
                    CategoriesItem(image = R.drawable.souvenir, title = "Souvenir", modifier = Modifier.clickable {
                        navigateToCategory("Appetizer")
                    })
                }
            }
        }
        SectionText(title = "Cek yang menarik di U-Con") {
            LazyRow(
                modifier = modifier.padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ){
                items(orderMenu){data->
                    MenuItem(
                        image = data.menu.image,
                        title = data.menu.title,
                        price = data.menu.price,
                        modifier = modifier.clickable {
                            navigateToDetail(data.menu.id)
                        },
                    )
                }
            }
        }
        SectionText(title = "Best-Selling Menu") {
            LazyRow(
                modifier = modifier.padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ){
                items(orderMenu){data->
                    MenuItem(
                        image = data.menu.image,
                        title = data.menu.title,
                        price = data.menu.price,
                        modifier = modifier.clickable {
                            navigateToDetail(data.menu.id)
                        }
                    )
                }
            }
        }
    }
}

