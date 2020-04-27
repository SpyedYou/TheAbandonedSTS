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
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.util.CustomCardTags;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class SkyDrop extends CustomCard {

    /*
     * Innate
     * Deals 18(24) Damage.
     * Apply 2(3) Vulnerable.
     * Exhaust.
     */

    public static final String ID = TheAbandonedMod.makeID(SkyDrop.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 2;
    private static final int DAMAGE = 18;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_DMG = 6;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public SkyDrop() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
        baseDamage = DAMAGE;
        this.isInnate = true;
        this.exhaust = true;
        tags.add(CustomCardTags.INITIATIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
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
