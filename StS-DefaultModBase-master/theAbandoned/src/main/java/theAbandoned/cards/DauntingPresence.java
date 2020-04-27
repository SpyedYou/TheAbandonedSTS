package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class DauntingPresence extends AbstractDynamicCard {

    /*
     * Gain block equal to ALL enemies battered.
     */

    public static final String ID = TheAbandonedMod.makeID(DauntingPresence.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;

    public DauntingPresence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;

        for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m2.isDeadOrEscaped()) {
                //Check if the monster is battered
                if(m2.hasPower("theAbandoned:BatteredPower"))
                    //Add the battered to the block to be gained
                    count += m2.getPower("theAbandoned:BatteredPower").amount;
            }
        }

        //Showing the block effect but not gaining block makes me sad :(
        if(count > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, count));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
