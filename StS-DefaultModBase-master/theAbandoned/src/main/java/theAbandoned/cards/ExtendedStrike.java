package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.actions.FirstCardPlayed;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.FirstCardPower;
import theAbandoned.util.CustomCardTags;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class ExtendedStrike extends CustomCard {

    /*
     * Deal 12(15) Damage.
     * Initiative: deal 7(10) additional damage.
     */

    public static final String ID = TheAbandonedMod.makeID(ExtendedStrike.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("ExtendedStrike.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 3;

    public ExtendedStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        this.tags.add(CardTags.STRIKE);
        this.tags.add(CustomCardTags.INITIATIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        AbstractDungeon.actionManager.addToBottom(new FirstCardPlayed(
                new DamageAction(m, new DamageInfo(p, (damage-3), damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT)));
    }

    //Add a glow check if another card hasn't been played yet
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0 && !AbstractDungeon.player.hasRelic("theAbandoned:MirrorPendant")
                || AbstractDungeon.player.hasPower(FirstCardPower.POWER_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
