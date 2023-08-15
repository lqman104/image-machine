package com.luqman.imagemachine.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.luqman.imagemachine.R

sealed class MainMenu(
    @StringRes val name: Int,
    val icon: @Composable () -> Unit,
    val route: String
) {
    class List: MainMenu(
        name = R.string.home_menu,
        route = Graph.Main.LIST_PAGE,
        icon = @Composable {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "Home menu"
            )
        },
    )

    class Scan: MainMenu(
        name = R.string.scan_menu,
        route = Graph.Main.SEARCH_PAGE,
        icon = @Composable {
            Icon(
                painter = painterResource(id = com.luqman.imagemachine.uikit.R.drawable.ic_scan),
                contentDescription = "Scan menu",
                modifier = Modifier.height(24.dp)
            )
        },
    )

    companion object {
        val menus = listOf(
            List(),
            Scan(),
        )
    }
}
