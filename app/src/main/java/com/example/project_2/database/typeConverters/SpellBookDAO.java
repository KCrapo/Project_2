package com.example.project_2.database.typeConverters;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.SpellBook;

import java.util.List;

@Dao
public interface SpellBookDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SpellBook...spellBook);

    @Delete
    void delete(SpellBook spellBook);

    @Query("DELETE from " + "SpellBookTable")
    void deleteAll();

    @Query("DELETE from " + "SpellBookTable" + " WHERE spellBookId == :spellBookId")
    void deleteSpellBookById(int spellBookId);

    @Query("DELETE from " + "SpellBookTable" + " WHERE characterId == :characterId")
    void deleteSpellBookByCharacterId(int characterId);

    @Query(" SELECT * FROM " + "SpellBookTable"+ " ORDER BY characterId")
    LiveData<List<SpellBook>> getAllSpellBooks();

    @Query("SELECT * from "+ "SpellBookTable"+ " WHERE characterId == :characterId")
    LiveData<SpellBook> getSpellBookByCharacterId(int characterId);

    @Query("SELECT * from "+ "SpellBookTable"+ " WHERE spellBookId == :spellBookId")
    LiveData<SpellBook> getSpellBookBySpellBookId(int spellBookId);
}