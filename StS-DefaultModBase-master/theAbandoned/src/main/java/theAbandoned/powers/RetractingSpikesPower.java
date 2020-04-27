package theAbandoned.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makePowerPath;

public class RetractingSpikesPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;
    private int count;

    public static final String POWER_ID = TheAbandonedMod.makeID("RetractingSpikesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public RetractingSpikesPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

    //Gain thorns when playing a skill
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if(card.type == AbstractCard.CardType.SKILL) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new ThornsPower(owner, amount), amount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new RetractingSpikesDownPower(owner, owner, amount)));
            count++;
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
        return new RetractingSpikesPower(owner, source, amount);
    }
}
