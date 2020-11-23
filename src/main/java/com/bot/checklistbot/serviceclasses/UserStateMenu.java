package com.bot.checklistbot.serviceclasses;

import com.bot.checklistbot.model.userstate.UserState;

/**
 * Варианты меню, в которых может находиться пользователь
 */
public enum  UserStateMenu {
    /**
     * Стартовое меню
     */
    START() {
        @Override
        public boolean isMenu(UserState state) {
            return state.getChecklistId() == null;
        }
    },
    /**
     * Меню показа списка
     */
    VIEW_CHECKLIST() {
        @Override
        public boolean isMenu(UserState state) {
            return state.getChecklistId() != null && state.getChecklistItemId() == null;
        }
    },
    /**
     * Меню показа элемента списка
     */
    VIEW_CHECKLIST_ITEM() {
        @Override
        public boolean isMenu(UserState state) {
            return state.getChecklistItemId() != null;
        }
    };

    /**
     * Проверяет находится ли пользователь в текущем меню
     * @param state состояние пользователя
     * @return true - если находится, false - иначе
     */
    public abstract boolean isMenu(UserState state);
}
