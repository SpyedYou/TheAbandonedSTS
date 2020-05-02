package theAbandoned.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

import java.util.Iterator;

import static theAbandoned.TheAbandonedMod.makeRelicOutlinePath;
import static theAbandoned.TheAbandonedMod.makeRelicPath;

public class GlowingRock extends CustomRelic {

    /*
     * On pickup, choose a card.
     * Permanently reduce that card's cost by 1.
     */

    // ID, images, text.
    public static final String ID = TheAbandonedMod.makeID("GlowingRock");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("glowing_stone.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("glowing_stone.png"));

    public AbstractCard card = null;
    public AbstractCard secondCard = null;

    private boolean cardSelected = true;

    public GlowingRock() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public void onEquip() {
        AbstractCard c;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
            c = abstractCard;
            if(c.cost > 0){
                tmp.addToRandomSpot(c);
            }
        }
        //Need to ensure there are at least 2 valid cards
        if (tmp.size() > 1) {
            this.isDone = true;
            this.cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(tmp, 2, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }

    public boolean canSpawn() {
        Iterator<AbstractCard> var1 = AbstractDungeon.player.masterDeck.group.iterator();
        int cardsAbove0 = 0;

        // Loop to check if deck has at least 2 cards above 0 cost
        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            c = var1.next();
            if(c.cost > 0){
                cardsAbove0++;
            }
        } while(cardsAbove0 < 2);

        return true;
    }

    public void onUnequip() {
        if (this.card != null && this.secondCard != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            AbstractCard cardInDeck2 = AbstractDungeon.player.masterDeck.getSpecificCard(this.secondCard);

            if (cardInDeck != null) {
                cardInDeck.cost += 1;
                cardInDeck2.cost += 1;
            }
        }
    }

    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.selectedCards.size() > 1) {
            this.cardSelected = true;
            // Get cards
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            this.secondCard = AbstractDungeon.gridSelectScreen.selectedCards.get(1);

            // Change cost
            this.card.updateCost(-1);
            this.secondCard.updateCost(-1);
            // Update the cost permanently, rather than just temporarily like updateCost normally does
            this.card.isCostModified = false;
            this.secondCard.isCostModified = false;

            // Update the deck to show the changed cards' new costs
            AbstractDungeon.player.masterDeck.update();

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[2] + FontHelper.colorString(this.secondCard.name, "y") + this.DESCRIPTIONS[3];
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();

            // Show the 'upgraded' cards
            if (this.isDone && this.secondCard != null && this.card != null) {
                //Get information for the cards to be shown
                AbstractCard showCard1 = this.card.makeStatEquivalentCopy();
                showCard1.updateCost(-1);
                AbstractCard showCard2 = this.secondCard.makeStatEquivalentCopy();
                showCard2.updateCost(-1);

                this.addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(showCard1, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(showCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
