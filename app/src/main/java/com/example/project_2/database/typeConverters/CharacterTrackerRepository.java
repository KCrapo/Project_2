package com.example.project_2.database.typeConverters;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.User;
import com.example.project_2.viewHolders.MainActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CharacterTrackerRepository {
    private final UserDAO userDAO;

    private final CharacterDAO characterDAO;

    private static CharacterTrackerRepository repository;


    /**
     *
     * added characterDAO to characterTrackerRepository
     */
    private CharacterTrackerRepository(Application application) {
        CharacterTrackerDatabase db = CharacterTrackerDatabase.getDatabase(application);
        this.userDAO = db.userDao();
        this.characterDAO = db.characterDao();
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

    public void deleteUser(User user) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(user);
        });
    }

    /**
     *
     * @param DNDCharacter
     * adding insertCharacter function
     * Not sure if I need to change it to be (Character... character),
     * kept getting error when I would try to set it that way
     */
    public void insertCharacter(DNDCharacter DNDCharacter) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            characterDAO.insert(DNDCharacter);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return (userDAO.getUserByUserName(username));


    }

    public LiveData<User> getUserByUserId(int userId) {
        return (userDAO.getUserByUserId(userId));


    }
    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
       // I tried this without the new thread statement and it said i wasn't allowed to do this on main thread
        // I looked this up as a potential solution. It works but I want to know if this is something standard
        new Thread(() -> {
            userDAO.updateUser(user);
        }).start();
    }


    /**
     *Added methods to get characters by charactername and character ID
     */
    public LiveData<DNDCharacter> getCharacterByName(String name) {
        return (characterDAO.getCharacterByName(name));
    }

    public LiveData<DNDCharacter> getCharacterByCharacterId(int characterId) {
        return (characterDAO.getCharacterByCharacterId(characterId));
    }


}
