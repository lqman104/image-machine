package com.luqman.imagemachine.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luqman.imagemachine.data.repository.model.SortMenuType
import com.luqman.imagemachine.ui.navigation.Graph.Detail.CODE_KEY
import com.luqman.imagemachine.ui.navigation.Graph.Detail.ID_KEY
import com.luqman.imagemachine.ui.navigation.Graph.Main.TYPE_KEY
import com.luqman.imagemachine.ui.screens.detail.DetailScreen
import com.luqman.imagemachine.ui.screens.list.ListScreen
import com.luqman.imagemachine.ui.screens.main.MainScreen
import com.luqman.imagemachine.ui.screens.photopreview.PreviewScreen
import com.luqman.imagemachine.ui.screens.search.SearchScreen

@Composable
fun RootGraph(
    controller: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = controller,
        startDestination = startDestination,
        route = Graph.Root.ROUTE
    ) {
        composable(route = Graph.Main.MAIN_PAGE) {
            MainScreen(rootNavHostController = controller)
        }
        composable(
            route = Graph.Detail.DETAIL_PAGE,
            arguments = listOf(
                navArgument(ID_KEY) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(CODE_KEY) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailScreen(navController = controller)
        }
        composable(route = Graph.Detail.PREVIEW_PAGE) {
            PreviewScreen()
        }
    }
}

@Composable
fun MainGraph(
    rootController: NavHostController,
    controller: NavHostController,
    padding: PaddingValues,
    sortMenuType: SortMenuType
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        navController = controller,
        startDestination = Graph.Main.LIST_PAGE
    ) {
        composable(
            route = Graph.Main.LIST_PAGE,
            arguments = listOf(navArgument(TYPE_KEY) { type = NavType.StringType })
        ) {
            ListScreen(sortMenuType = sortMenuType, navHostController = rootController)
        }
        composable(Graph.Main.SEARCH_PAGE) {
            SearchScreen(navHostController = rootController)
        }
    }
}

object Graph {

    object Root {
        const val ROUTE = "root"
    }

    object Main {
        const val TYPE_KEY: String = "type"

        const val MAIN_PAGE = "main"
        const val LIST_PAGE = "list_page/{$TYPE_KEY}"
        const val SEARCH_PAGE = "search_page"

        fun getMachineListByTypeRoute(type: String): String {
            return LIST_PAGE.replace("{$TYPE_KEY}", type)
        }
    }

    object Detail {
        const val ID_KEY: String = "machine_id"
        const val PICTURES_KEY: String = "pictures"
        const val CODE_KEY: String = "code"

        const val DETAIL_PAGE = "detail_page?$ID_KEY={$ID_KEY}&$CODE_KEY={$CODE_KEY}"
        const val PREVIEW_PAGE = "preview_page?$PICTURES_KEY={$PICTURES_KEY}"

        fun getDetailPage(): String {
            return DETAIL_PAGE
        }

        fun getPhotoByMachineId(id: String): String {
            return PREVIEW_PAGE.replace("{$PICTURES_KEY}", id)
        }

        fun getDetailPageById(id: String): String {
            return DETAIL_PAGE.replace("{$ID_KEY}", id)
        }

        fun getDetailPageByCode(code: String): String {
            return DETAIL_PAGE.replace("{$CODE_KEY}", code)
        }
    }
}