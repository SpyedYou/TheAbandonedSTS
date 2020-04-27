package theAbandoned.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static theAbandoned.TheAbandonedMod.makeID;

public class SpearWallBlock extends DynamicVariable
{
    @Override
    public String key()
    {
        return makeID("SW");
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isBlockModified;
    }

    @Override
    public int value(AbstractCard card)
    {
        if(card.upgraded) {
            return card.baseBlock - 8;
        } else {
            return card.baseBlock - 6;
        }
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if(card.upgraded) {
            return card.baseBlock - 8;
        } else {
            return card.baseBlock - 6;
        }
    }
    
    // If the card has it's damage upgraded, this variable will glow green on the upgrade selection screen as well.
    @Override
    public boolean upgraded(AbstractCard card)
    {               
       return card.upgradedBlock;
    }
}