package ru.practiceground.presentation.vk.pages.hub

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class HubViewModel : BaseViewModel() {
    val items = MutableLiveData<List<HubBaseItem>>()

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
                onTaxiClick = ::onTaxiClick,
                onAllowGeoClick = ::onAllowGeoClick
            ),

        )
    }

    private fun onCategoryClick(category: Category) {}

    private fun onMiniAppClick(miniApp: MiniApp) {}

    private fun onMoreMiniAppsClick() {}

    private fun onTaxiClick() {}

    private fun onAllowGeoClick() {}
}