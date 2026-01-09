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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
                        CategoryItem(
                            category = categoryModel,
                            modifier = Modifier
                                .weight(1f),
                            onItemClick = {}
                        )
                    }
                    // Add spacers for incomplete rows
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
    val imageUrl = category.ImagePath?.trim() ?: ""

    // Log only once per URL change
    LaunchedEffect(imageUrl) {
        Log.d("CategoryItem", "🔍 Loading: name='${category.Name}' url='$imageUrl'")
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl.takeIf { it.isNotBlank() })  // Only load if URL is not blank
            .crossfade(true)
            .listener(
                onStart = { Log.d("CategoryItem", "🖼️ Image request started: $imageUrl") },
                onSuccess = { _, _ -> Log.d("CategoryItem", "🎉 Image loaded successfully: ${category.Name}") },
                onError = { _, result -> Log.e("CategoryItem", "💥 Image load error: ${category.Name}", result.throwable) },
                onCancel = { Log.w("CategoryItem", "🚫 Image load cancelled: ${category.Name}") }
            )
            .build(),
        contentScale = ContentScale.Crop
    )
    val state = painter.state

    // Log state transitions
    when (state) {
        is AsyncImagePainter.State.Success -> Log.d("CategoryItem", "✅ Loaded: ${category.Name}")
        is AsyncImagePainter.State.Error -> Log.e("CategoryItem", "❌ Failed: ${category.Name} - ${state.result.throwable?.message}")
        is AsyncImagePainter.State.Loading -> Log.d("CategoryItem", "⏳ Loading: ${category.Name}")
        else -> {}
    }

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
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
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