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
                onCategoryClick = ::onCategoryClick,
                miniApps = MiniApp.values().toList(),
                onMiniAppCLick = ::onMiniAppClick,
                onMoreMiniAppsClick = ::onMoreMiniAppsClick
            )
        )
    }

    private fun onCategoryClick(category: Category) {

    }

    private fun onMiniAppClick(miniApp: MiniApp) {

    }

    private fun onMoreMiniAppsClick() {

    }
}