package com.example.project_2.typeConverters;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User...user);

    @Delete
    void delete(User user);

    @Query(" SELECT * FROM " + "UserTable"+ " ORDER BY username")
    LiveData<User> getAllUsers();

    @Query("DELETE from " + "UserTable")
    void deleteAll();

    @Query("SELECT * from "+ "UserTable"+ " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * from "+ "UserTable"+ " WHERE id == :userId ")
    LiveData<User> getUserByUserId(int userId);
}
