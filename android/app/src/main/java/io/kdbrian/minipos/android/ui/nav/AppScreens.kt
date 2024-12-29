package io.kdbrian.minipos.android.ui.nav

sealed class AppScreens(val route: String) {
    data object POS_HOME_SCREEN : AppScreens("view_all_products")
    data object VIEW_ALL_PRODUCT : AppScreens("view_all_product")
    data object VIEW_SEARCH_PRODUCT_RESULTS : AppScreens("view_search_product_results")
    data object VIEW_SINGLE_PRODUCT : AppScreens("view_single_product")
    data object PORTFOLIO: AppScreens("portfolio")

}