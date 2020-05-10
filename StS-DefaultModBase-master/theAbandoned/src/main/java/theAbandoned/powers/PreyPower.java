package theAbandoned.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.actions.PreyBatteredAction;
import theAbandoned.util.TextureLoader;

public class PreyPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheAbandonedMod.makeID("PreyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theAbandonedResources/images/powers/Prey_Power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theAbandonedResources/images/powers/Prey_Power32.png");

    public PreyPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // When the enemy takes damage, apply battered to a random enemy.
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // Shouldn't trigger on non-attack damage
        if(info.type != DamageInfo.DamageType.NORMAL) {
            AbstractDungeon.actionManager.addToBottom(new PreyBatteredAction(this));
        }
        return damageAmount;
    }

    public void atEndOfTurn(final boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(owner, owner, PreyPower.POWER_ID, this.amount));
    }

        @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PreyPower(owner, source, amount);
    }
}
