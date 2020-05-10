package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAbandoned.TheAbandonedMod;

public class ReduceAndDraw extends AbstractGameAction {
    private int amount;
    public static final Logger logger = LogManager.getLogger(TheAbandonedMod.class.getName());

    public ReduceAndDraw(int amount) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        for (int i = 0; i < amount; i++) {
            AbstractCard c = AbstractDungeon.player.drawPile.getNCardFromTop(i);

            // Reduce the two cards on the top of the deck's cost by 1 IF their cost is above 0.
            if(c.cost > 0){
                // Reduce it's cost by 1.
                //c.cost = 0;
                c.updateCost(-1);
                c.isCostModified = true;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(amount));
        this.tickDuration();
        this.isDone = true;
    }
}
