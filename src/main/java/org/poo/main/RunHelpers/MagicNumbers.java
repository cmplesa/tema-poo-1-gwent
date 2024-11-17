package org.poo.main.RunHelpers;

public enum MagicNumbers {
    MAX_BACK_ROW_SIZE(5),
    MAX_FRONT_ROW_SIZE(5),
    MANA_COST(2),
    PLAYER_ONE_TURN(1),
    PLAYER_TWO_TURN(2),
    THREE(3),
    TEN(10);

    private final int value;

    MagicNumbers(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
