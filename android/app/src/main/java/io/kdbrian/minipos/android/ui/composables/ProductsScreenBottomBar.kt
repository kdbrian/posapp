package io.kdbrian.minipos.android.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.kdbrian.minipos.android.ui.nav.BottomAppBarItem

@Composable
fun ProductsScreenBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarItems: List<BottomAppBarItem> = BottomAppBarItem.TWOSCREENHOMEMODE
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination by remember {
        mutableStateOf(currentBackStack?.destination?.route)
    }
    NavigationBar(modifier) {
        bottomBarItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route.route,
                onClick = {
                    navController.navigate(item.route.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    item.painter?.let {
                        Icon(
                            painter = painterResource(item.painter),
                            contentDescription = item.label
                        )
                    } ?: kotlin.run {
                        Icon(
                            imageVector = item.icon!!,
                            contentDescription = item.label
                        )
                    }
                },
                label = {
                    Text(text = item.label)
                }
            )

        }
    }

}