package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAbandoned.cards.Hack;

import java.util.Iterator;

public class AndBackAction extends AbstractGameAction {
    private AbstractCard card;

    public AndBackAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public void update() {
        AbstractCard var = this.card;
        var.baseDamage += this.amount;
        this.card.applyPowers();

        Iterator<AbstractCard> var1 = AbstractDungeon.player.discardPile.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = var1.next();
            if (c instanceof Hack) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while(var1.hasNext()) {
            c = var1.next();
            if (c instanceof Hack) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            c = var1.next();
            if (c instanceof Hack) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        this.isDone = true;
    }
}
