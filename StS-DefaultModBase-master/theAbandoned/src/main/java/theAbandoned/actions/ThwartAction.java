package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.powers.BatteredPower;

import java.util.Iterator;

public class ThwartAction extends AbstractGameAction {
    private AbstractMonster m;

    public ThwartAction(int amount, AbstractMonster m) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.m = m;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            this.addToTop(new ApplyPowerAction(this.m, AbstractDungeon.player, new BatteredPower(this.m, AbstractDungeon.player, amount), this.amount));
        }

        this.isDone = true;
    }
}