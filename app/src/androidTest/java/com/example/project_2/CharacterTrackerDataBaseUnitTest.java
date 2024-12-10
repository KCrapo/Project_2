package com.example.project_2;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import static org.junit.Assert.assertNull;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.User;
import com.example.project_2.database.typeConverters.CharacterDAO;
import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;
import com.example.project_2.database.typeConverters.UserDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

/**
 * SOMEOF THIS WORKS : Live Data is really difficult to unit test,
 *
 * Below are the resources I used to try and figure out how to accomplish these tests.
 *
 * Overall I think the best solution would be looking into Mockit and figuring out how
 * to "Mock" Livedata for testing purposes
 *
 * Using below for reference :
 *
 * https://developer.android.com/training/data-storage/room/testing-db
 * https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
 * POTENTIAL SOLUTION to problems (or harbinger of despair) https://github.com/android/codelab-android-room-with-a-view/blob/master/app/src/androidTest/java/com/example/android/roomwordssample/LiveDataTestUtil.java
 * https://medium.com/swlh/unit-testing-livedata-with-mockito-and-truth-b096535cf57e
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
        characterDao = db.characterDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    ///////////////////////////////// User Table Tests //////////////////////////////////////////


    ///// Insert and Retrieve by Username Test
    @Test
    public void testInsertAndGetUserByUsername() {
        // Create a user
        User user = new User("testuser", "password12345");

        // insert into db
        userDao.insert(user);

        // Ensure the observation is done on the main thread (This was the main problem)
        // According to Android website InstrumentationRegistry is deprecated buuuut it works. Absolutely will not take credit for coming up with this on my own. Looking
        // for alternative strategies to achieve same effect
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {


            // Get the user by username
            LiveData<User> liveDataUser = userDao.getUserByUserName("testuser");

            // Observe LiveData on the main thread
            // Reference https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
            liveDataUser.observeForever(new Observer<User>() {
                @Override
                public void onChanged(User retrievedUser) {
                    // Assert that the retrieved user has correct data
                    assertEquals(user.getUserName(), retrievedUser.getUserName());
                    assertEquals(user.getPassword(), retrievedUser.getPassword());

                    // Stop observing once we have the data
                    liveDataUser.removeObserver(this);
                }
            });
        });
    }


    // Test for updating a user
    @Test
    public void testUpdateUser() {
        User user = new User("testuser", "password123");
        userDao.insert(user);

        // Ensure the observation is done on the main thread (This was the main problem)
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {

            LiveData<User> liveDataUser = userDao.getUserByUserName("testuser");
            liveDataUser.observeForever(new Observer<User>() {
                @Override
                public void onChanged(User retrievedUser) {
                    retrievedUser.setPassword("newpassword456");
                    userDao.insert(retrievedUser);

                    // Re-fetch and verify the updated password
                    LiveData<User> liveDataUpdatedUser = userDao.getUserByUserId(retrievedUser.getId());
                    liveDataUpdatedUser.observeForever(new Observer<User>() {
                        @Override
                        public void onChanged(User updatedUser) {
                            assertEquals("newpassword456", updatedUser.getPassword());
                            liveDataUpdatedUser.removeObserver(this);
                        }
                    });
                    liveDataUser.removeObserver(this);
                }
            });
        });
    }

    // Test for deleting user
    @Test
    public void testDeleteUser() {
        User user = new User("testuser", "password123");

        // insert user
        userDao.insert(user);

        // Ensure the observation is done on the main thread
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {

            // Get the user by username before deletion
            LiveData<User> liveDataUser = userDao.getUserByUserName("testuser");

            // Observe the LiveData to check if the user is successfully fetched
            liveDataUser.observeForever(new Observer<User>() {
                @Override
                public void onChanged(User retrievedUser) {
                    // Delete the user
                    userDao.delete(retrievedUser);

                    // Now that the user is deleted, get the LiveData again to verify deletion (Holy Crap this is getting complicated and i am lost in the sauce)
                    LiveData<User> liveDataAfterDeletion = userDao.getUserByUserName("testuser");

                    // Observe the LiveData after deletion
                    liveDataAfterDeletion.observeForever(new Observer<User>() {
                        @Override
                        public void onChanged(User deletedUser) {
                            // Assert that the deleted user is now null (i.e., user doesn't exist anymore)
                            assertNull(deletedUser);
                            liveDataAfterDeletion.removeObserver(this);
                        }
                    });
                    liveDataUser.removeObserver(this);
                }
            });
        });
    }


    ////////////// DND Character Table Unit tests ////////////////////////////////////

    // Test for inserting and getting a character by name
    @Test
    public void testInsertAndGetCharacterByName() {
        DNDCharacter character = new DNDCharacter("Tav 1", "Elf", "Barbarian", 3,
                12, 14, 16, 17, 10, 8, 1);
        ;
        characterDao.insert(character);



        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            LiveData<DNDCharacter> liveDataCharacter = characterDao.getCharacterByName("Tav 1");
            liveDataCharacter.observeForever(new Observer<DNDCharacter>() {
                @Override
                public void onChanged(DNDCharacter retrievedCharacter) {
                    assertEquals(character.getName(), retrievedCharacter.getName());
                    liveDataCharacter.removeObserver(this);
                }
            });
        });
    }

    @Test
    public void testUpdateCharacter() {
        DNDCharacter character = new DNDCharacter("Tav 1", "Elf", "Barbarian", 3,
                12, 14, 16, 17, 10, 8, 1);
        characterDao.insert(character);

        // Ensure the observation is done on the main thread
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {

            LiveData<DNDCharacter> liveDataCharacter = characterDao.getCharacterByName("Tav 1");
            liveDataCharacter.observeForever(new Observer<DNDCharacter>() {
                @Override
                public void onChanged(DNDCharacter retrievedCharacter) {
                    // Update the character's class and level
                    retrievedCharacter.setCharacterClass("Wizard");
                    retrievedCharacter.setLevel(5);
                    characterDao.insert(retrievedCharacter);

                    // Re-fetch and verify the updated values
                    LiveData<DNDCharacter> liveDataUpdatedCharacter = characterDao.getCharacterByName("Tav 1");
                    liveDataUpdatedCharacter.observeForever(new Observer<DNDCharacter>() {
                        @Override
                        public void onChanged(DNDCharacter updatedCharacter) {
                            assertEquals("Wizard", updatedCharacter.getCharacterClass());
                            assertEquals(5, updatedCharacter.getLevel());
                            liveDataUpdatedCharacter.removeObserver(this);
                        }
                    });
                    liveDataCharacter.removeObserver(this);
                }
            });
        });
    }

    @Test
    public void testDeleteCharacter() {
        DNDCharacter character = new DNDCharacter("Tav 1", "Elf", "Barbarian", 3,
                12, 14, 16, 17, 10, 8, 1);
        characterDao.insert(character);

        // Ensure the observation is done on the main thread
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            LiveData<DNDCharacter> liveDataCharacter = characterDao.getCharacterByName("Tav 1");
            liveDataCharacter.observeForever(new Observer<DNDCharacter>() {
                @Override
                public void onChanged(DNDCharacter retrievedCharacter) {
                    characterDao.delete(retrievedCharacter); // Delete the character

                    LiveData<DNDCharacter> liveDataAfterDeletion = characterDao.getCharacterByName("Tav 1");
                    liveDataAfterDeletion.observeForever(new Observer<DNDCharacter>() {
                        @Override
                        public void onChanged(DNDCharacter deletedCharacter) {
                            // Assert that the character is deleted (it should be null)
                            assertNull(deletedCharacter);
                            liveDataAfterDeletion.removeObserver(this);
                        }
                    });
                    liveDataCharacter.removeObserver(this);
                }
            });
        });
    }


}


// Notes : Sometimes (If i am lucky) all the unit tests will pass. Working with live data is really confusing
// and I am lost in the sauce. I started to research alternative methods for testing.
// POTENTIAL SOLUTION https://github.com/android/codelab-android-room-with-a-view/blob/master/app/src/androidTest/java/com/example/android/roomwordssample/LiveDataTestUtil.java