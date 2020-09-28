package ru.practiceground.other

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import ru.practiceground.R
import ru.practiceground.other.extensions.toDp
import ru.practiceground.presentation.vk.pages.news.MenuAction

@BindingAdapter("gildeSrc")
fun setGlideSrc(view: ImageView, link: String) {
    Glide.with(view).load(link).into(view)
}

@BindingAdapter("menuItems")
fun setMenuItems(view: ImageView, items: List<MenuAction>) {
    val popMenu = PopupMenu(ContextThemeWrapper(view.context, R.style.PopupMenuTheme), view)
    items.forEach {
        popMenu.menu.add(it.id, it.id, it.id, it.title)
    }
    popMenu.setOnMenuItemClickListener { menuItem ->
        Toast.makeText(view.context, "menu item click ${menuItem.itemId}", Toast.LENGTH_LONG).show()
        true
    }
    view.setOnClickListener {
        popMenu.show()
    }
}

@BindingAdapter("checkedColor")
fun setCheckedColor(view: TextView, checked: Boolean?) {
    val color = getColor(if (checked == true) R.color.blue728 else R.color.grey)
    view.apply {
        (background as? GradientDrawable)?.setStroke(if (checked == true) 2.toDp() else 1.toDp(), color)
        setTextColor(color)
        (compoundDrawables + compoundDrawablesRelative).forEach { it?.setTint(color) }
    }
}

@BindingAdapter(value = ["checkedColorWithStartDrawable", "drawable"], requireAll = true)
fun setCheckedColor(view: TextView, checked: Boolean?, startDrawable: Drawable) {
    val color = getColor(if (checked == true) R.color.blue728 else R.color.grey)
    view.apply {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            startDrawable,
            compoundDrawablesRelative.elementAtOrNull(1) ?: compoundDrawables.elementAtOrNull(1) ,
            compoundDrawablesRelative.elementAtOrNull(2) ?: compoundDrawables.elementAtOrNull(2),
            compoundDrawablesRelative.elementAtOrNull(3) ?: compoundDrawables.elementAtOrNull(3)
        )
        (background as? GradientDrawable)?.setStroke(if (checked == true) 2.toDp() else 1.toDp(), color)
        setTextColor(color)
        (compoundDrawables + compoundDrawablesRelative).forEach { it?.setTint(color) }
    }
}