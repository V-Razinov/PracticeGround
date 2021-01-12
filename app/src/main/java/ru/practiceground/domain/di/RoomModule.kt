package ru.practiceground.domain.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.App
import ru.practiceground.data.room.LikeableDataBase
import ru.practiceground.data.room.TABLE_NAME_LIKEABLE
import ru.practiceground.data.room.dao.LikeableDao
import ru.practiceground.data.room.entity.LikeableEntity
import ru.practiceground.data.room.repos.LikeableRepository
import javax.inject.Singleton

@Module
class RoomModule() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val dataBase: LikeableDataBase = Room.databaseBuilder(
        App.context,
        LikeableDataBase::class.java,
        TABLE_NAME_LIKEABLE
    ).addCallback(callback).build()
    private var likeableRepository: LikeableRepository? = null

    @Singleton
    @Provides
    fun provideLikeableDao(dataBase: LikeableDataBase): LikeableDao {
        return dataBase.likeableDao()
    }

    @Singleton
    @Provides
    fun provideDataBase(): LikeableDataBase {
        return dataBase
    }

    @Singleton
    @Provides
    fun provideRepo(likeableDao: LikeableDao): LikeableRepository {
        return likeableRepository ?: synchronized(this) {
            likeableRepository = LikeableRepository(likeableDao)
            likeableRepository!!
        }
    }

    private val callback get() = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch {
                repeat(150) { index ->
                    dataBase.likeableDao().insert(LikeableEntity(text = "$index SampleText"))
                }
            }
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)
            scope.launch(Dispatchers.IO) {
                dataBase.likeableDao().clear()
                repeat(150) { index ->
                    dataBase.likeableDao().insert(LikeableEntity(text = "$index SampleText"))
                }
            }
        }
    }
}