package com.example.project_2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project_2.database.typeConverters.CharacterTrackerDatabase;

import java.util.Objects;

/**
 * this is a POJO meant to represent the Character Tracker
 */

@Entity(tableName = CharacterTrackerDatabase.CHARACTER_TABLE)
public class DNDCharacter {

    @PrimaryKey(autoGenerate = true)
    private int characterId;

    private int userId;

    private String name;

    private String race;

    private String characterClass;

    private int level;

    private int strength;

    private int dexterity;

    private int constitution;

    private int intelligence;

    private int wisdom;

    private int charisma;

    public DNDCharacter(String name, String race, String characterClass, int level, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int userId) {
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
        this.level = level;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.userId = userId;
    }



    @NonNull
    @Override
    public String toString() {
        return "CharacterTracker{" +
                "characterId=" + characterId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", characterClass='" + characterClass + '\'' +
                ", level=" + level +
                ", strength=" + strength +
                ", dexterity=" + dexterity +
                ", constitution=" + constitution +
                ", intelligence=" + intelligence +
                ", wisdom=" + wisdom +
                ", charisma=" + charisma +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNDCharacter character = (DNDCharacter) o;
        return characterId == character.characterId && userId == character.userId && level == character.level && strength == character.strength && dexterity == character.dexterity && constitution == character.constitution && intelligence == character.intelligence && wisdom == character.wisdom && charisma == character.charisma && Objects.equals(name, character.name) && Objects.equals(race, character.race) && Objects.equals(characterClass, character.characterClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterId, userId, name, race, characterClass, level, strength, dexterity, constitution, intelligence, wisdom, charisma);
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }
}
