package com.example.project_2.database.typeConverters;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project_2.database.entities.Macro;

import java.util.List;

@Dao
public interface MacroDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Macro...macro);

    @Query("DELETE from " + "MacrosTable")
    void deleteAll();

    @Query("DELETE from " + "MacrosTable" + " WHERE macroId == :macroId")
    void deleteMacroById(int macroId);

    @Query("DELETE from " + "MacrosTable" + " WHERE characterId == :characterId")
    void deleteMacroByCharacterId(int characterId);

    @Query(" SELECT * FROM " + "MacrosTable" + " ORDER BY macroId")
    LiveData<List<Macro>> getAllMacros();

    @Query("SELECT * from "+ "MacrosTable" + " WHERE characterId == :characterId")
    LiveData<List<Macro>> getMacroByCharacterId(int characterId);

    @Query("SELECT * from "+ "MacrosTable" + " WHERE macroId == :macroId")
    LiveData<Macro> getMacroByMacroId(int macroId);

    @Delete
    void delete(Macro macro);
}
