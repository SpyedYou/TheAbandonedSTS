package theAbandoned;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomUnlockBundle;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAbandoned.cards.*;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.events.IdentityCrisisEvent;
import theAbandoned.potions.DisengagingPotion;
import theAbandoned.relics.*;
import theAbandoned.util.IDCheckDontTouchPls;
import theAbandoned.util.TextureLoader;
import theAbandoned.variables.DefaultCustomVariable;
import theAbandoned.variables.DefaultSecondMagicNumber;
import theAbandoned.variables.ExtendedDamage;
import theAbandoned.variables.SpearWallBlock;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class TheAbandonedMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostBattleSubscriber,
        SetUnlocksSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(TheAbandonedMod.class.getName());
    private static String modID;

    public static Properties theAbandonedDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Abandoned";
    private static final String AUTHOR = "Overdose";
    private static final String DESCRIPTION = "Adds a new character, focused around battering his opponents before going in the for kill. Includes new cards and relics";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color YELLOW = CardHelper.getColor(210.0f, 210.0f, 20.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theAbandonedResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theAbandonedResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theAbandonedResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theAbandonedResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theAbandonedResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theAbandonedResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theAbandonedResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theAbandonedResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theAbandonedResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_ABANDONED_BUTTON = "theAbandonedResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_ABANDONED_PORTRAIT = "theAbandonedResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_ABANDONED_SHOULDER_1 = "theAbandonedResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_ABANDONED_SHOULDER_2 = "theAbandonedResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_ABANDONED_CORPSE = "theAbandonedResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theAbandonedResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theAbandonedResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theAbandonedResources/images/char/defaultCharacter/skeleton.json";

    //Unlock bundles for the unlock tracker
    private CustomUnlockBundle unlockItems1;
    private CustomUnlockBundle unlockItems2;
    private CustomUnlockBundle unlockItems3;
    private CustomUnlockBundle unlockItems4;
    private CustomUnlockBundle unlockItems5;

    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public TheAbandonedMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);

        setModID("theAbandoned");
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheAbandonedCharacter.Enums.COLOR_YELLOW.toString());
        
        BaseMod.addColor(TheAbandonedCharacter.Enums.COLOR_YELLOW, YELLOW, YELLOW, YELLOW,
                YELLOW, YELLOW, YELLOW, YELLOW,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        

        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theAbandonedDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("The Abandoned Mod", "theAbandonedConfig", theAbandonedDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TheAbandonedMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }
    
    public static String getModID() {
        return modID;
    }

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TheAbandonedMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TheAbandonedMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        TheAbandonedMod defaultmod = new TheAbandonedMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheAbandonedCharacter.Enums.THE_ABANDONED.toString());
        
        BaseMod.addCharacter(new TheAbandonedCharacter("the Default", TheAbandonedCharacter.Enums.THE_ABANDONED),
                THE_ABANDONED_BUTTON, THE_ABANDONED_PORTRAIT, TheAbandonedCharacter.Enums.THE_ABANDONED);
        
        receiveEditPotions();
        logger.info("Added " + TheAbandonedCharacter.Enums.THE_ABANDONED.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Adds The Abandoned character, as well as three shared relics (if unlocked).",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("theAbandonedMod", "theAbandonedConfig", theAbandonedDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        BaseMod.addPotion(DisengagingPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, DisengagingPotion.POTION_ID, TheAbandonedCharacter.Enums.THE_ABANDONED);
        
        logger.info("Done editing potions");
    }
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new AggressiveStance(), TheAbandonedCharacter.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new MirrorPendant(), TheAbandonedCharacter.Enums.COLOR_YELLOW);

        //Character specific relics
        BaseMod.addRelicToCustomPool(new TrainingGauntlets(), TheAbandonedCharacter.Enums.COLOR_YELLOW);

        // This adds a relic to the Shared pool. Any character can find these relics.
        BaseMod.addRelic(new OldBadge(), RelicType.SHARED);

        // Unlockable relics go in the unlock bundles here as well
        /*unlockItems2 = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC,
                relic1.ID, relic2.ID, relic3.ID
        );}*/

        logger.info("Done adding relics!");
    }
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables.
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new ExtendedDamage());
        BaseMod.addDynamicVariable(new SpearWallBlock());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Total cards should be around 75. With each of the sections (starter/common/uncommon/rare), I've
        // including value - a rough average based on the amount of cards of each category other classes have

        /*
        * ~~~Starter Cards 3/3-4~~~
        */
        BaseMod.addCard(new Strike_Abandoned());
        BaseMod.addCard(new Defend_Abandoned());
        BaseMod.addCard(new SkyUppercut());


        /*
        * ~~~Common Cards, 13/20~~~
        */
        // Common Attacks, 8
        BaseMod.addCard(new LowBlows());
        BaseMod.addCard(new SweepAttack());
        BaseMod.addCard(new FastJab());
        BaseMod.addCard(new AxeThrow());
        BaseMod.addCard(new Hack());
        BaseMod.addCard(new Brawl());
        BaseMod.addCard(new Thrust());
        BaseMod.addCard(new BounceStrike());

        // Common Skills, 5
        BaseMod.addCard(new FallBack());
        BaseMod.addCard(new HelmetSwap());
        BaseMod.addCard(new Reposition());
        BaseMod.addCard(new BeatEmUp());
        BaseMod.addCard(new WeaponThrow());

        // No Common Powers
        // F in chat from those who remember when Capacitor was a common.


        /*
        * ~~~Uncommon Cards 20/35~~~
        */
        // Uncommon Attacks, 11
        BaseMod.addCard(new ExtendedStrike());
        BaseMod.addCard(new Backhand());
        BaseMod.addCard(new SlideKick());
        BaseMod.addCard(new Overextend());
        BaseMod.addCard(new DoubleAxe());
        BaseMod.addCard(new WildSwings());
        BaseMod.addCard(new WickedSwing());
        BaseMod.addCard(new GutPunch());
        BaseMod.addCard(new ShoulderBarge());
        BaseMod.addCard(new IronFist());
        BaseMod.addCard(new RepeatingStrikes());

        // Uncommon Skills, 7
        BaseMod.addCard(new Counterweight());
        BaseMod.addCard(new SparePlating());
        BaseMod.addCard(new Scout());
        BaseMod.addCard(new ShieldsUp());
        BaseMod.addCard(new Retarget());
        BaseMod.addCard(new Thwart());
        BaseMod.addCard(new SpearWall());

        // Uncommon Powers, 2
        BaseMod.addCard(new RetractingSpikes());
        BaseMod.addCard(new RecklessPlan());


        /*
        * ~~~Rare Cards 12/16~~~
        */
        // Rare Attacks, 6
        BaseMod.addCard(new TendonSlice());
        BaseMod.addCard(new SkyDrop());
        BaseMod.addCard(new SoulStealer());
        BaseMod.addCard(new ImpendingDoom());
        BaseMod.addCard(new Overkill());
        BaseMod.addCard(new CleanseEvil());

        // Rare Skills, 3
        BaseMod.addCard(new Invigorate());
        BaseMod.addCard(new BlindFury());
        BaseMod.addCard(new DauntingPresence());

        // Rare Powers, 3
        BaseMod.addCard(new EchoBlade());
        BaseMod.addCard(new Overwhelm());
        BaseMod.addCard(new Ferocity());


        // Unlockable cards can be found here as well
        /*unlockItems1 = new CustomUnlockBundle(
                card1.ID, card2.ID, card3.ID
        );*/

        logger.info("Unlocking starting cards.");
        // Unlock the cards, marking them as "seen" in the library
        // before playing your mod.
        UnlockTracker.unlockCard(Strike_Abandoned.ID);
        UnlockTracker.unlockCard(Defend_Abandoned.ID);
        UnlockTracker.unlockCard(SkyUppercut.ID);

        logger.info("Done adding cards!");
    }

    // Here we include cards/relics etc. that are unlocked throughout the unlock tracker in-game.
    @Override
    public void receiveSetUnlocks(){
        BaseMod.addUnlockBundle(unlockItems1, TheAbandonedCharacter.Enums.THE_ABANDONED, 0);
        BaseMod.addUnlockBundle(unlockItems2, TheAbandonedCharacter.Enums.THE_ABANDONED, 1);
        BaseMod.addUnlockBundle(unlockItems3, TheAbandonedCharacter.Enums.THE_ABANDONED, 2);
        BaseMod.addUnlockBundle(unlockItems4, TheAbandonedCharacter.Enums.THE_ABANDONED, 3);
        BaseMod.addUnlockBundle(unlockItems5, TheAbandonedCharacter.Enums.THE_ABANDONED, 4);

        // All cards and relics that need to be unlocked are in the unlock tracker here as well

        // Unlock set 1 - Contains Battered related cards
        // UnlockTracker.addCard(card1.ID);
        // UnlockTracker.addCard(card1.ID);
        // UnlockTracker.addCard(card1.ID);

        // Unlock set 2 - General Mixture of cards
        // UnlockTracker.addCard(card1.ID);
        // UnlockTracker.addCard(card1.ID);
        // UnlockTracker.addCard(card1.ID);

        // Unlock set 3 - Contains relics in the Abandoned pool
        // UnlockTracker.addRelic(relic.ID);
        // UnlockTracker.addRelic(relic.ID);
        // UnlockTracker.addRelic(relic.ID);

        // Unlock set 4 - Contains Initiative related cards
        // UnlockTracker.addCard(card1.ID);
        // UnlockTracker.addCard(card1.ID);
        // UnlockTracker.addCard(card1.ID);

        // Unlock set 5 - Contains relics in the Shared pool
        // UnlockTracker.addRelic(relic.ID);
        // UnlockTracker.addRelic(relic.ID);
        // UnlockTracker.addRelic(relic.ID);
    }
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/TheAbandonedMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/TheAbandonedMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/TheAbandonedMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/TheAbandonedMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/TheAbandonedMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/TheAbandonedMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/TheAbandonedMod-Orb-Strings.json");
        
        logger.info("Done editing strings");
    }
    

    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/TheAbandonedMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    //Add "ModName:" before the ID of any item to avoid conflicts should another mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        //AlternateEffect.resetCards();
    }
}
