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
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.BatteredPower;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class SkyUppercut extends CustomCard {

    /*
     * Deal 9(12) damage.
     * Apply 4(6) Battered.
     */

    public static final String ID = TheAbandonedMod.makeID(SkyUppercut.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SkyUppercut.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 2;
    private static final int DAMAGE = 9;
    private static final int MAGIC_NUM = 4;
    private static final int UPG_MAGIC_NUM = 2;
    private static final int UPGRADE_PLUS_DMG = 3;

    public SkyUppercut() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC_NUM;
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, p, new BatteredPower(m, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPG_MAGIC_NUM);
            initializeDescription();
        }
    }
}
