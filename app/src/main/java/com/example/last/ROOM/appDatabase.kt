package com.example.last.ROOM

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.last.ROOM.achive.achive
import com.example.last.ROOM.achive.achiveDao
import com.example.last.ROOM.memo.memo
import com.example.last.ROOM.memo.memoDao
import com.example.last.ROOM.schedule.schedule
import com.example.last.ROOM.schedule.scheduleDao
import kotlinx.coroutines.*
import java.util.*

@Database(entities = [schedule::class, memo::class, achive::class], version = 15, exportSchema = false )
@TypeConverters(typeConverter::class)
abstract class appDatabase: RoomDatabase(){
    abstract fun scheduleDao() : scheduleDao
    abstract fun memoDao(): memoDao
    abstract fun achiveDao(): achiveDao

    companion object{
        @Volatile
        private var INSTANCE: appDatabase? = null
        fun getDatabase(context: Context): appDatabase{
            synchronized(this){
                var instance:appDatabase? = INSTANCE
                    if(instance == null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            appDatabase::class.java,
                            "Database").fallbackToDestructiveMigration().build()
                    }
                return instance
            }



        /*
            if(temp != null)
                return temp
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "Database"
                ).fallbackToDestructiveMigration()
                    .addCallback(dbCallBack(scope))
                    .build()
                INSTANCE = instance
                return instance
            }*/
        }
    }
    private class dbCallBack() : RoomDatabase.Callback(){
        //private val scope: CoroutineScope
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    database.scheduleDao().deleteAllSchedule()
                    database.memoDao().deleteAllMemo()
                    database.achiveDao().deleteAllAchive()

                    //val now = System.currentTimeMillis()
                    //val date = Date(now)


                    //val sched = schedule(0, "2021 11 02", "TitleS", "ContentS")
                    val memo = memo(0, "TitleM", "ContentM", "1111", "2222")
                    val achive = achive(2, "TitleA", "ContentA")

                    //database.scheduleDao().insertSchedule(sched)
                    database.memoDao().insertMemo(memo)
                    database.achiveDao().insertAchive(achive)

                    println(memo.Title + "스트링")
                    Log.i("데이터베이스", "instance???")

                }


            }

        }


    }
    /*
    private class dbCallBack() : RoomDatabase.Callback(){
        //private val scope: CoroutineScope
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    database.scheduleDao().deleteAllSchedule()
                    database.memoDao().deleteAllMemo()
                    database.achiveDao().deleteAllAchive()

                    //val now = System.currentTimeMillis()
                    //val date = Date(now)


                    val sched = schedule(1, "TitleS", "ContentS")
                    val memo = memo(0, "TitleM", "ContentM", 1111, 2222, false)
                    val achive = achive(2, "TitleA", "ContentA")

                    database.scheduleDao().insertSchedule(sched)
                    database.memoDao().insertMemo(memo)
                    database.achiveDao().insertAchive(achive)

                    println(memo.Title + "스트링")
                    Log.i("데이터베이스", "instance???")

                }


                }

            }


        }*/
/*
    private class dbCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        //private val scope: CoroutineScope
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    initValue(database.scheduleDao(), database.memoDao(), database.achiveDao())
                }

        }


        }

        private suspend fun initValue(scheduleDao: scheduleDao, memoDao: memoDao, achiveDao: achiveDao) {
            scheduleDao.deleteAllSchedule()
            memoDao.deleteAllMemo()
            achiveDao.deleteAllAchive()

            val now = System.currentTimeMillis()
            val date = Date(now)


            val sched = schedule(1, "TitleS", "ContentS")
            val memo = memo(0, "TitleM", "ContentM", 1111, 2222, false)
            val achive = achive(2, "TitleA", "ContentA")

            scheduleDao.insertSchedule(sched)
            memoDao.insertMemo(memo)
            achiveDao.insertAchive(achive)
        }

    }*/

}

/*
    companion object{
        @Volatile
        private var INSTANCE: appDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): appDatabase{
            val temp = INSTANCE
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "Database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(dbCallBack(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }

        /*
            if(temp != null)
                return temp
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "Database"
                ).fallbackToDestructiveMigration()
                    .addCallback(dbCallBack(scope))
                    .build()
                INSTANCE = instance
                return instance
            }*/
        }
    }

    private class dbCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    initValue(database.scheduleDao(), database.memoDao(), database.achiveDao())
                }
            }

        }

        suspend fun initValue(scheduleDao: scheduleDao, memoDao: memoDao, achiveDao: achiveDao){
            scheduleDao.deleteAllSchedule()
            memoDao.deleteAllMemo()
            achiveDao.deleteAllAchive()

            val now = System.currentTimeMillis()
            val date = Date(now)


            val sched = schedule(1, "TitleS", "ContentS")
            val memo = memo(1, "TitleM", "ContentM", 1111, 2222, false)
            val achive = achive(2, "TitleA", "ContentA")

            scheduleDao.insertSchedule(sched)
            memoDao.insertMemo(memo)
            achiveDao.insertAchive(achive)


        }
    }

 */
/*
        fun getDatabase(context: Context, scope: CoroutineScope): appDatabase{ //context: Context, scope: CoroutineScope
            val temp = INSTANCE
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "Database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(dbCallBack(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
 */