package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class Invigorate extends AbstractDynamicCard {

    /*
     * Ethereal.
     * Remove ALL debuffs.
     * Exhaust.
     */

    public static final String ID = TheAbandonedMod.makeID(Invigorate.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int UPG_COST = 0;

    public Invigorate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(p));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeBaseCost(UPG_COST);
            upgradeName();
            initializeDescription();
        }
    }
}
