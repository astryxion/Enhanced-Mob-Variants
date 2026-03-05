package com.astryxion.mobvariants.variant;

public record MoonPhaseModifier(float minimumMoonSize) implements VariantModifier {
    public boolean canSpawn(float moonSize) {
        return moonSize > minimumMoonSize;
    }
}
