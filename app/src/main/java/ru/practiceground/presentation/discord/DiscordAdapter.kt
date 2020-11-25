package ru.practiceground.presentation.discord

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.databinding.ItemDiscordPersonMessageBinding

class DiscordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<MessageItem>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MessageHolder(ItemDiscordPersonMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MessageHolder)?.bind(items[position])
    }

    fun setItems(items: List<MessageItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class MessageHolder(private val binding: ItemDiscordPersonMessageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MessageItem) = binding.apply {
            nickname.text = item.nickname
            datetime.text = item.date
            message.text = item.message
        }
    }
}