package com.example.project_2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;

import java.util.Objects;

@Entity(tableName = CharacterTrackerDatabase.SPELL_TABLE)
public class Spell {

    @PrimaryKey(autoGenerate = true)
    private int spellId;

    private String spellName;

    public Spell(String spellName) {
        this.spellName = spellName;
    }

    public int getSpellId() {
        return spellId;
    }
    public void setSpellId(int spellId) {
        this.spellId = spellId;
    }
    public String getSpellName() {
        return spellName;
    }
    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return spellId == spell.spellId && Objects.equals(spellName, spell.spellName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spellId, spellName);
    }

    @Override
    public String toString() {
        return "Spell{" +
                "spellId=" + spellId +
                ", spellName='" + spellName + '\'' +
                '}';
    }
}
