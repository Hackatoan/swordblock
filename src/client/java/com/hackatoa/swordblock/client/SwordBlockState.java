package com.hackatoa.swordblock.client;

public final class SwordBlockState {
    private SwordBlockState() {}
    public static boolean active = false;
    // Master switch, flipped by the (unbound by default) toggle key. Session-only.
    public static boolean enabled = true;
    // Shield hiding on its own, flipped by a separate (unbound by default) key. Session-only.
    public static boolean hideShields = true;

    public static boolean shouldHideShields() {
        return enabled && hideShields;
    }
}
