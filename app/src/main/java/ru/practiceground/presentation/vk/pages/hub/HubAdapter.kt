package ru.practiceground.presentation.vk.pages.hub

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_hub_categories.view.*
import kotlinx.android.synthetic.main.item_hub_category.view.*
import kotlinx.android.synthetic.main.item_hub_category.view.image
import kotlinx.android.synthetic.main.item_mini_app.view.*
import ru.practiceground.R
import ru.practiceground.other.getColor
import ru.practiceground.other.getView
import kotlin.math.ceil

class HubAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<HubBaseItem>()

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_CATEGORIES -> return CategoriesHolder(getView(parent, R.layout.item_hub_categories))
            else -> throw Exception("qwe")//todo
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (item.viewType) {
            VIEW_TYPE_CATEGORIES -> (holder as CategoriesHolder).bind(item as? CategoriesItem ?: return)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<HubBaseItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class CategoriesHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val inflater = LayoutInflater.from(view.context)

        private fun <T> List<T>.splitBy(count: Int): List<List<T>> {
            if (count >= size)
                return listOf(this)

            val splitted = mutableListOf<List<T>>()
            repeat(ceil(size.toDouble().div(count)).toInt()) { iteration ->
                val fromIndex = iteration * count
                val endIndex = if (fromIndex + count > size) size else fromIndex + count
                splitted.add(subList(fromIndex, endIndex))
            }
            return splitted
        }

        fun bind(item: CategoriesItem) {
            val rows = item.categories.splitBy(3)
            rows.forEach { row ->
                val tableRow = TableRow(view.context).apply {
                    layoutParams = TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        weight = 1f
                        weightSum = 1f
                    }
                }
                row.forEach { tableRow.addView(getCategoryView(it) { }) }
                if (row.size < 3)
                    repeat(3 - row.size) {
                        tableRow.addView(View(view.context).apply {
                            layoutParams = TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT
                            )
                        })
                    }
                view.categories_table.addView(tableRow)
            }

            item.miniApps.forEach { miniApp ->
                view.mini_apps.addView(getMiniAppView(miniApp, item.onMiniAppCLick))
            }

            view.more_mini_apps.setOnClickListener { item.onMoreMiniAppsClick() }
        }

        private fun getCategoryView(category: Category, onCategoryClick: (Category) -> Unit): View =
            inflater.inflate(R.layout.item_hub_category, null).apply {
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                image.setImageDrawable(ContextCompat.getDrawable(context, category.drawableId))
                image.imageTintList = ColorStateList.valueOf(getColor(category.colorId))

                desc.text = category.text
                setOnClickListener { onCategoryClick(category) }
            }

        private fun getShowMoreCategoriesView(onShowMoreClick: () -> Unit): View =
            inflater.inflate(R.layout.item_hub_category, null).apply {
                image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_round_expand_more_24)?.apply {
                    setTint(getColor(R.color.blue00))
                })
                desc.text = "More"
                setOnClickListener { onShowMoreClick() }
            }

        private fun getMiniAppView(miniApp: MiniApp, onMiniAppCLick: (MiniApp) -> Unit): View =
            inflater.inflate(R.layout.item_mini_app, null).apply {
                sale.isVisible = miniApp.sale.isNotEmpty()
                sale.text = miniApp.sale
                name.text = miniApp.text
                setOnClickListener { onMiniAppCLick(miniApp) }
            }

        inner class CategoriesAdapter(private val context: Context) : BaseAdapter() {
            private var items = mutableListOf<Category>()
            private var addExpandView: Boolean = true

            override fun getCount(): Int = items.size

            override fun getItem(position: Int): Category? = items[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val category = items[position]
                return (convertView ?: LayoutInflater.from(context).inflate(R.layout.item_hub_category, null)).apply {
                    image.setImageDrawable(ContextCompat.getDrawable(context, category.drawableId)?.apply {
                        setTint(getColor(category.colorId))
                    })
                    desc.text = category.text
                    setOnClickListener { }
                }
            }

            fun setItems(newItems: List<Category>, addExpandView: Boolean) {
                items.clear()
                items.addAll(newItems)
                this.addExpandView = addExpandView
                notifyDataSetChanged()
            }

            fun addItems(newItems: List<Category>) {
                items.addAll(newItems)
                notifyItemRangeChanged(items.size - newItems.size, items.size)
            }
        }
    }
}