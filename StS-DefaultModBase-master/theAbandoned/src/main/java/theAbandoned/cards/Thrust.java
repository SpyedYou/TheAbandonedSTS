package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class Thrust extends CustomCard {

    /*
     * Deal 22(26) damage.
     * When drawn, reduce this card's cost by 1 this combat.
     */

    public static final String ID = TheAbandonedMod.makeID(Thrust.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 5;
    private static final int UPG_COST = 4;
    private static final int DAMAGE = 22;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int MAGIC_NUMBER = 1;

    public Thrust() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void triggerWhenDrawn() {
        this.addToBot(new ReduceCostAction(this.uuid, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPG_COST);
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
