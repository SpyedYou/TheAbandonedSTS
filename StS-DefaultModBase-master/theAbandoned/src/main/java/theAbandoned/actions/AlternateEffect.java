package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAbandoned.TheAbandonedMod;
import theAbandoned.cards.SwiftKneeStrike;
import theAbandoned.cards.SwiftKneeSweep;
import theAbandoned.cards.SwipeHigh;
import theAbandoned.cards.SwipeLow;
import theAbandoned.util.CustomCardTags;

import java.util.Arrays;
import java.util.Iterator;

public class AlternateEffect extends AbstractGameAction {

    public static boolean alternateEffect = false;
    public static final Logger logger = LogManager.getLogger(TheAbandonedMod.class.getName());
    private AbstractCard card;
    private AbstractGameAction[] actions;

    //Sort of a hack to alternate effects - Uses two version of the card, one for each effect
    public AlternateEffect(AbstractCard card, AbstractGameAction[] actions){
        this.card = card;
        this.actions = actions;
    }

    public void update() {
        //Start with the actions of the card
        Iterator<AbstractGameAction> var = Arrays.stream(actions).iterator();
        while(var.hasNext()){
            AbstractGameAction action = var.next();
            AbstractDungeon.actionManager.addToBottom(action);
        }

        //Now, update the card played.
        replaceCard(this.card, getAlternateCard(this.card));

        //Finally, loop through the other piles, updating cards accordingly
        Iterator<AbstractCard> var1 = AbstractDungeon.player.discardPile.group.iterator();

        logger.info("Request for alternate card in Discard Pile");

        while(var1.hasNext()) {
            AbstractCard card = var1.next();
            logger.info("Received card : " + card.name);
            if(card.hasTag(CustomCardTags.ALTERNATE)){
                logger.info("Replacing " + card.name + " with " + getAlternateCard(card).name);
                replaceCard(card, getAlternateCard(card));
                card.update();
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        logger.info("Request for alternate card in Draw Pile");

        while(var1.hasNext()) {
            AbstractCard card = var1.next();
            logger.info("Received card : " + card.name);
            if(card.hasTag(CustomCardTags.ALTERNATE)){
                logger.info("Replacing " + card.name + " with " + getAlternateCard(card).name);
                replaceCard(card, getAlternateCard(card));
                card.update();
            }
        }

        var1 = AbstractDungeon.player.hand.group.iterator();

        logger.info("Request for alternate card in Hand");

        while(var1.hasNext()) {
            AbstractCard card = var1.next();
            logger.info("Received card : " + card.name);
            if(card.hasTag(CustomCardTags.ALTERNATE)){
                logger.info("Replacing " + card.name + " with " + getAlternateCard(card).name);
                replaceCard(card, getAlternateCard(card));
                card.update();
            }
        }

        alternateEffect = !alternateEffect;

        this.isDone = true;
    }

    public static AbstractCard getAlternateCard(AbstractCard card) {
        if(card.cardID.equals(SwiftKneeStrike.ID)){
            return SwiftKneeStrike.ALTERNATE_CARD;
        }
        if(card.cardID.equals(SwiftKneeSweep.ID)){
            return SwiftKneeSweep.ALTERNATE_CARD;
        }
        if(card.cardID.equals(SwipeLow.ID)){
            return SwipeLow.ALTERNATE_CARD;
        }
        if(card.cardID.equals(SwipeHigh.ID)){
            return SwipeHigh.ALTERNATE_CARD;
        }
        return card;
    }

    public static void resetCards() {
        //We only need to reset if the cards are on their alternative versions

        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.hasTag(CustomCardTags.ALTERNATE_VERSION)) {
                replaceCard(card, getAlternateCard(card));
                card.update();
            }
        }
    }

    /*Replace all elements of one card with the element of the other
     * Note that Damage, Block and Magic Number are unchanged due to both cards having identical effects.
     * This also helps with cards that modify values of one card, since they would lose any changes afterwards
     */
    public static void replaceCard(AbstractCard originalCard, AbstractCard replacementCard) {
        originalCard.target = replacementCard.target;
        originalCard.cost = replacementCard.cost;
        originalCard.description = replacementCard.description;
        originalCard.rawDescription = replacementCard.rawDescription;
        originalCard.portrait = replacementCard.portrait;
        originalCard.cardID = replacementCard.cardID;
        originalCard.angle = replacementCard.angle;
        originalCard.assetUrl = replacementCard.assetUrl;
        originalCard.costForTurn = replacementCard.costForTurn;
        originalCard.name = replacementCard.name;
        originalCard.type = replacementCard.type;
        originalCard.uuid = replacementCard.uuid;
    }
}
