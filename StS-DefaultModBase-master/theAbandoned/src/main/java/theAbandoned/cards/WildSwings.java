package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.actions.WildSwingsAction;
import theAbandoned.characters.TheAbandonedCharacter;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class WildSwings extends CustomCard {

    /*
     * Deal 9(12) damage to a random enemy X times.
     */

    public static final String ID = TheAbandonedMod.makeID(WildSwings.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("WildSwings.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = -1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;

    public WildSwings() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new WildSwingsAction(p, this, this.freeToPlayOnce, this.energyOnUse));
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
