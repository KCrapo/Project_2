package com.example.project_2.database.typeConverters;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.DNDCharacter;

import java.util.List;

@Dao
public interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DNDCharacter character);

    @Delete
    void delete(DNDCharacter character);

    @Query(" SELECT * FROM " + "characterTable"+ " ORDER BY name")
    LiveData<List<DNDCharacter>> getAllCharacters();

    @Query("SELECT * from "+ "characterTable"+ " WHERE name == :name")
    LiveData<DNDCharacter> getCharacterByName(String name);

    @Query("DELETE from " + "characterTable")
    void deleteAll();

    @Query("SELECT * from "+ "characterTable"+ " WHERE characterId == :characterId")
    LiveData<DNDCharacter> getCharacterByCharacterId(int characterId);

    @Query("SELECT * FROM " + "characterTable" + " WHERE userId == :userId")
    LiveData<List<DNDCharacter>> getAllCharactersByUserId(int userId);
}
