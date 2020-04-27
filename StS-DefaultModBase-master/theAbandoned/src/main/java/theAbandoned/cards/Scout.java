package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class Scout extends AbstractDynamicCard {

    /*
    * Innate.
    * Scry 3(5). Draw 1.
    * Exhaust.
     */

    public static final String ID = TheAbandonedMod.makeID(Scout.class.getSimpleName());
    public static final String IMG = makeCardPath("Scout.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 0;
    private static final int MAGIC_NUM = 3;
    private static final int UPGRADE_MAGIC_NUM = 2;

    public Scout() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isInnate = true;
        this.exhaust = true;
        magicNumber = baseMagicNumber = MAGIC_NUM;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ScryAction(magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUM);
            initializeDescription();
        }
    }
}
