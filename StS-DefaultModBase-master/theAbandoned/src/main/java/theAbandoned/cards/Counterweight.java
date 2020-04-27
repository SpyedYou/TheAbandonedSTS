package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class Counterweight extends AbstractDynamicCard {

    /*
     * (Innate)
     * Gain 1 strength for each enemy.
     */

    public static final String ID = TheAbandonedMod.makeID(Counterweight.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 0;

    public Counterweight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m2.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(AbstractDungeon.player, 1), 1)); //GainBlockAction(p, p, block));
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            this.rawDescription = "Innate. NL " + this.rawDescription;
            initializeDescription();
        }
    }
}
