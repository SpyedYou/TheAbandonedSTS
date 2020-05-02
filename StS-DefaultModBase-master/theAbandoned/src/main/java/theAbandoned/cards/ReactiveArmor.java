package theAbandoned.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAbandoned.TheAbandonedMod;
import theAbandoned.characters.TheAbandonedCharacter;
import theAbandoned.powers.ReactiveArmorPower;

import static theAbandoned.TheAbandonedMod.makeCardPath;

public class ReactiveArmor extends CustomCard {

    /*
     * Gain 4(7) block.
     * At the start of your next turn, deal damage to ALL enemies equal to your block when this card was played.
     */

    public static final String ID = TheAbandonedMod.makeID(ReactiveArmor.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAbandonedCharacter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 3;

    public ReactiveArmor() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int currentBlock;
        int blockDamage;
        AbstractDungeon.actionManager.addToTop(
                new GainBlockAction(p, p, block));
        // Need to add the card's block, as the effect takes place after gaining the block.
        // Also added 1 so the number is rounded up when halved
        currentBlock = p.currentBlock + this.block + 1;
        blockDamage = (currentBlock/2);
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ReactiveArmorPower(p, m, blockDamage), blockDamage));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
