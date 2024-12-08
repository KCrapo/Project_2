package com.example.project_2.database.typeConverters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project_2.database.entities.DNDCharacter;
import com.example.project_2.database.entities.Inventory;
import com.example.project_2.database.entities.InventoryItem;
import com.example.project_2.database.entities.Macro;
import com.example.project_2.database.entities.Spell;
import com.example.project_2.database.entities.SpellBook;
import com.example.project_2.database.entities.User;
import com.example.project_2.viewHolders.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { User.class, DNDCharacter.class, Inventory.class, InventoryItem.class, SpellBook.class, Spell.class, Macro.class}, version = 1, exportSchema = false)
public abstract class CharacterTrackerDatabase extends RoomDatabase {

    public static final String USER_TABLE = "userTable";

    public static final String INVENTORY_TABLE = "InventoryTable";

    public static final String INVENTORY_ITEM_TABLE = "InventoryItemTable";

    public static final String SPELL_BOOK_TABLE = "SpellBookTable";

    public static final String SPELL_TABLE = "SpellTable";

    public static final String MACROS_TABLE = "MacrosTable";

    private static final String DATABASE_NAME = "CharacterTrackerDatabase";

    private static volatile CharacterTrackerDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    // Added for Character.java, added by Samuel
    public static final String CHARACTER_TABLE = "characterTable";





    // Create a service that will supply threads to perform database operations
    // Create all at start up and put them in a pool
    // when you need to do database operations pull a thread from the pool
    // when you are done using it , it goes back in the pool
    // WE have a maximum of 4 threads as shown by NUMBER_OF_THREADS
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CharacterTrackerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {

            // a keyword that basically means:
            // "Make sure that this class that im supplying on has nothing else working on it"
            // All happening in the same thread/place

            synchronized (CharacterTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    CharacterTrackerDatabase.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();


                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database Created");


            // Use this as a way to add default records into database
            // When our database is created we call this, and it will perform said action
            // when database is created
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDao();
//                dao.deleteAll(); //be sure to comment this out once we get this running or we will only have the default values
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testUser1", "testUser1");
                dao.insert(testUser1);
            });

            /**
             * Adding character to test character table in database
             */
            databaseWriteExecutor.execute(() -> {
                CharacterDAO dao = INSTANCE.characterDao();
                dao.deleteAll();
                DNDCharacter character = new DNDCharacter("Tav", "Elf","Barbarian",3, 12, 14, 16, 17, 10, 8, 1);
                dao.insert(character);
            });

            databaseWriteExecutor.execute(() -> {
                InventoryDAO dao = INSTANCE.inventoryDao();
                dao.deleteAll();
                dao.insert(new Inventory(1, 1));
            });

            databaseWriteExecutor.execute(() -> {
                InventoryItemDAO dao = INSTANCE.inventoryItemDao();
                dao.deleteAll();
                String itemDescription = "This bag has an interior space considerably larger than its outside " +
                        "dimensions—roughly 2 feet square and 4 feet deep on the inside. The bag can hold up to 500 " +
                        "pounds, not exceeding a volume of 64 cubic feet. The bag weighs 5 pounds, regardless of its " +
                        "contents. Retrieving an item from the bag requires a Utilize action.\n" + "\n" + "If the bag is " +
                        "overloaded, pierced, or torn, it is destroyed, and its contents are scattered in the Astral Plane. " +
                        "If the bag is turned inside out, its contents spill forth unharmed, but the bag must be put right " +
                        "before it can be used again. The bag holds enough air for 10 minutes of breathing, divided by the " +
                        "number of breathing creatures inside.\n" + "\n" + "Placing a Bag of Holding inside an " +
                        "extradimensional space created by a Heward's Handy Haversack, Portable Hole, or similar item " +
                        "instantly destroys both items and opens a gate to the Astral Plane. The gate originates where the " +
                        "one item was placed inside the other. Any creature within a 10-foot-radius Sphere centered on the " +
                        "gate is sucked through it to a random location on the Astral Plane. The gate then closes. The " +
                        "gate is one-way and can't be reopened.";
                dao.insert(new InventoryItem("Bag of Holding", itemDescription, 5, 500, "Wonderous Item", 1, "Uncommon"));
            });

            databaseWriteExecutor.execute(() -> {
                SpellBookDAO dao = INSTANCE.spellBookDao();
                dao.deleteAll();
                dao.insert(new SpellBook(1,1));
            });

            databaseWriteExecutor.execute(() -> {
                SpellDAO dao = INSTANCE.spellDao();
                dao.deleteAll();
                dao.insert((new Spell("Wish")));
            });

            databaseWriteExecutor.execute(() -> {
                MacroDAO dao = INSTANCE.macroDao();
                dao.deleteAll();
                dao.insert(new Macro(1, "6s4d6dlr1")); // 6 sets of 4 d6 dice rolls, drop the lowest roll, reroll 1s
            });

        }
    };



    public abstract UserDAO userDao();

// abstract characterDao
    public abstract CharacterDAO characterDao();

    public abstract InventoryDAO inventoryDao();

    public abstract InventoryItemDAO inventoryItemDao();

    public abstract SpellBookDAO spellBookDao();

    public abstract SpellDAO spellDao();

    public abstract MacroDAO macroDao();
}
