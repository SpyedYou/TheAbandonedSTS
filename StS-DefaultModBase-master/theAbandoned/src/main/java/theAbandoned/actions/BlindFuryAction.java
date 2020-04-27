package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.powers.FirstCardPower;

import java.util.Iterator;


public class BlindFuryAction extends AbstractGameAction {
    private AbstractPlayer p;

    public BlindFuryAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        Iterator<AbstractCard> var3 = this.p.hand.group.iterator();

        while(var3.hasNext()) {
            AbstractCard card = AbstractDungeon.player.hand.getTopCard();
            AbstractDungeon.player.hand.group.remove(card);
            AbstractDungeon.actionManager.addToBottom(new NewQueueCardAction(card,
                    AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null,
                            true, AbstractDungeon.cardRandomRng),
                    false, true));
            if (this.target != null) {
                this.addToTop(new UnlimboAction(card));
            }
        }
        this.isDone = true;
    }

    /*
    Code below is for the UnravelingAction - kept here in case it is removed/becomes obsolete in a future update.
    public UnravelingAction() {
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                Iterator var3 = AbstractDungeon.actionManager.cardQueue.iterator();

                while(var3.hasNext()) {
                    CardQueueItem q = (CardQueueItem)var3.next();
                    if (q.card == c) {
                    }
                }

                c.freeToPlayOnce = true;
                switch(c.target) {
                case SELF_AND_ENEMY:
                case ENEMY:
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, AbstractDungeon.getRandomMonster()));
                    break;
                case SELF:
                case ALL:
                case ALL_ENEMY:
                case NONE:
                default:
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, (AbstractMonster)null));
                }
            }
        }

        this.tickDuration();
    }*/
}
