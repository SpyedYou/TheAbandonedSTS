package theAbandoned.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makeRelicOutlinePath;
import static theAbandoned.TheAbandonedMod.makeRelicPath;

public class AggressiveStance extends CustomRelic {

    /*
     * The first non X attack played in combat with a cost of 2 or more [E] refunds [E] .
     */

    public static final String ID = TheAbandonedMod.makeID("AggressiveStance");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private boolean activated = true;

    public AggressiveStance() {super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);}

    public void atPreBattle() {
        activated = true;
        this.grayscale = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && (card.costForTurn >= 2 && !card.freeToPlayOnce && card.cost != -1 && card.energyOnUse >= 2) && this.activated) {
            this.activated = false;
            this.flash();
            this.grayscale = true;

            this.addToBot(new GainEnergyAction(1));

            this.pulse = false;
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
