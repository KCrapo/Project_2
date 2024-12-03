package com.example.project_2.database.typeConverters;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.Character;

import java.util.List;

@Dao
public interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Character... character);

    @Delete
    void delete(Character character);

    @Query(" SELECT * FROM " + "CharacterTable"+ " ORDER BY name ASC")
    LiveData<List<Character>> getAllCharacters();

    @Query("SELECT * from "+ "characterTable"+ " WHERE name == :name")
    LiveData<Character> getCharacterByName(String name);


    @Query("DELETE from " + "CharacterTable")
    void deleteAll();

    @Query("SELECT * from "+ "characterTable"+ " WHERE characterId == :characterId")
    LiveData<Character> getCharacterByCharacterId(int characterId);

    @Query("SELECT * FROM " + CharacterTrackerDatabase.CHARACTER_TABLE)
    List<Character> getAllRecords();
}
