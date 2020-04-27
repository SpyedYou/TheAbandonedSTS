package theAbandoned.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.CustomCardTags;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makePowerPath;

public class FirstCardPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheAbandonedMod.makeID("FirstCardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("FirstCard_Power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("FirstCard_Power32.png"));

    public FirstCardPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

    //Decrease this buff amount
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
        //Only decrement if it's a 'first_play' card
        if(card.hasTag(CustomCardTags.INITIATIVE)) {
            this.flash();
            --this.amount;
            if(this.amount == 0){
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        if(amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else{
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FirstCardPower(owner, source, amount);
    }
}
