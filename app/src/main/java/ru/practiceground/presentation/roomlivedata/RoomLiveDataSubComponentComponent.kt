package ru.practiceground.presentation.roomlivedata

import dagger.Subcomponent
import ru.practiceground.domain.di.RoomModule
import ru.practiceground.presentation.roomlivedata.pages.all.PageAllViewModel
import ru.practiceground.presentation.roomlivedata.pages.favs.PageFavsViewModel
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [RoomModule::class])
interface RoomLiveDataSubComponentComponent {
    fun inject(vm: ViewPagerViewModel)
    fun inject(vm: PageAllViewModel)
    fun inject(vm: PageFavsViewModel)
}