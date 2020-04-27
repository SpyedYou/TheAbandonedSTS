package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class ShoulderBarge extends CustomCard {

    /*
     * Deal 7(9) damage.
     * Gain 2(3) Temporary Dexterity.
     */

    public static final String ID = TheAbandonedMod.makeID(ShoulderBarge.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC_NUM = 2;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public ShoulderBarge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC_NUM;
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber, true));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber, true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC_NUM);
            initializeDescription();
        }
    }
}
