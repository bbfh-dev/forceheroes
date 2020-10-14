package com.bubblefish.forceheroes.keybinds;

import com.bubblefish.forceheroes.ForceHeroes;

public class keySlowMotion {
    public static boolean slowMotion = false;

    public static void update() {
        if (ForceHeroes.keySlowMotion.wasPressed()) {
            slowMotion = !slowMotion;
        }
    }
}
