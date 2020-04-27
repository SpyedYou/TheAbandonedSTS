package theAbandoned.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAbandoned.powers.FirstCardPower;

public class FirstCardPlayed extends AbstractGameAction {
    private AbstractPlayer p;
    AbstractGameAction extraAction;

    public FirstCardPlayed(AbstractGameAction extraAction) {
        this.p = AbstractDungeon.player;
        this.extraAction = extraAction;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if((!AbstractDungeon.player.hasRelic("theAbandoned:MirrorPendant") && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 1)
                || this.p.hasPower(FirstCardPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(extraAction);
        }
        this.isDone = true;
    }
}
