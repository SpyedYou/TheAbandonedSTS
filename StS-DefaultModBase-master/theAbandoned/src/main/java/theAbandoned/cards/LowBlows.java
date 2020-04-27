package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.BatteredPower;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class LowBlows extends CustomCard {

    /*
     * Deal 3(5) damage twice.
     * Apply 3(4) Battered.
     */

    public static final String ID = TheAbandonedMod.makeID(LowBlows.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("LowBlows.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC_NUM = 3;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public LowBlows() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC_NUM;
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count;
        for(count = 0; count < 2; count++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, p, new BatteredPower(m, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
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
