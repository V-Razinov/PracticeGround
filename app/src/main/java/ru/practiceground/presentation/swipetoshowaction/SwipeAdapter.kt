package ru.practiceground.presentation.swipetoshowaction

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.databinding.ItemSwipeBinding
import ru.practiceground.presentation.base.BaseRecViewItem

class SwipeAdapter : RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder>() {

    var items = emptyList<BaseRecViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder {
        return SwipeViewHolder(ItemSwipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {
        holder.bind(items[position] as SwipeItem)
    }

    inner class SwipeViewHolder(private val binding: ItemSwipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SwipeItem) = binding.apply {
            itemSwipeToShowView.apply {
                setOnCentralPanelClickListener {
                    Toast.makeText(context, "click-clack", Toast.LENGTH_LONG).show()
                }
                setOnPanelChangedListener(item::currentPanel::set)
                setOnMovingListener(
                    onStarted = { _, _, _ ->
                        itemTopLineV.isVisible = true
                        itemBottomLineV.isVisible = true
                    },
                    onEnded = { _, _, _ ->
                        itemTopLineV.isInvisible = true
                        itemBottomLineV.isInvisible = true
                    }
                )
            }

            itemDeleteIv.setOnClickListener { Toast.makeText(binding.root.context, "Смэрть", Toast.LENGTH_LONG).show() }
            itemResendIv.setOnClickListener { Toast.makeText(binding.root.context, "Смэрть", Toast.LENGTH_LONG).show() }
        }
    }
}