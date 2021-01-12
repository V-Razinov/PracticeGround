package ru.practiceground

import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.practiceground.domain.di.RoomModule
import ru.practiceground.presentation.roomlivedata.RoomLiveDataSubComponentComponent
import javax.inject.Singleton

@Component()
interface AppComponent {
    /**
     * @package ru.practiceground.presentation.roomlivedata
     */
    fun viewPagerComponent(): RoomLiveDataSubComponentComponent
}