package ru.practiceground.presentation.root

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.databinding.ItemRootBinding

class RootAdapter : RecyclerView.Adapter<RootAdapter.Item>() {

    private var items = emptyList<RootItem>()
    private var onItemClickAction: (RootItems) -> Unit = { }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item {
        return Item(ItemRootBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Item, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<RootItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setItemClickAction(onItemClickAction: (RootItems) -> Unit) {
        this.onItemClickAction = onItemClickAction
        notifyDataSetChanged()
    }

    inner class Item(private val binding: ItemRootBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RootItem) = binding.apply {
            title.text = data.title
            root.setOnClickListener { onItemClickAction(data.type) }
        }
    }
}