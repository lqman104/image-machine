package com.luqman.imagemachine.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.luqman.imagemachine.R
import com.luqman.imagemachine.ui.screens.list.ListScreen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButtonHome()
            },
            bottomBar = {
                BottomNavigation()
            },
            topBar = {
                TopBar(pageName = "Text", showSortMenu = true)
            }
        ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                ListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    pageName: String,
    showSortMenu: Boolean
) {
    var isMenuOpen by remember {
        mutableStateOf(false)
    }

    Box {
        TopAppBar(
            scrollBehavior = enterAlwaysScrollBehavior(),
            modifier = modifier,
            title = {
                Text(text = pageName)
            },
            actions = {
                if (showSortMenu) {
                    IconButton(onClick = {
                        isMenuOpen = true
                    }) {
                        Icon(
                            painter = painterResource(id = com.luqman.imagemachine.uikit.R.drawable.ic_sort),
                            contentDescription = "Sort List"
                        )
                    }
                }
            }
        )

        SortMenuDialog(
            modifier = Modifier.align(Alignment.TopEnd),
            selectedMenu = SortMenuType.NAME,
            expanded = isMenuOpen
        ) {
            isMenuOpen = false
        }
    }
}

@Composable
fun FloatingActionButtonHome(
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(modifier = modifier, onClick = {

    }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add new machine")

    }
}

@Composable
fun SortMenuDialog(
    modifier: Modifier = Modifier,
    selectedMenu: SortMenuType,
    expanded: Boolean,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismiss,
        properties = PopupProperties()
    ) {
        if (selectedMenu == SortMenuType.NAME) {
            SelectedSortMenu(name = stringResource(id = R.string.sort_by_name)) {

            }
            SortMenu(name = stringResource(id = R.string.sort_by_type)) {

            }
        } else {
            SortMenu(name = stringResource(id = R.string.sort_by_name)) {

            }
            SelectedSortMenu(name = stringResource(id = R.string.sort_by_type)) {

            }
        }
    }
}

@Composable
fun SelectedSortMenu(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer),
        text = { Text(text = name) },
        onClick = onClick
    )
}

@Composable
fun SortMenu(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        modifier = modifier,
        text = { Text(text = name) },
        onClick = onClick
    )
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Home menu"
                )
            },
            label = { Text(stringResource(id = R.string.home_menu)) },
            selected = false, onClick = { }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = com.luqman.imagemachine.uikit.R.drawable.ic_scan),
                    contentDescription = "Scan menu",
                    modifier = Modifier.height(24.dp)
                )
            },
            label = { Text(stringResource(id = R.string.scan_menu)) },
            selected = false, onClick = { }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}