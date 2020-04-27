package theAbandoned.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theAbandoned.TheAbandonedMod;
import theAbandoned.powers.FirstCardPower;

public class DisengagingPotion extends AbstractPotion {

    public static final String POTION_ID = TheAbandonedMod.makeID("DisengagingPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DisengagingPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOLT, PotionColor.FIRE);
        
        potency = getPotency();

        //Deal with higher potency - pluralize Attack to Attacks
        if(potency > 1) {
            description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
        else {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }

        isThrown = false;
        
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new FirstCardPower(target, target, potency)));
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new DisengagingPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }

    public void upgradePotion()
    {
      potency += 2;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}
