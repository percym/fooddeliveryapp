package dev.percym.fooddeliveryapp.Activity.Dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import dev.percym.fooddeliveryapp.Domain.CategoryModel
import dev.percym.fooddeliveryapp.R

@Composable
fun CategorySection(categories: SnapshotStateList<CategoryModel>, showCategoryLoading: Boolean) {
    Text(
        text = "Choose a category",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
    if (showCategoryLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val rows = categories.chunked(3)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEachIndexed { index, categoryModel ->
                        CategoryItem(
                            category = categoryModel,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            onItemClick = {}
                        )
                    }
                    if (row.size < 3) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryModel, modifier: Modifier = Modifier, onItemClick: () -> Unit) {
    val context = LocalContext.current
    val imageUrl = category.ImagePath?.trim() ?: ""

    Log.d("CategoryItem", "category='${category.Name}' url='$imageUrl' (empty=${imageUrl.isEmpty()})")

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(imageUrl.ifEmpty { null })
            .crossfade(true)
            .build()
    )
    val state = painter.state

    LaunchedEffect(state) {
        when (state) {
            is AsyncImagePainter.State.Success -> Log.d("CategoryItem", "✓ image loaded for '${category.Name}'")
            is AsyncImagePainter.State.Error -> Log.e("CategoryItem", "✗ image load failed for '${category.Name}': ${state.result.throwable?.message}")
            else -> {}
        }
    }

    Column(
        modifier = modifier
            .background(color = colorResource(R.color.lightOrange), shape = RoundedCornerShape(13.dp))
            .clickable(onClick = onItemClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
            when (state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
                is AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(color = colorResource(R.color.white), shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No\nimage", fontWeight = FontWeight.Medium, fontSize = 11.sp)
                    }
                }
                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = painter,
                        contentDescription = category.Name,
                        modifier = Modifier.size(80.dp)
                    )
                }
                else -> {}
            }
        }

        Text(
            text = category.Name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}