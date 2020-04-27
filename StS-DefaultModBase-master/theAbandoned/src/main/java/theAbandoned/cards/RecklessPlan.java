package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.RecklessPlanPower;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class RecklessPlan extends AbstractDynamicCard {

    public static final String ID = TheAbandonedMod.makeID(RecklessPlan.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public RecklessPlan() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new RecklessPlanPower(p, m, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            initializeDescription();
        }
    }
}