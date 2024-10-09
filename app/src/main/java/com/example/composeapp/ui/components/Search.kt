package com.example.composeapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.composeapp.data.search.SelectionChipModel
import com.example.composeapp.ui.theme.ButtonColor
import com.example.composeapp.ui.theme.ChildBgColor
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.theme.ParentBgColor
import com.example.composeapp.ui.theme.WhiteColor

@Composable
fun SearchBox(
    placeHolder: String,
    onClick: () -> Unit
) {
    val text = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            .border(
                1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable {
                    onClick.invoke()
                }
        ) {
            Text(
                text = placeHolder,
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SearchInputBox(
    placeHolder: String,
    onTextChange: (String) -> Unit,
    searchForText: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ParentBgColor
            )
            .padding(18.dp)
    ) {
        Row {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onTextChange(text)
                },
                placeholder = {
                    Text(text = placeHolder)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectionChipGroup(
    itemList: List<SelectionChipModel>,
    onChipSelection: (SelectionChipModel) -> Unit
) {
    var chipInputList: List<SelectionChipModel> by remember {
        mutableStateOf(itemList)
    }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier
            .padding(12.dp)
    ) {
        chipInputList.forEach { word ->
            DynamicText(
                word.title,
                modifier = Modifier
                    .background(
                        color = if (word.isSelected.value) {
                            ButtonColor
                        } else {
                            Color.Gray
                        }, shape = CircleShape
                    )
                    .padding(vertical = 10.dp, horizontal = 18.dp)
                    .clickable {
                        chipInputList.forEach { item ->
                            if (item.title == word.title) {
                                item.isSelected.value = !word.isSelected.value
                            } else {
                                item.isSelected.value = false
                            }
                        }
                        onChipSelection(word)
                    },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun FieldPicker(
    itemList: List<SelectionChipModel>,
    onItemClick: (SelectionChipModel) -> Unit
) {

    var showDropdown by remember {
        mutableStateOf(false)
    }
    var selectedText by remember {
        mutableStateOf("Select Days")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ParentBgColor)
            .padding(16.dp)
            .clickable {
                showDropdown = !showDropdown
            }
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                DynamicText(
                    text = selectedText,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    tint = WhiteColor,
                    contentDescription = "select field",
                    modifier = Modifier.size(30.dp)
                )
            }
            AnimatedVisibility(visible = showDropdown, modifier = Modifier.padding(16.dp)) {
                SelectionChipGroup(
                    itemList = itemList,
                    onChipSelection = {
                        selectedText = it.title
                        onItemClick(it)
                        showDropdown = false
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBox() {
    ComposeAppTheme {
        SearchBox(placeHolder = "Type in ...", onClick = {})
    }
}