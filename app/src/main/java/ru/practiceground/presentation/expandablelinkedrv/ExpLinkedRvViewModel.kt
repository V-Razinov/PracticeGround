package ru.practiceground.presentation.expandablelinkedrv

import androidx.lifecycle.MutableLiveData
import ru.practiceground.other.extensions.string
import ru.practiceground.presentation.base.BaseViewModel
import java.lang.StringBuilder

class ExpLinkedRvViewModel : BaseViewModel() {
    private val largeBody = StringBuilder().apply {
        repeat(150) { append(it.string) }
    }.string

    val items = MutableLiveData<List<BaseExpLinkedItem>>(
        listOf(
            ExpLinkedItem(
                title = "MainTitle1",
                items = listOf(
                    ExpLinkedSubItem(title = "SubTitle1", body = largeBody)
                )
            ),
            ExpLinkedItem(
                title = "MainTitle2",
                items = listOf(
                    ExpLinkedSubItem(title = "SubTitle1", body = "SubBody1"),
                    ExpLinkedSubItem(title = "SubTitle1", body = "SubBody1"),
                    ExpLinkedSubItem(title = "SubTitle1", body = "SubBody1"),
                    ExpLinkedSubItem(title = "SubTitle1", body = "SubBody1"),
                    ExpLinkedSubItem(title = "SubTitle1", body = "SubBody1"),
                    ExpLinkedSubItem(title = "SubTitle1", body = "SubBody1"),
                )
            ),
            ExpLinkedItem(
                title = "MainTitle3",
                items = listOf(ExpLinkedSubItem(title = "SubTitle3", body = "SubBody3"))
            ),
            ExpLinkedItem(
                title = "MainTitle4",
                items = listOf(ExpLinkedSubItem(title = "SubTitle4", body = "SubBody4"))
            ),
            ExpLinkedItem(
                title = "MainTitle5",
                items = listOf(ExpLinkedSubItem(title = "SubTitle5", body = "SubBody5"))
            ),
        )
    )
}