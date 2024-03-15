package com.example.composeapp.ui.components

import android.icu.lang.UCharacter.VerticalOrientation
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.ui.navigation.PARAM_SEARCH
import com.example.composeapp.ui.navigation.SEARCH_TYPE
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.ui.navigation.TRIP_ID
import com.example.composeapp.ui.theme.ChildBgColor
import com.example.composeapp.utils.AppUtils

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeListItem(
    item: TripItemModel,
    imageHeight: Dp = 130.dp,
    imageWidth: Dp = 150.dp,
    titleSize: TextStyle = MaterialTheme.typography.titleMedium,
    descSize: TextStyle = MaterialTheme.typography.bodySmall,
    onclickItem: (item: TripItemModel) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(start = 18.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(ChildBgColor)
    ) {
        Column(
            modifier = Modifier
                .width(imageWidth)
                .clickable {
                    onclickItem.invoke(item)
                }
        ) {
            GlideImage(
                model = item.imageUrl,
                contentDescription = "trip image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .width(imageWidth)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                it.thumbnail()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 12.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(
                    text = item.tripTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = titleSize,
                    color = Color.White
                )
                Row {
                    Text(
                        text = item.description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = descSize,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun HomeListRow(
    title: String,
    description: String,
    itemList: MutableList<TripItemModel>,
    navHostController: NavHostController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        DynamicText(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(18.dp))
        LazyRow {
            itemsIndexed(itemList) { index, item ->
                HomeListItem(
                    item = item,
                    imageWidth = 150.dp,
                    imageHeight = 130.dp,
                    onclickItem = {
                        navHostController?.navigate(
                            route = Screen.Detail.route.replace(
                                oldValue = PARAM_SEARCH,
                                newValue = item.tripTitle
                            ).replace(
                                oldValue = SEARCH_TYPE,
                                newValue = item.description
                            ).replace(
                                oldValue = TRIP_ID,
                                newValue = item.tripId
                            )
                        )
                    }
                )
            }
            if (itemList.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun HomeHighlightedRow(
    itemList: MutableList<TripItemModel>,
    navHostController: NavHostController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow {
            itemsIndexed(itemList) { index, item ->
                HomeListItem(
                    item = item,
                    imageWidth = 260.dp,
                    imageHeight = 220.dp,
                    titleSize = MaterialTheme.typography.titleLarge,
                    descSize = MaterialTheme.typography.bodyLarge,
                    onclickItem = {
                        navHostController?.navigate(
                            route = Screen.Detail.route.replace(
                                oldValue = PARAM_SEARCH,
                                newValue = item.tripTitle
                            ).replace(
                                oldValue = SEARCH_TYPE,
                                newValue = item.description
                            ).replace(
                                oldValue = TRIP_ID,
                                newValue = item.tripId
                            )
                        )
                    }
                )
            }
            if (itemList.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}


@Composable
fun MultiLineRow(
    title: String,
    description: String,
    itemList: MutableList<TripItemModel>,
    navHostController: NavHostController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        DynamicText(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            onClick = { _text ->
                println("Clicked $_text")
                navHostController?.navigate(Screen.Search.route)
            }
        )
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(18.dp))
        MultiLineItem(
            itemList = itemList,
            itemClick = { item ->
                // Do the click action
                navHostController?.navigate(
                    route = Screen.Detail.route.replace(
                        oldValue = PARAM_SEARCH,
                        newValue = item.tripTitle
                    ).replace(
                        oldValue = SEARCH_TYPE,
                        newValue = item.description
                    ).replace(
                        oldValue = TRIP_ID,
                        newValue = item.tripId
                    )
                )
            }
        )
    }
}

@Composable
fun MultiLineItem(
    itemList: MutableList<TripItemModel>,
    itemClick: (item: TripItemModel) -> Unit
) {
    val entryList = AppUtils.multiRowList(itemList)
    LazyRow {
        itemsIndexed(entryList) { index, item ->
            Column {
                if (item.isNotEmpty()) {
                    HomeListItem(
                        item = item[0],
                        imageWidth = 120.dp,
                        imageHeight = 120.dp,
                        titleSize = MaterialTheme.typography.titleMedium,
                        descSize = MaterialTheme.typography.bodySmall,
                        onclickItem = {
                            itemClick.invoke(item[0])
                        }
                    )
                }
                if (item.size > 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeListItem(
                        item = item[1],
                        imageWidth = 120.dp,
                        imageHeight = 120.dp,
                        titleSize = MaterialTheme.typography.titleMedium,
                        descSize = MaterialTheme.typography.bodySmall,
                        onclickItem = {
                            itemClick.invoke(item[1])
                        }
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
