package theAbandoned.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makeRelicOutlinePath;
import static theAbandoned.TheAbandonedMod.makeRelicPath;

public class OldBadge extends CustomRelic {

    /*
     * Non-upgraded cards deal 2 additional damage.
     */

    // ID, images, text.
    public static final String ID = TheAbandonedMod.makeID("OldBadge");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public OldBadge() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public float atDamageModify(float damage, AbstractCard c) {
        return c.timesUpgraded == 0 ? damage + 2.0F : damage;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
