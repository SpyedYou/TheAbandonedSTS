package theAbandoned.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makePowerPath;

public class RetractingSpikesDownPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheAbandonedMod.makeID("RetractingSpikesDownPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public RetractingSpikesDownPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    //Now at the start of the turn, remove the added thorns.
    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, -(this.amount))));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        AbstractPower currentThorn = this.owner.getPower(ThornsPower.POWER_ID);
        if(currentThorn != null) {
            if (currentThorn.amount == 0) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ThornsPower.POWER_ID));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RetractingSpikesDownPower(owner, source, amount);
    }
}
