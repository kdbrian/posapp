package io.kdbrian.minipos.android.ui.nav

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import io.kdbrian.minipos.android.R

sealed class BottomAppBarItem(
    val label: String,
    val icon: ImageVector? = null,
    @DrawableRes val painter: Int? = null,
    val route: AppScreens
) {

    data object ViewProducts : BottomAppBarItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = AppScreens.POS_HOME_SCREEN
    )


    data object PortFolio : BottomAppBarItem(
        label = "Portfolio",
        painter = R.drawable.monitoring_24dp,
        route = AppScreens.PORTFOLIO
    )

    companion object {
        val TWOSCREENHOMEMODE = listOf(
            ViewProducts,
            PortFolio
        )
    }

}