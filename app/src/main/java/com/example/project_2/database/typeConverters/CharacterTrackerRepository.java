package com.example.project_2.database.typeConverters;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.Inventory;
import com.example.project_2.database.entities.InventoryItem;
import com.example.project_2.database.entities.Macro;
import com.example.project_2.database.entities.Spell;
import com.example.project_2.database.entities.SpellBook;
import com.example.project_2.database.entities.User;
import com.example.project_2.viewHolders.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CharacterTrackerRepository {
    private final UserDAO userDAO;

    private final CharacterDAO characterDAO;

    private final InventoryDAO inventoryDAO;

    private final InventoryItemDAO inventoryItemDAO;

    private final SpellBookDAO spellBookDAO;

    private final SpellDAO spellDAO;

    private final MacroDAO macroDAO;

    private static CharacterTrackerRepository repository;


    /**
     *
     * added characterDAO to characterTrackerRepository
     */
    private CharacterTrackerRepository(Application application) {
        CharacterTrackerDatabase db = CharacterTrackerDatabase.getDatabase(application);
        this.userDAO = db.userDao();
        this.characterDAO = db.characterDao();
        this.inventoryDAO = db.inventoryDao();
        this.inventoryItemDAO = db.inventoryItemDao();
        this.spellBookDAO = db.spellBookDao();
        this.spellDAO = db.spellDao();
        this.macroDAO = db.macroDao();
    }


    // Dont know if we will stick with this function. May switch over to liveData Method
    public static CharacterTrackerRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<CharacterTrackerRepository> future = CharacterTrackerDatabase.databaseWriteExecutor.submit(
                new Callable<CharacterTrackerRepository>() {
                    @Override
                    public CharacterTrackerRepository call() throws Exception {
                        CharacterTrackerRepository characterTrackerRepository = new CharacterTrackerRepository(application);
                        return characterTrackerRepository;
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem getting, CharacterTracker Repository thread error.");
        }
        return null;
    }

    public void insertUser(User... user) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });

    }

    public void deleteUser(User user) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(user);
        });
    }

    /**
     *
     * @param DNDCharacter
     * adding insertCharacter function
     * Not sure if I need to change it to be (Character... character),
     * kept getting error when I would try to set it that way
     */
    public void insertCharacter(DNDCharacter DNDCharacter) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            characterDAO.insert(DNDCharacter);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return (userDAO.getUserByUserName(username));


    }

    public LiveData<User> getUserByUserId(int userId) {
        return (userDAO.getUserByUserId(userId));


    }
    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
       // I tried this without the new thread statement and it said i wasn't allowed to do this on main thread
        // I looked this up as a potential solution. It works but I want to know if this is something standard
        new Thread(() -> {
            userDAO.updateUser(user);
        }).start();
    }





    /**
     *Added methods to get characters by charactername and character ID
     */
    public LiveData<DNDCharacter> getCharacterByName(String name) {
        return (characterDAO.getCharacterByName(name));
    }

    public LiveData<DNDCharacter> getCharacterByCharacterId(int characterId) {
        return (characterDAO.getCharacterByCharacterId(characterId));
    }

    public LiveData<List<DNDCharacter>> getAllCharactersByUserId(int userId) {
        return (characterDAO.getAllCharactersByUserId(userId));
    }

    //Methods to get Inventories
    public LiveData<List<Inventory>> getInventoryByCharacterId(int characterId) {
        return (inventoryDAO.getInventoryByCharacterId(characterId));
    }

    public LiveData<List<Inventory>> getInventoriesByItemId(int itemId) {
        return (inventoryDAO.getInventoriesByItemId(itemId));
    }

    public LiveData<List<Inventory>> getAllInventories() {
        return (inventoryDAO.getAllInventories());
    }

    public void insertInventory(Inventory inventory) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            inventoryDAO.insert(inventory);
        });
    }

    public void deleteInventory(Inventory inventory) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            inventoryDAO.delete(inventory);
        });
    }

    public void deleteInventoryByCharacterIdAndItemId(int characterId, int itemId) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            inventoryDAO.deleteInventoryByCharacterIdAndItemId(characterId, itemId);
        });
    }

    public void deleteInventoryByCharacterId(int characterId) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            inventoryDAO.deleteInventoryByCharacterId(characterId);
        });
    }

    public void deleteAllInventories() {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(inventoryDAO::deleteAll);
    }




    //Methods to get InventoryItems
    public LiveData<List<InventoryItem>> getAllItems() {
        return (inventoryItemDAO.getAllItems());
    }

    public LiveData<InventoryItem> getInventoryItemByItemId(int itemId) {
        return (inventoryItemDAO.getInventoryItemById(itemId));
    }

    public LiveData<InventoryItem> getInventoryItemByItemName(String itemName) {
        return (inventoryItemDAO.getInventoryItemByItemName(itemName));
    }

    public LiveData<List<InventoryItem>> getInventoryItemByItemCategory(String itemCategory) {
        return (inventoryItemDAO.getInventoryItemsByItemCategory(itemCategory));
    }

    public LiveData<List<InventoryItem>> getInventoryItemByItemRarity(String itemRarity) {
        return (inventoryItemDAO.getInventoryItemsByItemRarity(itemRarity));
    }

    public void insertInventoryItem(InventoryItem inventoryItem) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            inventoryItemDAO.insert(inventoryItem);
        });
    }

    public void deleteInventoryItem(InventoryItem inventoryItem) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            inventoryItemDAO.delete(inventoryItem);
        });
    }

    public void deleteInventoryItemById(int itemId) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            inventoryItemDAO.deleteItemById(itemId);
        });
    }

    public void deleteAllInventoryItems() {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(inventoryItemDAO::deleteAll);
    }

    public List<InventoryItem> getInventoryItemsByCharacterId(int characterId) {
        LiveData<List<Inventory>> inventory = getInventoryByCharacterId(characterId);
        List<InventoryItem> inventoryItems = new ArrayList<>();
        for (int i = 0; i < inventory.getValue().size(); i++) {
            inventoryItems.add(inventoryItemDAO.getInventoryItemById(inventory.getValue().get(i).getItemId()).getValue());
        }
        return (inventoryItems);
    }



    //Methods to get SpellBooks
    public LiveData<List<SpellBook>> getAllSpellBooks() {
        return (spellBookDAO.getAllSpellBooks());
    }

    public LiveData<SpellBook> getSpellBookByCharacterId(int characterId) {
        return (spellBookDAO.getSpellBookByCharacterId(characterId));
    }

    public void insertSpellBook(SpellBook spellBook) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            spellBookDAO.insert(spellBook);
        });
    }

    public void deleteSpellBook(SpellBook spellBook) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            spellBookDAO.delete(spellBook);
        });
    }

    public void deleteAllSpellBooks() {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(spellBookDAO::deleteAll);
    }


    //Methods to get Spells
    public LiveData<List<Spell>> getAllSpells() {
        return (spellDAO.getAllSpells());
    }

    public LiveData<Spell> getSpellBySpellId(int spellId) {
        return (spellDAO.getSpellBySpellId(spellId));
    }

    public LiveData<Spell> getSpellBySpellName(String spellName) {
        return (spellDAO.getSpellBySpellName(spellName));
    }

    public void insertSpell(Spell spell) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            spellDAO.insert(spell);
        });
    }

    public void deleteSpell(Spell spell) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            spellDAO.delete(spell);
        });
    }

    public void deleteSpellById(int spellId) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            spellDAO.deleteSpellById(spellId);
        });
    }

    public void deleteAllSpells() {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(spellDAO::deleteAll);
    }


    //Methods to get Macros
    public LiveData<List<Macro>> getAllMacros() {
        return (macroDAO.getAllMacros());
    }

    public LiveData<Macro> getMacroByMacroId(int macroId) {
        return (macroDAO.getMacroByMacroId(macroId));
    }

    public LiveData<List<Macro>> getMacroByCharacterId(int characterId) {
        return (macroDAO.getMacrosByCharacterId(characterId));
    }

    public void insertMacro(Macro macro) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            macroDAO.insert(macro);
        });
    }

    public void deleteMacro(Macro macro) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            macroDAO.delete(macro);
        });
    }

    public void deleteMacroById(int macroId) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            macroDAO.deleteMacroById(macroId);
        });
    }

    public void deleteMacroByCharacterId(int characterId) {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(() -> {
            macroDAO.deleteMacroByCharacterId(characterId);
        });
    }

    public void deleteAllMacros() {
        CharacterTrackerDatabase.databaseWriteExecutor.execute(macroDAO::deleteAll);
    }
}
