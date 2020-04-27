package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SoulStealerAction extends AbstractGameAction {
    private AbstractCard randomCard;
    private int increaseAmount;
    private DamageInfo info;

    public SoulStealerAction(AbstractCreature target, DamageInfo info, int incAmount) {
        this.info = info;
        this.setValues(target, info);
        this.increaseAmount = incAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                Iterator<AbstractCard> var1 = AbstractDungeon.player.masterDeck.group.iterator();
                List<AbstractCard> attackCards = new ArrayList<>();

                AbstractCard c;

                //Get a list of all attack cards
                while(var1.hasNext()){
                    c = var1.next();
                    if(c.type == AbstractCard.CardType.ATTACK) {
                        attackCards.add(c);
                    }
                }

                //Pick a random attack card
                randomCard = attackCards.get((int)(Math.random()*attackCards.size()));

                randomCard.applyPowers();
                randomCard.baseDamage += this.increaseAmount;
                randomCard.isDamageModified = false;
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();

        //Show the card that was upgraded
        if (this.isDone && randomCard != null) {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(randomCard.makeStatEquivalentCopy()));
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }
}
