package dev.percym.fooddeliveryapp.Activity.Dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.percym.fooddeliveryapp.R

@Composable
@Preview
fun MyBottomBar(){
    val bottomMenuItemsList = prepareBottomMenu()
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf("Home") }


    BottomAppBar(
        backgroundColor = colorResource(R.color.grey),
        elevation = 3.dp,
    ){
        bottomMenuItemsList.forEach {
            bottomMenuItem ->
                BottomNavigationItem(selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    if(bottomMenuItem.label == "Cart"){}

                        else{}

                },
                icon = {
                   Icon(painter = bottomMenuItem.icon,
                       contentDescription = null,
                       modifier = Modifier
                           .padding(top =8.dp)
                           .size(20.dp)
                   )
                })
                }
    }


}

@Composable
fun prepareBottomMenu(): List<BottomMenuItem>{
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()
    bottomMenuItemList.add(BottomMenuItem("Home", painterResource(id = R.drawable.btn_1)))
    bottomMenuItemList.add(BottomMenuItem("Cart", painterResource(id = R.drawable.btn_2)))
    bottomMenuItemList.add(BottomMenuItem("Favourite", painterResource(id = R.drawable.btn_3)))
    bottomMenuItemList.add(BottomMenuItem("Order", painterResource(id = R.drawable.btn_4)))
    bottomMenuItemList.add(BottomMenuItem("Profile", painterResource(id = R.drawable.btn_5)))
    return bottomMenuItemList
}