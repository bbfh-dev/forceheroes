package com.bubblefish.forceheroes.keybinds;

import com.bubblefish.forceheroes.ClientForceHeroes;
import com.bubblefish.forceheroes.ForceHeroes;

public class keySpeedForce {
    public static boolean speedForce = false;

    public static void update() {
        if (ForceHeroes.keySpeedForce.wasPressed()) {
            speedForce = !speedForce;
        }
    }
}
