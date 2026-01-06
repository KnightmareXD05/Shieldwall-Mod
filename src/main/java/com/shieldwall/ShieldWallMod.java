package com.shieldwall;

import com.shieldwall.config.ShieldWallConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ShieldWallMod.MODID)
public class ShieldWallMod {

    public static final String MODID = "shieldwall";

    public ShieldWallMod() {
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.COMMON,
                ShieldWallConfig.SPEC,
                "shieldwall.toml"
        );
    }
}
