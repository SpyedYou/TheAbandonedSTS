package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.BatteredPower;

import java.util.Iterator;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class Brawl extends CustomCard {

    /*
     * Deal 5(8) damage to ALL enemies.
     * Apply 2(3) Battered to ALL enemies.
     */

    public static final String ID = TheAbandonedMod.makeID(Brawl.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Brawl.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUM = 2;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public Brawl() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC_NUM;
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator<AbstractMonster> var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        while(var3.hasNext()) {
            AbstractMonster mo = var3.next();
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(mo, p, new BatteredPower(mo, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC_NUM);
            initializeDescription();
        }
    }
}
