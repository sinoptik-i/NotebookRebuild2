package sin.android.notebook.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration

import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = arrayOf(Note::class),
    version = 3,
  /*  autoMigrations = [
        AutoMigration(from=2, to=3)
    ]*/
    )
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        //@Volatile - za4em ono nado, esli est synchronized?
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration()
                  //  .addMigrations(migration_1_2)
                 //   .addMigrations(migration_2_3)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}

  val migration_1_2 =object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE notes ADD COLUMN time INT DEFAULT 0 NOT NULL"
        )
    }
}

/*
val migration_2_3= object :Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE notes ALTER COLUMN time STRING"
        )
    }

}*/
