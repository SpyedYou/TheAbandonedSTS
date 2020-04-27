package theAbandoned.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

public class OverwhelmPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheAbandonedMod.makeID("OverwhelmPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theAbandonedResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theAbandonedResources/images/powers/placeholder_power32.png");

    public OverwhelmPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    //Checks to make sure it's Battered, the owner of the power is applying it, it's not on themselves and they don't have an artifact to block it.
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals("theAbandoned:BatteredPower") && source == this.owner && target != this.owner && !target.hasPower("Artifact")) {
            this.flash();
            //Make sure they have this power active
            if(source.hasPower(POWER_ID)) {
                this.addToBot(new ApplyPowerAction(source, source, new StrengthPower(source, amount), 1));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new OverwhelmPower(owner, source, amount);
    }
}
