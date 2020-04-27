package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.UnravelingAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.FirstCardPower;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class BlindFury extends AbstractDynamicCard {

    /*
     * Trigger initiative on all cards played this turn. NL Play your entire hand. NL Exhaust.
     */

    public static final String ID = TheAbandonedMod.makeID(BlindFury.class.getSimpleName());
    public static final String IMG = makeCardPath("BlindFury.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 4;
    private static final int UPG_COST = 3;

    public BlindFury() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FirstCardPower(p, p, 99)));
        AbstractDungeon.actionManager.addToBottom(new UnravelingAction());
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPG_COST);
            initializeDescription();
        }
    }
}
