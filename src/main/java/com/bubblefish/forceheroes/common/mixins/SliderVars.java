package com.bubblefish.forceheroes.common.mixins;

public class SliderVars {
    public static double varA = 1;
    public static boolean aEdited = false;
    public static double varB = 1;
    public static boolean bEdited = false;

    public static void setA(double v) {
        if (varA != v) {
            aEdited = true;
        }
        varA = v;
    }

    public static void setB(double v) {
        if (varB != v) {
            bEdited = true;
        }
        varB = v;
    }
}
