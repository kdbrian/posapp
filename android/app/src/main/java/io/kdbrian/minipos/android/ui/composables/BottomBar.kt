package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.kdbrian.minipos.android.ui.nav.BottomAppBarItem
import io.kdbrian.minipos.android.ui.nav.Route
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.TextLocals
import timber.log.Timber

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomAppBarItem>,
    navController: NavController,
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination?.route

    NavigationBar(modifier) {
        items.forEach { navItem ->
            NavigationBarItem(
                selected = currentDestination == navItem.route.toString(),
                onClick = { navController.navigate(navItem.route) },
                icon = {
                    if (navItem.icon != null) {
                        Icon(imageVector = navItem.icon, contentDescription = null)
                    } else if (navItem.painter != null) {
                        Icon(painter = painterResource(navItem.painter), contentDescription = null)
                    }
                },
                label = { Text(
                    text = navItem.label,
                    style = TextLocals.LocalDefaultTextStyle.current.copy(
                        fontSize = 14.sp
                    )
                ) }
            )
        }

    }
}


@Preview
@Composable
private fun BottomBarPrev() {
    MiniposTheme {
        Box(modifier = Modifier.fillMaxSize()){


        }
    }
}

