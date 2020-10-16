package com.bubblefish.forceheroes.keybinds;

import com.bubblefish.forceheroes.ClientForceHeroes;

public class keySpeedForce {
    public static boolean speedForce = false;

    public static void update() {
        if (ClientForceHeroes.keySpeedForce.wasPressed()) {
            speedForce = !speedForce;
        }
    }
}
