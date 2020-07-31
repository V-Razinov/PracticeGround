package ru.practiceground.presentation.allinonerecview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_gif_horizontal.view.*
import ru.practiceground.R
import ru.practiceground.other.getView

class HorizontalGifsAdapter(private val items: List<GifItem> = emptyList())
    : RecyclerView.Adapter<HorizontalGifsAdapter.GifViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HorizontalGifsAdapter.GifViewHolder {
        return GifViewHolder(getView(parent, R.layout.item_gif_horizontal))
    }

    override fun onBindViewHolder(holder: HorizontalGifsAdapter.GifViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class GifViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: GifItem) {
            Glide.with(view.context).load(item.id).into(view.gif_iv)
        }
    }
}