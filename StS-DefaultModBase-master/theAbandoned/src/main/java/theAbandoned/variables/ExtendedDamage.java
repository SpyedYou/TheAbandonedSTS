package theAbandoned.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static theAbandoned.TheAbandonedMod.makeID;

public class ExtendedDamage extends DynamicVariable
{
    @Override
    public String key()
    {
        return makeID("ED");
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isDamageModified;
    }

    @Override
    public int value(AbstractCard card)
    {
        return card.damage-3;
    }
    
    // The baseValue the variable should display.
    // just like baseBlock or baseDamage, this is what the variable should reset to by default. (the base value before any modifications)
    @Override
    public int baseValue(AbstractCard card)
    {   
        return card.baseDamage-3;
    }
    
    // If the card has it's damage upgraded, this variable will glow green on the upgrade selection screen as well.
    @Override
    public boolean upgraded(AbstractCard card)
    {               
       return card.upgradedDamage;
    }
}