package ru.practiceground.presentation.vk.pages.hub

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import androidx.lifecycle.MutableLiveData
import ru.practiceground.R
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseViewModel

class HubViewModel : BaseViewModel() {
    val items = MutableLiveData<List<HubBaseItem>>()
    val notifyItemChanged = SingleLiveEvent<Int>()

    init {
        items.value = listOf(
            CategoriesItem(
                categories = Category.values().toList(),
                previewCount = 5,
                onCategoryClick = ::onCategoryClick
            ),
            MiniAppsItem(
                miniApps = MiniApp.values().toList(),
                onMiniAppCLick = ::onMiniAppClick,
                onMoreMiniAppsClick = ::onMoreMiniAppsClick
            ),
            DateItem(
                day = "Today",
                date = "25 September"
            ),
            TaxiItem(
                isGeoAllowed = false,
                routes = emptyList(),
                onTaxiClick = ::onTaxiClick,
                onAllowGeoClick = ::onAllowGeoClick
            ),
        )
    }

    private fun onCategoryClick(category: Category) {}

    private fun onMiniAppClick(miniApp: MiniApp) {}

    private fun onMoreMiniAppsClick() {}

    private fun onTaxiClick() {}

    private fun onAllowGeoClick(item: TaxiItem) {
        item.isGeoAllowed = true
        item.routes = listOf(
            Route(getRouteName("ул. Ефремова 149", "₽195", "₽225"), "23 min ride"),
            Route(getRouteName("ул. Ефремова 149", "₽195", ""), "23 min ride"),
            Route(getRouteName("ул. Ефремова 123", "₽123", ""), "15 min ride"),
        )
        notifyItemChanged.value = VIEW_TYPE_TAXI
    }

    private fun getRouteName(address: String, newPrice: String, oldPrice: String) : CharSequence {
        return if (newPrice.isEmpty()) {
            "$address for $oldPrice"
        } else {
            SpannableStringBuilder("$address for $newPrice ")
                .append(SpannableString(oldPrice).apply {
                    setSpan(StrikethroughSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(ForegroundColorSpan(getColor(R.color.grey)), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                })
        }
    }
}