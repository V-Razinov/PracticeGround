package ru.practiceground.presentation.doublevpmotion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.practiceground.databinding.FragmentDoubleVpBinding
import ru.practiceground.databinding.ViewPageBinding
import ru.practiceground.other.extensions.string
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.base.BaseViewModel

class DoubleVpFragment : BaseFragment() {
    override val viewModel: DoubleVpViewModel by viewModels()
    private lateinit var binding: FragmentDoubleVpBinding

    private val items = listOf(
        PagerData(0),
        PagerData(1),
        PagerData(2),
        PagerData(3),
        PagerData(4)
    )
    private val adapterLeft = MyPagerAdapter().apply { setData(items) }
    private val adapterRight = MyPagerAdapter().apply { setData(items) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoubleVpBinding.inflate(layoutInflater, container, false).apply {
            vpLeft.adapter = adapterLeft
            vpRight.adapter = adapterRight
        }
        return binding.root
    }

    data class PagerData(val number: Int)

    class MyPagerAdapter : RecyclerView.Adapter<PageVH>() {

        private var items = emptyList<PagerData>()

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageVH {
            return PageVH(ViewPageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: PageVH, position: Int) {
            holder.bind(items[position])
        }

        fun setData(items: List<PagerData>) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    class PageVH(private val binding: ViewPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pagerData: PagerData) {
            binding.price.text = pagerData.number.string
        }
    }
}