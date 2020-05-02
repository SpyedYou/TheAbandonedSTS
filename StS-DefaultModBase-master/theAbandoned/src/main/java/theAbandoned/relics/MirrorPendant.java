package theAbandoned.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAbandoned.TheAbandonedMod;
import theAbandoned.util.TextureLoader;

import static theAbandoned.TheAbandonedMod.makeRelicOutlinePath;
import static theAbandoned.TheAbandonedMod.makeRelicPath;

public class MirrorPendant extends CustomRelic {

    /*
     * Gain [E] at the start of your turn. Initiative effects no longer trigger on the first card played.
     */

    public static final String ID = TheAbandonedMod.makeID("MirrorPendant");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MirrorPendant.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MirrorPendant.png"));

    public MirrorPendant() {super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);}

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
