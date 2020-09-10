package ru.practiceground.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.data.room.dao.LikeableDao
import ru.practiceground.data.room.entity.LikeableEntity

@Database(entities = [LikeableEntity::class], version = 2, exportSchema = false)
abstract class LikeableDataBase : RoomDatabase() {

    abstract fun likeableDao(): LikeableDao

    companion object {
        private var INSTANCE: LikeableDataBase? = null

        fun getInstance(context: Context, scope: CoroutineScope): LikeableDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    LikeableDataBase::class.java,
                    TABLE_NAME_LIKEABLE
                ).addCallback(LikeableCallBack(scope)).build()
                INSTANCE!!
            }
        }
    }

    private class LikeableCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { instance ->
                scope.launch(Dispatchers.IO) {
                    repeat(150) { index ->
                        instance.likeableDao().insert(LikeableEntity(text = "$index SampleText"))
                    }
                }
            }
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)
            INSTANCE?.let { instance ->
                scope.launch(Dispatchers.IO) {
                    instance.likeableDao().clear()
                    repeat(150) { index ->
                        instance.likeableDao().insert(LikeableEntity(text = "$index SampleText"))
                    }
                }
            }
        }
    }
}