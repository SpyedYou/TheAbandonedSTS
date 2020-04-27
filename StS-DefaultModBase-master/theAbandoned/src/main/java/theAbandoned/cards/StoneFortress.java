package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class StoneFortress extends AbstractDynamicCard {

    /*
     * Gain 1 Intangible. Draw 1 less card next turn. Exhaust.
     */

    public static final String ID = TheAbandonedMod.makeID(StoneFortress.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int DRAW_DOWN = 1;

    public StoneFortress() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        this.addToBot(new ApplyPowerAction(p, p, new DrawReductionPower(AbstractDungeon.player, DRAW_DOWN)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
