package com.hackatoa.swordblock.client;

public final class SwordBlockState {
    private SwordBlockState() {}
    public static boolean active = false;
    // Master switch, flipped by the (unbound by default) toggle key. Session-only.
    public static boolean enabled = true;
}
