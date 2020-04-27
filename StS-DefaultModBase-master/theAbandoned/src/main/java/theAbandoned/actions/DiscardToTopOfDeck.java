package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardToTopOfDeck extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard card;

    public DiscardToTopOfDeck(AbstractCard card) {
        this.p = AbstractDungeon.player;
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (!AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.p.discardPile.moveToDeck(this.card.makeCopy(), false);
        }
        this.isDone = true;
    }
}
