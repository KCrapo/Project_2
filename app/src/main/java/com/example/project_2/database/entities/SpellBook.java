package com.example.project_2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;

import java.util.Objects;

@Entity(tableName = CharacterTrackerDatabase.SPELL_BOOK_TABLE)
public class SpellBook {

    @PrimaryKey(autoGenerate = true)
    private int spellBookId;

    private int characterId;
    private int spellId;

    public SpellBook(int characterId, int spellId) {
        this.characterId = characterId;
        this.spellId = spellId;
    }

    public int getSpellBookId() {
        return spellBookId;
    }
    public void setSpellBookId(int spellBookId) {
        this.spellBookId = spellBookId;
    }
    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
    public int getSpellId() {
        return spellId;
    }
    public void setSpellId(int spellId) {
        this.spellId = spellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellBook spellBook = (SpellBook) o;
        return spellBookId == spellBook.spellBookId && characterId == spellBook.characterId && spellId == spellBook.spellId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(spellBookId, characterId, spellId);
    }

    @Override
    public String toString() {
        return "SpellBook{" +
                "spellBookId=" + spellBookId +
                ", characterId=" + characterId +
                ", spellId=" + spellId +
                '}';
    }
}
