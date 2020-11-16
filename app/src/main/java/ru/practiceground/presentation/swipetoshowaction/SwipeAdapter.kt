package ru.practiceground.presentation.swipetoshowaction

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_swipe.view.*
import ru.practiceground.R
import ru.practiceground.other.getView
import ru.practiceground.presentation.base.BaseRecViewItem

class SwipeAdapter : RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder>() {

    var items = emptyList<BaseRecViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder {
        return SwipeViewHolder(getView(parent, R.layout.item_swipe))
    }

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {
        holder.bind(items[position] as SwipeItem)
    }

    inner class SwipeViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: SwipeItem) {
            view.apply {
                item_swipe_to_show_view.apply {
                    setOnCentralPanelClickListener {
                        Toast.makeText(context, "click-clack", Toast.LENGTH_LONG).show()
                    }
                    setOnPanelChangedListener(item::currentPanel::set)
                    setOnMovingListener(
                        onStarted = { _, _, _ ->
                            item_top_line_v.isVisible = true
                            item_bottom_line_v.isVisible = true
                        },
                        onEnded =  { _, _, _ ->
                            item_top_line_v.isInvisible = true
                            item_bottom_line_v.isInvisible = true
                        }
                    )
                }

                item_delete_iv.setOnClickListener { Toast.makeText(context, "Смэрть", Toast.LENGTH_LONG).show() }
                item_resend_iv.setOnClickListener { Toast.makeText(context, "Смэрть", Toast.LENGTH_LONG).show() }
            }
        }
    }
}