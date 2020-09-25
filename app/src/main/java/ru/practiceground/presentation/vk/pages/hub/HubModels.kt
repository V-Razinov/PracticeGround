package ru.practiceground.presentation.vk.pages.hub

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import ru.practiceground.R
import java.lang.Exception

internal const val VIEW_TYPE_CATEGORIES = 0
internal const val VIEW_TYPE_MINI_APPS = 1
internal const val VIEW_TYPE_DATE = 2
internal const val VIEW_TYPE_TAXI = 3
internal const val VIEW_TYPE_APPS = 4
internal const val VIEW_TYPE_SPORTS = 5
internal const val VIEW_TYPE_EXCHANGE = 6
internal const val VIEW_TYPE_GAMES = 7

abstract class HubBaseItem(val viewType: Int)

class CategoriesItem(
    val categories: List<Category>,
    val previewCount: Int,
    val onCategoryClick: (Category) -> Unit,
    var isExpanded: Boolean = false,
) : HubBaseItem(VIEW_TYPE_CATEGORIES) {
    init { if (previewCount >= categories.size) throw Exception() }
}

class MiniAppsItem(
    val miniApps: List<MiniApp>,
    val onMiniAppCLick: (MiniApp) -> Unit,
    val onMoreMiniAppsClick: () -> Unit
) : HubBaseItem(VIEW_TYPE_MINI_APPS)

class DateItem(
    val day: String,
    val date: String
) : HubBaseItem(VIEW_TYPE_DATE)

class TaxiItem(
    val onTaxiClick: () -> Unit,
    val onAllowGeoClick: () -> Unit
) : HubBaseItem(VIEW_TYPE_TAXI)

class AppsItem(
    @DrawableRes val image : Int = R.drawable.ic_round_apps_24,
    val apps: List<MiniApp>,
    val onAppCLick: (MiniApp) -> Unit,
    val onAppClick: () -> Unit
) : HubBaseItem(VIEW_TYPE_APPS)

class SportsItem(): HubBaseItem(VIEW_TYPE_SPORTS)

class ExchangeItem() : HubBaseItem(VIEW_TYPE_EXCHANGE)

class GamesItem() : HubBaseItem(VIEW_TYPE_GAMES)

enum class Category(val text: String, @DrawableRes val drawableId: Int, @ColorRes val colorId: Int) {
    FRIENDS(     "Friends",      R.drawable.ic_round_people_outline_24,      R.color.red354),
    COMMUNITIES( "Communities",  R.drawable.ic_teamwork,                     R.color.orange866),
    MUSIC(       "Music",        R.drawable.ic_round_music_note_24,          R.color.blue43),
    VK_PAY(      "VK Pay",       R.drawable.ic_rub_symbol,                   R.color.purple662),
    GAMES(       "Games",        R.drawable.ic_console,                      R.color.green932),
    VIDEOS(      "Videos",       R.drawable.ic_round_play_circle_outline_24, R.color.blue728),
    SHOPPING(    "Shopping",     R.drawable.ic_round_shopping_basket_24,     R.color.green2),
    STICKERS(    "Stickers",     R.drawable.ic_round_sentiment_satisfied_24, R.color.red450),
    LIVE_STREAMS("Live Streams", R.drawable.ic_round_adjust_24,              R.color.red143),
    ADS(         "Ads",          R.drawable.ic_megaphone,                    R.color.blue00),
    PODCASTS(    "PodCasts",     R.drawable.ic_round_mic_24,                 R.color.white88)
}

enum class MiniApp(val text: String, val sale: String) {
    BOOK_TAXI("Book Taxi", "-₽30"),
    ORDER_FOOD("Order food", "-20%"),
    ALI_EXPRESS("AliExpress", "-₽100"),
    SEARCH_JOBS("Search jobs", ""),
    BUY("Buy", "-₽200"),
    GET("Get", "")
}