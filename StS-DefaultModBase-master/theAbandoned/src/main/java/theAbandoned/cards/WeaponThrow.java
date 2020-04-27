package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.actions.WeaponThrowAction;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class WeaponThrow extends CustomCard {

    /*
     * Exhaust one card.
     * If the card is an attack, deal 9(13) damage to a random enemy.
     * Otherwise, gain 7(9) Block.
     */

    public static final String ID = TheAbandonedMod.makeID(WeaponThrow.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 2;

    public WeaponThrow() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new WeaponThrowAction(this));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
