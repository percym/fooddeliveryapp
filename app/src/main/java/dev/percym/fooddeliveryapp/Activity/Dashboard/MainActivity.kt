package dev.percym.fooddeliveryapp.Activity.Dashboard

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import dev.percym.fooddeliveryapp.Activity.BaseActivity
import dev.percym.fooddeliveryapp.Domain.BannerModel
import dev.percym.fooddeliveryapp.Domain.CategoryModel
import dev.percym.fooddeliveryapp.ViewModel.MainViewModel

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()

        }
    }

    @Composable
    fun MainScreen() {
        val scaffoldState = rememberScaffoldState()
        val banners = remember { mutableStateListOf<BannerModel>() }
        val categories = remember { mutableStateListOf<CategoryModel>() }

        var showBannerLoading by remember { mutableStateOf(true) }
        var showCategoryLoading by remember { mutableStateOf(true) }

        val viewModel = remember { MainViewModel() }

        // Only set up observers ONCE using DisposableEffect
        androidx.compose.runtime.DisposableEffect(Unit) {
            val bannerObserver = androidx.lifecycle.Observer<List<BannerModel>> { bannerList ->
                Log.d("MainActivity", "Banners loaded: ${bannerList.size}")
                banners.clear()
                banners.addAll(bannerList)
                showBannerLoading = false
            }

            val categoryObserver = androidx.lifecycle.Observer<List<CategoryModel>> { categoryList ->
                Log.d("MainActivity", "Categories loaded: ${categoryList.size}")
                categories.clear()
                categories.addAll(categoryList)
                showCategoryLoading = false  // ← Always set false after load
            }

            viewModel.loadBanner().observe(this@MainActivity, bannerObserver)
            viewModel.loadCategory().observe(this@MainActivity, categoryObserver)

            onDispose {
                viewModel.loadBanner().removeObserver(bannerObserver)
                viewModel.loadCategory().removeObserver(categoryObserver)
            }
        }

        Scaffold(
            bottomBar = { MyBottomBar() },
            scaffoldState = scaffoldState
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                item { TopBar() }
                item { Banner(banners, showBannerLoading) }
                item { Search() }
                item { CategorySection(categories, showCategoryLoading) }
            }
        }
    }
}


