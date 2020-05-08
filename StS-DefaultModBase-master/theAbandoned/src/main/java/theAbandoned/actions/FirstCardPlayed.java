package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAbandoned.powers.FirstCardPower;
import theAbandoned.relics.VanityDagger;
import theAbandoned.util.CustomCardTags;

public class FirstCardPlayed extends AbstractGameAction {
    AbstractGameAction extraAction;
    AbstractCard card;

    public FirstCardPlayed(AbstractGameAction extraAction, AbstractCard card) {
        this.extraAction = extraAction;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.card = card;
    }

    public void update() {
        if(willTriggerInitiative(this.card)){
            AbstractDungeon.actionManager.addToTop(extraAction);
        }
        if(AbstractDungeon.player.hasRelic("theAbandoned:VanityDagger")) {
            if(VanityDagger.activated) {
                AbstractDungeon.actionManager.addToTop(extraAction);
            }
        }
        this.isDone = true;
    }

    public static boolean willTriggerInitiative(AbstractCard card){
        return ((!AbstractDungeon.player.hasRelic("theAbandoned:MirrorPendant") && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 1)
                || AbstractDungeon.player.hasPower(FirstCardPower.POWER_ID) && card.hasTag(CustomCardTags.INITIATIVE));
    }
}
