package com.shieldwall.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ShieldWallConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue PUSH_STRENGTH;
    public static final ForgeConfigSpec.DoubleValue HORSE_STOP_FORCE;
    public static final ForgeConfigSpec.DoubleValue STAGGER_CHANCE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        PUSH_STRENGTH = builder
                .comment("Strength of shield pushback")
                .defineInRange("push_strength", 0.8D, 0.0D, 5.0D);

        HORSE_STOP_FORCE = builder
                .comment("How strongly the shield wall slows/stops horses ")
                .defineInRange("horse_stop_force", 1.5D, 0.0D, 5.0D);

        STAGGER_CHANCE = builder
                .comment("Chance (0–1) to stagger blocker on strong impacts ")
                .defineInRange("stagger_chance", 0.0D, 0.0D, 1.0D);

        SPEC = builder.build();
    }
}
