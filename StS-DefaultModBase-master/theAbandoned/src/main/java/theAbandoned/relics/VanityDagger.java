package theAbandoned.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.CustomCardTags;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makeRelicOutlinePath;
import static theAbandoned.TheAbandonedMod.makeRelicPath;

public class VanityDagger extends CustomRelic {

    /*
     * The first Initiative effect triggered each combat is triggered twice.
     */

    // ID, images, text.
    public static final String ID = TheAbandonedMod.makeID("VanityDagger");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public static boolean activated = true;

    public VanityDagger() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        //Checks if the card is an initiative card and this effect hasn't triggered yet
        if (card.tags.contains(CustomCardTags.INITIATIVE) && activated) {
            activated = false;
            this.flash();
            this.grayscale = true;
        }
        //Most of the actions are handled in the FirstCardPlayed action
    }

    public void justEnteredRoom(AbstractRoom room) {
        //Reset the relic to an active state
        activated = true;
        this.grayscale = false;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
