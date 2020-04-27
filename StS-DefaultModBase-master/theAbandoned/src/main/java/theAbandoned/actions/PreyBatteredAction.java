package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAbandoned.powers.BatteredPower;

public class PreyBatteredAction extends AbstractGameAction {

    private AbstractPower power;

    public PreyBatteredAction(AbstractPower power){
        this.power = power;
    }
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
                this.addToTop(
                        new ApplyPowerAction(this.target, AbstractDungeon.player, new BatteredPower(this.target, AbstractDungeon.player, this.power.amount), this.power.amount, true));
        }

        this.isDone = true;
    }
}
