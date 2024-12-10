package com.example.project_2.database.typeConverters;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.Spell;

import java.util.List;

@Dao
public interface SpellDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Spell...spell);

    @Delete
    void delete(Spell spell);

    @Query("DELETE from " + "SpellTable")
    void deleteAll();

    @Query("DELETE from " + "SpellTable" + " WHERE spellId == :spellId")
    void deleteSpellById(int spellId);

    @Query(" SELECT * FROM " + "SpellTable"+ " ORDER BY spellId")
    LiveData<List<Spell>> getAllSpells();

    @Query("SELECT * from "+ "SpellTable"+ " WHERE spellId == :spellId")
    LiveData<Spell> getSpellBySpellId(int spellId);

    @Query("SELECT * from "+ "SpellTable"+ " WHERE spellName == :spellName")
    LiveData<Spell> getSpellBySpellName(String spellName);
}
