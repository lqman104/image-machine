package com.luqman.imagemachine.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luqman.imagemachine.R
import com.luqman.imagemachine.data.repository.model.SortMenuType
import com.luqman.imagemachine.ui.navigation.Graph
import com.luqman.imagemachine.ui.navigation.MainGraph
import com.luqman.imagemachine.ui.navigation.MainMenu

@Composable
fun MainScreen(
    rootNavHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButtonHome {
                rootNavHostController.navigate(Graph.Detail.DETAIL_PAGE)
            }
        },
        bottomBar = {
            BottomNavigation(navController)
        },
        topBar = {
            TopBar(pageName = "Text", showSortMenu = true)
        }
    ) { padding ->
        MainGraph(controller = navController, padding = padding)
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

    TopAppBar(
        modifier = modifier,
        scrollBehavior = enterAlwaysScrollBehavior(),
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

            SortMenuDialog(
                selectedMenu = SortMenuType.NAME,
                expanded = isMenuOpen
            ) {
                isMenuOpen = false
            }
        }
    )

}

@Composable
fun FloatingActionButtonHome(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(modifier = modifier, onClick = onClick) {
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
    ) {
        if (selectedMenu == SortMenuType.NAME) {
            SelectedSortMenu(name = stringResource(id = R.string.sort_by_name), onClick = onDismiss)
            SortMenu(name = stringResource(id = R.string.sort_by_type), onClick = onDismiss)
        } else {
            SortMenu(name = stringResource(id = R.string.sort_by_name), onClick = onDismiss)
            SelectedSortMenu(name = stringResource(id = R.string.sort_by_type), onClick = onDismiss)
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
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        MainMenu.menus.forEach { menu ->
            NavigationBarItem(
                icon = menu.icon,
                label = { Text(stringResource(id = menu.name)) },
                selected = currentDestination?.hierarchy?.any { it.route == menu.route } == true,
                onClick = {
                    navController.navigate(menu.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(NavHostController(LocalContext.current))
}