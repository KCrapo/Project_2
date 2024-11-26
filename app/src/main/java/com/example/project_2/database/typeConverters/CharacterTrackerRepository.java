package com.example.project_2.database.typeConverters;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project_2.database.entities.User;
import com.example.project_2.viewHolders.MainActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CharacterTrackerRepository {
    private final UserDAO userDAO;

    private static CharacterTrackerRepository repository;

    private CharacterTrackerRepository(Application application) {
        CharacterTrackerDatabase db = CharacterTrackerDatabase.getDatabase(application);
        this.userDAO = db.userDao();
    }

    // Dont know if we will stick with this function. May switch over to liveData Method
    public static CharacterTrackerRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<CharacterTrackerRepository> future = CharacterTrackerDatabase.databaseWriteExecutor.submit(
                new Callable<CharacterTrackerRepository>() {
                    @Override
                    public CharacterTrackerRepository call() throws Exception {
                        CharacterTrackerRepository characterTrackerRepository = new CharacterTrackerRepository(application);
                        return characterTrackerRepository;
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem getting, CharacterTracker Repository thread error.");
        }
        return null;
    }

    public void insertUser(User... user) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });

    }

    public LiveData<User> getUserByUserName(String username) {
        return (userDAO.getUserByUserName(username));


    }

    public LiveData<User> getUserByUserId(int userId) {
        return (userDAO.getUserByUserId(userId));


    }
}
