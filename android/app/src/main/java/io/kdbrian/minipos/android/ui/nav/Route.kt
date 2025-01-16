package io.kdbrian.minipos.android.ui.nav

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object DASHBOARD : Route()

    @Serializable
    data object ALLPRODUCTS : Route()

    @Serializable
    data object VIEW_SEARCH_PRODUCT_RESULTS : Route()

    @Serializable
    data object VIEW_SINGLE_PRODUCT : Route()

    @Serializable
    data object PORTFOLIO : Route()
}
