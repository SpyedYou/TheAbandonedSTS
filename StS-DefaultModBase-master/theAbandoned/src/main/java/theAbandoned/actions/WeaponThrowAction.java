package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class WeaponThrowAction extends AbstractGameAction {
    private AbstractCard card;

    private static final String TEXT = "Exhaust.";

    public WeaponThrowAction(AbstractCard card) {
        this.card = card;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (this.duration == this.startDuration) {
            if (p.hand.size() == 0) {
                applyAction(null);
                this.isDone = true;
                return;
            }

            int handSize;
            if (p.hand.size() <= this.amount) {
                this.amount = p.hand.size();
                handSize = p.hand.size();

                for (int i = 0; i < handSize; ++i) {
                    AbstractCard c = p.hand.getTopCard();
                    p.hand.moveToExhaustPile(c);
                    applyAction(c);
                    this.isDone = true;
                }

                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT, this.amount, true, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.moveToExhaustPile(c);
                applyAction(c);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    private void applyAction(AbstractCard c){
        AbstractPlayer p = AbstractDungeon.player;
        //If there was no card exhausted OR it wasn't an attack, gain block
        if(c == null || c.type != AbstractCard.CardType.ATTACK)
        {
            AbstractDungeon.actionManager.addToBottom(
                    new GainBlockAction(p, p, card.block));
        } else {
            //If it was an attack, deal damage
            AbstractDungeon.actionManager.addToBottom(
                    new AttackDamageRandomEnemyAction(card, AttackEffect.BLUNT_LIGHT));
        }
    }
}
