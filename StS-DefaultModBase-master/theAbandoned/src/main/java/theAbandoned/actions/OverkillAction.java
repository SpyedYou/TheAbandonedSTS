package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class OverkillAction extends AbstractGameAction {
    private DamageInfo info;

    public OverkillAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        int healAmount = 0;
        boolean killed = false;

        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            if (info.output > this.target.currentHealth){
                //Include health AND block.
                healAmount = info.output - (this.target.currentHealth + this.target.currentBlock);
            }
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                killed = true;
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();

        //Heal the amount, if the target was killed
        if(healAmount > 0 && killed){
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, healAmount));
        }
    }
}
