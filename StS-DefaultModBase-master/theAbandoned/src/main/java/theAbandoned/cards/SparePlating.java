package theAbandoned.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import java.util.Iterator;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class SparePlating extends AbstractDynamicCard {

    /*
     * If you have no skills in your hand, gain 12(16) block.
     */

    public static final String ID = TheAbandonedMod.makeID(SparePlating.class.getSimpleName());
    public static final String IMG = makeCardPath("SparePlating.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int BLOCK = 13;
    private static final int UPGRADE_PLUS_BLOCK = 5;

    public SparePlating() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int skillCount = hasSkills();
        if(skillCount <= 1) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (hasSkills()  <= 1) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    private int hasSkills(){
        Iterator<AbstractCard> var1 = AbstractDungeon.player.hand.group.iterator();
        int count = 0;

        AbstractCard c;
        do {
            if(var1.hasNext()) {
                c = var1.next();
                if ((c.type == CardType.SKILL)) {
                    count++;
                }
            }
        } while(var1.hasNext());

        return count;
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
