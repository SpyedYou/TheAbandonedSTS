package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class RepeatedStrike extends CustomCard {

    /*
     * Deal 6 Damage.
     * Add a Repeated Strike to your hand, with 3(5) increased damage
     */

    public static final String ID = TheAbandonedMod.makeID(RepeatedStrike.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("RepeatedStrike.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 2;

    public RepeatedStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.isEthereal = true;
        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Need the card here so we can increase it's damage and damage increase amount.
        AbstractCard newCard = this.makeStatEquivalentCopy();
        newCard.baseDamage += this.magicNumber;
        newCard.magicNumber += this.magicNumber;

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(newCard, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC_NUMBER);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
