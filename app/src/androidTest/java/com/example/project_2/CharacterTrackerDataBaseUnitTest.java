package com.example.project_2;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterDAO;
import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;
import com.example.project_2.database.typeConverters.UserDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Unit test for CharacterTrackerDatabase.
 * Using below for reference
 * https://developer.android.com/training/data-storage/room/testing-db
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CharacterTrackerDataBaseUnitTest {
    private CharacterTrackerDatabase db;
    private UserDAO userDao;
    private CharacterDAO characterDao;

    // This is functionally from the android developer website
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CharacterTrackerDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsertAndGetUserByUsername() {
        // Create a user
        User user = new User("testuser", "password12345");

        // insert into db
        userDao.insert(user);

        // Create a LiveData reference to observe the result (Thi
        LiveData<User> liveDataUser = userDao.getUserByUserName("testuser");

        // Observe the LiveData and assert the result when it is received
        // Handling LiveData in unit testing was way harder than I Imagined
        // References:
        // https://developer.android.com/training/data-storage/room/testing-db
        //https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04

        liveDataUser.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User retrievedUser) {
                // Assert that the retrieved user matches the inserted user
                assertEquals(user.getUserName(), retrievedUser.getUserName());
                assertEquals(user.getPassword(), retrievedUser.getPassword());

                // Stop observing as we no longer need to observe for this test
                liveDataUser.removeObserver(this);
            }
        });


    }

    public void testInsertAndGetUserById() {
        // Create a user
        User user = new User("testuser", "password12345");

        // insert into db
        userDao.insert(user);

        // Create a LiveData reference to observe the result
        LiveData<User> liveDataUser = userDao.getUserByUserId(user.getId());

        liveDataUser.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User retrievedUser) {
                // Assert that the retrieved user matches the inserted user
                assertEquals(user.getUserName(), retrievedUser.getUserName());
                assertEquals(user.getPassword(), retrievedUser.getPassword());

                // Stop observing as we no longer need to observe for this test
                liveDataUser.removeObserver(this);
            }
        });

    }
}
