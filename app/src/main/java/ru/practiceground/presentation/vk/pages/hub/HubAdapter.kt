package ru.practiceground.presentation.vk.pages.hub

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
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
import kotlinx.android.synthetic.main.item_date.view.*
import kotlinx.android.synthetic.main.item_hub_categories.view.*
import kotlinx.android.synthetic.main.item_hub_category.view.*
import kotlinx.android.synthetic.main.item_hub_category.view.image
import kotlinx.android.synthetic.main.item_mini_app.view.*
import kotlinx.android.synthetic.main.item_mini_apps.view.*
import kotlinx.android.synthetic.main.item_route.view.*
import kotlinx.android.synthetic.main.item_taxi.view.*
import ru.practiceground.R
import ru.practiceground.other.extensions.splitBySize
import ru.practiceground.other.getColor
import ru.practiceground.other.getView

class HubAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<HubBaseItem>()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORIES -> CategoriesHolder(getView(parent, R.layout.item_hub_categories))
            VIEW_TYPE_MINI_APPS -> MiniAppsHolder(getView(parent, R.layout.item_mini_apps))
            VIEW_TYPE_DATE -> DateHolder(getView(parent, R.layout.item_date))
            VIEW_TYPE_TAXI -> TaxiHolder(getView(parent, R.layout.item_taxi))
            else -> throw Exception("${HubAdapter::class.java} unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (item.viewType) {
            VIEW_TYPE_CATEGORIES -> (holder as CategoriesHolder).bind(item as CategoriesItem)
            VIEW_TYPE_MINI_APPS -> (holder as MiniAppsHolder).bind(item as MiniAppsItem)
            VIEW_TYPE_DATE -> (holder as DateHolder).bin(item as DateItem)
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
    class CategoriesHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val inflater = LayoutInflater.from(view.context)
        private val columns = 3

        fun bind(item: CategoriesItem) {

            if (view.categories_table.isNotEmpty()) {
                return
            }

            val rows = (if (item.isExpanded) item.categories else item.categories.subList(0, item.previewCount))
                .splitBySize(columns)
            rows.forEachIndexed { index, row ->
                val tableRow = getTableRow(view.context)
                row.forEach {
                    tableRow.addView(getCategoryView(it, item.onCategoryClick))
                }

                if (index == rows.size - 1 && !item.isExpanded) {
                    val showMore = getShowMoreCategoriesView {
                        item.isExpanded = !item.isExpanded

                        val fromIndex = tableRow.indexOfChild(this)
                        ((columns - 1)..fromIndex).forEach {
                            tableRow.removeViewAt(it)
                        }

                        val newItems1 = item.categories.subList(item.previewCount, item.previewCount + columns - fromIndex)
                        newItems1.forEach {
                            tableRow.addView(getCategoryView(it, item.onCategoryClick))
                        }

                        item.categories.subList(item.previewCount + newItems1.size, item.categories.size)
                            .splitBySize(columns)
                            .forEach {
                                val tableRow = getTableRow(view.context).apply { layoutTransition = LayoutTransition() }
                                it.forEach { category ->
                                    tableRow.addView(getCategoryView(category, item.onCategoryClick))
                                }
                                if (tableRow.childCount < columns) {
                                    repeat(tableRow.childCount - row.size) {
                                        tableRow.addView(getEmptyView(view.context))
                                    }
                                }
                                view.categories_table.addView(tableRow)
                            }
                    }
                    tableRow.addView(showMore)
                }
                view.categories_table.addView(tableRow)
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

        private fun getCategoryView(category: Category, onCategoryClick: (Category) -> Unit): View =
            inflater.inflate(R.layout.item_hub_category, null).apply {
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
                image.setImageDrawable(ContextCompat.getDrawable(context, category.drawableId))
                image.imageTintList = ColorStateList.valueOf(getColor(category.colorId))
                desc.text = category.text
                setOnClickListener { onCategoryClick(category) }
            }

        @SuppressLint("SetTextI18n")
        private fun getShowMoreCategoriesView(onShowMoreClick: View.() -> Unit): View =
            inflater.inflate(R.layout.item_hub_category, null).apply {
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
                image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_round_expand_more_24))
                image.imageTintList = ColorStateList.valueOf(getColor(R.color.blue00))
                desc.text = "More"
                setOnClickListener { onShowMoreClick() }
            }

        private fun getEmptyView(context: Context) = View(context).apply {
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
        }
    }

    class MiniAppsHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val inflater = LayoutInflater.from(view.context)

        fun bind(item: MiniAppsItem) {
            item.miniApps.forEach {
                view.mini_apps.addView(getMiniAppView(it, item.onMiniAppCLick))
            }
            view.more_mini_apps.setOnClickListener { item.onMoreMiniAppsClick() }
        }

        @SuppressLint("InflateParams")
        private fun getMiniAppView(miniApp: MiniApp, onMiniAppCLick: (MiniApp) -> Unit): View =
            inflater.inflate(R.layout.item_mini_app, null).apply {
                sale.isVisible = miniApp.sale.isNotEmpty()
                sale.text = miniApp.sale
                name.text = miniApp.text
                setOnClickListener { onMiniAppCLick(miniApp) }
            }
    }

    class DateHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bin(item: DateItem) {
            view.day.text = item.day
            view.date.text = item.date
        }
    }

    inner class TaxiHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: TaxiItem) {
            view.apply {
                taxi_routes.removeAllViews()
                if (item.isGeoAllowed) {
                    taxi_allow_access.isVisible = false
                    taxi_body_text.isVisible = false
                    taxi_routes.isVisible = true
                    taxi_routes.addRoutes(item.routes, item.onTaxiClick)
                    setOnClickListener { item.onTaxiClick() }
                } else {
                    taxi_routes.isVisible = false
                    taxi_body_text.isVisible = true
                    taxi_allow_access.isVisible = true
                    taxi_allow_access.setOnClickListener { item.onAllowGeoClick(item) }
                }
            }
        }

        private fun LinearLayout.addRoutes(routes: List<Route>, onRouteClick: () -> Unit) {
            val inflater = LayoutInflater.from(view.context)
            routes.forEach { addView(getRoute(inflater, it, onRouteClick)) }
        }

        @SuppressLint("InflateParams")
        private fun getRoute(inflater: LayoutInflater, route: Route, onRouteClick: () -> Unit) : View =
            inflater.inflate(R.layout.item_route, null).apply {
                item_route_name.text = route.name
                item_route_time.text = route.time
                setOnClickListener { onRouteClick() }
            }
    }

    class AppsHolder(private val view: View) : RecyclerView.ViewHolder(view)

    class SportsHolder(private val view: View) : RecyclerView.ViewHolder(view)

    class ExchangeHolder(private val view: View) : RecyclerView.ViewHolder(view)

    class GamesHolder(private val view: View) : RecyclerView.ViewHolder(view)
}