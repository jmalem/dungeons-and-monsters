package GameUI.controller;

import Game.GameLevel;

public interface Controller {
    void setViewProps();
    void setButtonHandlers(GameLevel game);
    void populateViews();
}
