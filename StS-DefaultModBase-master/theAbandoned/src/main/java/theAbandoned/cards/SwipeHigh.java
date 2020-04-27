package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theAbandoned.TheAbandonedMod;
import theAbandoned.actions.AlternateEffect;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.util.CustomCardTags;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class SwipeHigh extends CustomCard {

    /*
     * Deal 8(10) Damage.
     * Apply 1(2) Weak
     * Alternate - Apply 1(2) Vulnerable.
     */

    public static final String ID = TheAbandonedMod.makeID(SwipeHigh.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    public static final AbstractCard ALTERNATE_CARD = new SwipeLow();

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC_NUM = 1;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public SwipeHigh() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC_NUM;

        this.tags.add(CardTags.STRIKE);
        this.tags.add(CustomCardTags.ALTERNATE);
        this.tags.add(CustomCardTags.ALTERNATE_VERSION);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction[] actions = new AbstractGameAction[2];
        actions[0] = new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        actions[1] = new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE);

        AbstractDungeon.actionManager.addToBottom(new AlternateEffect(this, actions));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC_NUM);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
