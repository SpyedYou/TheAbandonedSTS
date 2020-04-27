package theAbandoned.cards;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.actions.AlternateEffect;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.util.CustomCardTags;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class SwiftKneeSweep extends CustomCard {

    /*
     * Gain 6(10) Damage.
     * Alternate - Deal 8(11) Damage.
     */

    public static final String ID = TheAbandonedMod.makeID(SwiftKneeSweep.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SwiftKnee.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    public static final AbstractCard ALTERNATE_CARD = new SwiftKneeStrike();

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;

    public SwiftKneeSweep() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;

        this.tags.add(CardTags.STRIKE);
        this.tags.add(CustomCardTags.ALTERNATE);
        this.tags.add(CustomCardTags.ALTERNATE_VERSION);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction[] actions = new AbstractGameAction[1];
        actions[0] = new GainBlockAction(p, p, block);

        AbstractDungeon.actionManager.addToBottom(new AlternateEffect(this, actions));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_BLOCK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
