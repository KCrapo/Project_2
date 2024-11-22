package com.example.project_2.typeConverters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project_2.database.entities.User;
import com.example.project_2.viewHolders.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { User.class}, version = 1, exportSchema = false)
public abstract class CharacterTrackerDatabase extends RoomDatabase {

    public static final String USER_TABLE = "userTable";

    private static final String DATABASE_NAME = "CharacterTrackerDatabase";

    private static volatile CharacterTrackerDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    // Create a service that will supply threads to perform database operations
    // Create all at start up and put them in a pool
    // when you need to do database operations pull a thread from the pool
    // when you are done using it , it goes back in the pool
    // WE have a maximum of 4 threads as shown by NUMBER_OF_THREADS
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CharacterTrackerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {

            // a keyword that basically means:
            // "Make sure that this class that im supplying on has nothing else working on it"
            // All happening in the same thread/place

            synchronized (CharacterTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    CharacterTrackerDatabase.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();

                    // .fallbackToDestructiveMigration Notes
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database Created");


            // Use this as a way to add default records into database
            // When our database is created we call this, and it will perform said action
            // when database is created
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDao();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testUser1", "testUser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract UserDAO userDao();

}
