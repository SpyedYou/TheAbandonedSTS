package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.RetractingSpikesPower;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class RetractingSpikes extends AbstractDynamicCard {

    public static final String ID = TheAbandonedMod.makeID(RetractingSpikes.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int UPG_COST = 1;

    public RetractingSpikes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new RetractingSpikesPower(p, m, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPG_COST);
            initializeDescription();
        }
    }
}