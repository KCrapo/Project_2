package com.example.project_2.database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;

import java.util.Objects;

@Entity(tableName = CharacterTrackerDatabase.MACROS_TABLE)
public class Macro {
    @PrimaryKey(autoGenerate = true)
    private int macroId;

    private int characterId;

    private String macro;

    public Macro(int characterId, String macro) {
        this.characterId = characterId;
        this.macro = macro;
    }

    public int getMacroId() {
        return macroId;
    }
    public void setMacroId(int macroId) {
        this.macroId = macroId;
    }
    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
    public String getMacro() {
        return macro;
    }
    public void setMacro(String macro) {
        this.macro = macro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Macro macros = (Macro) o;
        return macroId == macros.macroId && characterId == macros.characterId && Objects.equals(macro, macros.macro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(macroId, characterId, macro);
    }

    @Override
    public String toString() {
        return "Macros{" +
                "macroId=" + macroId +
                ", characterId=" + characterId +
                ", macro='" + macro + '\'' +
                '}';
    }
}
