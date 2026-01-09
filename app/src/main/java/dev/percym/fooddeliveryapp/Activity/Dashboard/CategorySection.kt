package dev.percym.fooddeliveryapp.Activity.Dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
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
    } else if (categories.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No categories found",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { categoryModel ->
                        Box(modifier = Modifier.weight(1f)) {
                            CategoryItem(
                                category = categoryModel,
                                modifier = Modifier.fillMaxWidth(),
                                onItemClick = {}
                            )
                        }
                    }
                    repeat(3 - row.size) {
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
    val imageUrl = category.ImagePath ?: ""

    Log.d("CategoryItem", "Category: ${category.Name}, URL: $imageUrl")

    Column(
        modifier = modifier
            .background(color = colorResource(R.color.lightOrange), shape = RoundedCornerShape(13.dp))
            .clickable(onClick = onItemClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(R.color.white)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUrl)
                        .crossfade(true)
                        .listener(
                            onStart = { Log.d("Coil", "Start loading: $imageUrl") },
                            onSuccess = { _, _ -> Log.d("Coil", "Success: $imageUrl") },
                            onError = { _, result -> Log.e("Coil", "Error: ${result.throwable}") }
                        )
                        .build(),
                    contentDescription = category.Name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    },
                    error = {
                        Log.e("CategoryItem", "Failed to load: $imageUrl")
                        Text(text = "Error", fontWeight = FontWeight.Medium, fontSize = 11.sp)
                    }
                )
            } else {
                Text(text = "No\nimage", fontWeight = FontWeight.Medium, fontSize = 11.sp)
            }
        }

        Text(
            text = category.Name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}