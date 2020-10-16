package com.bubblefish.forceheroes.keybinds;

import com.bubblefish.forceheroes.ClientForceHeroes;

public class keySlowMotion {
    public static boolean slowMotion = false;

    public static void update() {
        if (ClientForceHeroes.keySlowMotion.wasPressed()) {
            slowMotion = !slowMotion;
        }
    }
}
