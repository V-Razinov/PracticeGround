package ru.practiceground.presentation.vk.pages.hub

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.R
import ru.practiceground.databinding.*
import ru.practiceground.other.extensions.context
import ru.practiceground.other.extensions.splitBySize
import ru.practiceground.other.getColor

class HubAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<HubBaseItem>()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_CATEGORIES -> CategoriesHolder(ItemHubCategoriesBinding.inflate(layoutInflater, parent, false))
            VIEW_TYPE_MINI_APPS -> MiniAppsHolder(ItemMiniAppsBinding.inflate(layoutInflater, parent, false))
            VIEW_TYPE_DATE -> DateHolder(ItemDateBinding.inflate(layoutInflater, parent, false))
            VIEW_TYPE_TAXI -> TaxiHolder(ItemTaxiBinding.inflate(layoutInflater, parent, false))
            else -> throw Exception("${HubAdapter::class.java} unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (item.viewType) {
            VIEW_TYPE_CATEGORIES -> (holder as CategoriesHolder).bind(item as CategoriesItem)
            VIEW_TYPE_MINI_APPS -> (holder as MiniAppsHolder).bind(item as MiniAppsItem)
            VIEW_TYPE_DATE -> (holder as DateHolder).bind(item as DateItem)
            VIEW_TYPE_TAXI -> (holder as TaxiHolder).bind(item as TaxiItem)
        }
    }

    fun setItems(newItems: List<HubBaseItem>) {
        items = newItems
        notifyDataSetChanged()
    }
    
    fun notifyByViewType(viewType: Int) {
        items.indexOfFirst { it.viewType == viewType }.let { index ->
            if (index != -1) {
                notifyItemChanged(index)
            }
        }
    }

    @Suppress("NAME_SHADOWING")
    @SuppressLint("InflateParams")
    class CategoriesHolder(private val binding: ItemHubCategoriesBinding) : RecyclerView.ViewHolder(binding.root) {

        private val inflater = LayoutInflater.from(binding.context)
        private val columns = 3

        fun bind(item: CategoriesItem) {

            if (binding.categoriesTable.isNotEmpty()) {
                return
            }

            val rows = (if (item.isExpanded) item.categories else item.categories.subList(0, item.previewCount))
                .splitBySize(columns)
            rows.forEachIndexed { index, row ->
                val tableRow = getTableRow(binding.context)
                row.forEach {
                    tableRow.addView(getCategoryBinding(it, item.onCategoryClick).root)
                }

                if (index == rows.size - 1 && !item.isExpanded) {
                    val showMore = getShowMoreCategoriesVB {
                        item.isExpanded = !item.isExpanded

                        val fromIndex = tableRow.indexOfChild(this)
                        ((columns - 1)..fromIndex).forEach {
                            tableRow.removeViewAt(it)
                        }

                        val newItems1 = item.categories.subList(item.previewCount, item.previewCount + columns - fromIndex)
                        newItems1.forEach {
                            tableRow.addView(getCategoryBinding(it, item.onCategoryClick).root)
                        }

                        item.categories.subList(item.previewCount + newItems1.size, item.categories.size)
                            .splitBySize(columns)
                            .forEach {
                                val tableRow = getTableRow(binding.context).apply { layoutTransition = LayoutTransition() }
                                it.forEach { category ->
                                    tableRow.addView(getCategoryBinding(category, item.onCategoryClick).root)
                                }
                                if (tableRow.childCount < columns) {
                                    repeat(tableRow.childCount - row.size) {
                                        tableRow.addView(getEmptyView(binding.context))
                                    }
                                }
                                binding.categoriesTable.addView(tableRow)
                            }
                    }
                    tableRow.addView(showMore.root)
                }
                binding.categoriesTable.addView(tableRow)
            }
        }

        private fun getTableRow(context: Context) = TableRow(context).apply {
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
                weightSum = 1f
            }
        }

        private fun getCategoryBinding(category: Category, onCategoryClick: (Category) -> Unit): ItemHubCategoryBinding =
            ItemHubCategoryBinding.inflate(inflater, null, false).apply {
                root.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
                image.setImageDrawable(ContextCompat.getDrawable(context, category.drawableId))
                image.imageTintList = ColorStateList.valueOf(getColor(category.colorId))
                desc.text = category.text
                root.setOnClickListener { onCategoryClick(category) }
            }

        @SuppressLint("SetTextI18n")
        private fun getShowMoreCategoriesVB(onShowMoreClick: View.() -> Unit): ItemHubCategoryBinding =
            ItemHubCategoryBinding.inflate(inflater, null, false).apply {
                root.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
                image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_round_expand_more_24))
                image.imageTintList = ColorStateList.valueOf(getColor(R.color.blue00))
                desc.text = "More"
                root.setOnClickListener { root.onShowMoreClick() }
            }

        private fun getEmptyView(context: Context) = View(context).apply {
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
        }
    }

    class MiniAppsHolder(private val binding: ItemMiniAppsBinding) : RecyclerView.ViewHolder(binding.root) {

        private val inflater = LayoutInflater.from(binding.context)

        fun bind(item: MiniAppsItem) {
            item.miniApps.forEach {
                binding.miniApps.addView(getMiniAppVB(it, item.onMiniAppCLick).root)
            }
            binding.moreMiniApps.setOnClickListener { item.onMoreMiniAppsClick() }
        }

        @SuppressLint("InflateParams")
        private fun getMiniAppVB(miniApp: MiniApp, onMiniAppCLick: (MiniApp) -> Unit): ItemMiniAppBinding =
            ItemMiniAppBinding.inflate(inflater, null, false).apply {
                sale.isVisible = miniApp.sale.isNotEmpty()
                sale.text = miniApp.sale
                name.text = miniApp.text
                root.setOnClickListener { onMiniAppCLick(miniApp) }
            }
    }

    class DateHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DateItem) = binding.apply {
            day.text = item.day
            date.text = item.date
        }
    }

    inner class TaxiHolder(private val binding: ItemTaxiBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaxiItem) = binding.apply {
            taxiRoutes.removeAllViews()
            if (item.isGeoAllowed) {
                taxiAllowAccess.isVisible = false
                taxiBodyText.isVisible = false
                taxiRoutes.isVisible = true
                taxiRoutes.addRoutes(item.routes, item.onTaxiClick)
                root.setOnClickListener { item.onTaxiClick() }
            } else {
                taxiRoutes.isVisible = false
                taxiBodyText.isVisible = true
                taxiAllowAccess.isVisible = true
                taxiAllowAccess.setOnClickListener { item.onAllowGeoClick(item) }
            }
        }

        private fun LinearLayout.addRoutes(routes: List<Route>, onRouteClick: () -> Unit) {
            val inflater = LayoutInflater.from(binding.context)
            routes.forEach { addView(getRouteVB(inflater, it, onRouteClick).root) }
        }

        @SuppressLint("InflateParams")
        private fun getRouteVB(inflater: LayoutInflater, route: Route, onRouteClick: () -> Unit) : ItemRouteBinding =
            ItemRouteBinding.inflate(inflater, null, false).apply {
                itemRouteName.text = route.name
                itemRouteTime.text = route.time
                root.setOnClickListener { onRouteClick() }
            }
    }

    class AppsHolder(private val view: View) : RecyclerView.ViewHolder(view)

    class SportsHolder(private val view: View) : RecyclerView.ViewHolder(view)

    class ExchangeHolder(private val view: View) : RecyclerView.ViewHolder(view)

    class GamesHolder(private val view: View) : RecyclerView.ViewHolder(view)
}