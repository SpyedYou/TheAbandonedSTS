package theAbandoned.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makePowerPath;

//Unit takes 10% increased damage per stack. Half of the stacks are lost at the end of the turn.

public class BatteredPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheAbandonedMod.makeID("BatteredPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Battered_Power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Battered_Power32.png"));

    public BatteredPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    //Want to increase damage by 10% per stack.
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * (1.0F + (float)this.amount * 0.1F) : damage;
    }

    // At the end of the turn, remove half the stacks.
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        //Relic check - Battered would only lose 33% of stacks instead
        int batteredLost;
        if (this.owner != null && !this.owner.isPlayer && AbstractDungeon.player.hasRelic("theAbandoned:TrainingGauntlets")){
            if (this.amount > 1) {
                flash();
                //Determine how much to lose, rounded down. Always at least 1.
                batteredLost = (this.amount/3);
                if (batteredLost == 0){
                    batteredLost = 1;
                }
                AbstractDungeon.actionManager.addToBottom(
                        //Lose 33%, not half
                        new ReducePowerAction(owner, owner, BatteredPower.POWER_ID, (batteredLost)));
            } else if (this.amount == 1) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        } else if(this.owner != null) {
            if (this.amount > 1) {
                flash();
                AbstractDungeon.actionManager.addToBottom(
                        new ReducePowerAction(owner, owner, BatteredPower.POWER_ID, (this.amount / 2)));
            } else if (this.amount == 1) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount != 0) {
            if (AbstractDungeon.player.hasRelic("theAbandoned:TrainingGauntlets")) {
                description = DESCRIPTIONS[0] + this.amount*10 + DESCRIPTIONS[2];
            } else {
                description = DESCRIPTIONS[0] + this.amount*10 + DESCRIPTIONS[1];
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BatteredPower(owner, source, amount);
    }
}
