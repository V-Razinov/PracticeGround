package ru.practiceground.presentation.discordviewpager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.R
import ru.practiceground.databinding.ItemDiscordPersonMessageBinding
import ru.practiceground.other.getBinding

class DiscordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<MessageItem>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MessageHolder(getBinding(parent, R.layout.item_discord_person_message))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MessageHolder)?.bind(items[position])
    }

    fun setItems(items: List<MessageItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class MessageHolder(private val binding: ItemDiscordPersonMessageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(messageItem: MessageItem) {
            binding.item = messageItem
        }
    }
}