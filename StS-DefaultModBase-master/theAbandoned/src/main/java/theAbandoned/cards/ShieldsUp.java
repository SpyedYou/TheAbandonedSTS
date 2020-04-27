package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.FirstCardPower;
import theAbandoned.util.CustomCardTags;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class ShieldsUp extends AbstractDynamicCard {

    /*
     * Gain 12(16) block.
     * Exhaust.
     * Initiative: Does not exhaust.
     */

    public static final String ID = TheAbandonedMod.makeID(ShieldsUp.class.getSimpleName());
    public static final String IMG = makeCardPath("ShieldsUp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public ShieldsUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;

        tags.add(CustomCardTags.INITIATIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        //Use a little different code here, since we want to exhaust if it's NOT the first card. As a result, must use reversed logic compared to the glow check
        if ((AbstractDungeon.actionManager.cardsPlayedThisTurn.size() != 1 || AbstractDungeon.player.hasRelic("theAbandoned:MirrorPendant")) && !p.hasPower(FirstCardPower.POWER_ID)) {
            this.exhaust = true;
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0 && !AbstractDungeon.player.hasRelic("theAbandoned:MirrorPendant")
                || AbstractDungeon.player.hasPower(FirstCardPower.POWER_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
