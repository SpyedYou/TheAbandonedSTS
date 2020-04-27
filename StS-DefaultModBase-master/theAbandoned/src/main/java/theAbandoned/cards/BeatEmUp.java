package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.BatteredPower;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class BeatEmUp extends CustomCard {

    /*
     * Apply 5(8) Battered.
     * Draw 2 cards.
     */

    public static final String ID = TheAbandonedMod.makeID(BeatEmUp.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_MAGIC_NUMBER = 2;
    private static final int DRAW = 2;

    public BeatEmUp() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
        draw = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, p, new BatteredPower(m, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(
                        new DrawCardAction(p, this.draw));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
