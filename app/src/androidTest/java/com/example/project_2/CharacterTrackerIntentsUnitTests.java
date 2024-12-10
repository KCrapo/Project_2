package com.example.project_2;

import static com.example.project_2.viewHolders.CharacterCreationActivity.CHARACTER_CREATOR_USER_ID;
import static com.example.project_2.viewHolders.CharacterViewActivity.CHARACTER_VIEW_CHARACTER_ID;
import static com.example.project_2.viewHolders.MainActivity.MAIN_ACTIVITY_USER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project_2.viewHolders.AdminActivity;
import com.example.project_2.viewHolders.CharacterCreationActivity;
import com.example.project_2.viewHolders.CharacterViewActivity;
import com.example.project_2.viewHolders.CreateAccountActivity;
import com.example.project_2.viewHolders.LoginActivity;
import com.example.project_2.viewHolders.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CharacterTrackerIntentsUnitTests {


    @Test
    public void testLoginIntent() {
        // Mock the Context, to avoid launching real activities (taken from android website)
        Context context = ApplicationProvider.getApplicationContext();

        // Creating the intent using the factory
        Intent intent = LoginActivity.loginIntentFactory(context);

        // Check that the correct class is being passed
        // https://developer.android.com/reference/android/content/Intent#getComponent()
        assertEquals(LoginActivity.class.getName(), Objects.requireNonNull(intent.getComponent()).getClassName());

        // No extras to check

    }

    @Test
    public void testMainActivityIntentFactory() {

        Context context = ApplicationProvider.getApplicationContext();

        int expectedUserId = 123;

        // Creating the intent using the factory
        Intent intent = MainActivity.mainActivityIntentFactory(context, expectedUserId);

        // Verify that the correct intent class is being passed
        assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());

        // Check the extras
        assertTrue(intent.hasExtra(MAIN_ACTIVITY_USER_ID));
        assertEquals(expectedUserId, intent.getIntExtra(MAIN_ACTIVITY_USER_ID, -1));
    }

    @Test
    public void testCharacterCreationActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();

        int expectedUserId = 123;

        // Creating the intent using the factory
        Intent intent = CharacterCreationActivity.characterCreationIntentFactory(context, expectedUserId);

        // Verify that the correct intent class is being passed
        assertEquals(CharacterCreationActivity.class.getName(), intent.getComponent().getClassName());

        // Check the extras
        assertTrue(intent.hasExtra(CHARACTER_CREATOR_USER_ID));
        assertEquals(expectedUserId, intent.getIntExtra(CHARACTER_CREATOR_USER_ID, -1));

    }

    @Test
    public void testCreateAccountActivityIntentFactory() {

        // Mock the Context
        Context context = ApplicationProvider.getApplicationContext();

        // Creating the intent using the factory
        Intent intent = CreateAccountActivity.createAccountIntentFactory(context);

        // Check that the correct class is being passed
        assertEquals(CreateAccountActivity.class.getName(), Objects.requireNonNull(intent.getComponent()).getClassName());

        // No extras to check

    }

    @Test
    public void testCharacterViewActivity() {

        // Mock the Context
        Context context = ApplicationProvider.getApplicationContext();

        int expectedUserId = 123;

        // Creating the intent using the factory
        Intent intent = CharacterViewActivity.characterViewIntentFactory(context, expectedUserId);

        // Verify that the correct intent class is being passed
        assertEquals(CharacterViewActivity.class.getName(), intent.getComponent().getClassName());

        // Check the extras
        assertTrue(intent.hasExtra(CHARACTER_VIEW_CHARACTER_ID));
        assertEquals(expectedUserId, intent.getIntExtra(CHARACTER_VIEW_CHARACTER_ID, -1));


    }

    @Test
    public void testAdminActivity() {
        // Mock the Context
        Context context = ApplicationProvider.getApplicationContext();

        // Creating the intent using the factory
        Intent intent = AdminActivity.AdminIntentFactory(context);

        // Check that the correct class is being passed
        assertEquals(AdminActivity.class.getName(), Objects.requireNonNull(intent.getComponent()).getClassName());

        // No extras to check
    }
}