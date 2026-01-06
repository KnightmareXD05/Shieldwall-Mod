package com.shieldwall;

import com.shieldwall.config.ShieldWallConfig;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.scores.Team;
import net.minecraft.world.phys.Vec3;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ShieldWallMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShieldEvents {

    private static final ResourceLocation CUSTOM_SHIELDS_TAG =
            new ResourceLocation("shieldwall", "custom_shields");

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {

        LivingEntity blocker = event.getEntity();
        if (!isBlocking(blocker)) return;

        double pushStrength = ShieldWallConfig.PUSH_STRENGTH.get();
        double horseStopForce = ShieldWallConfig.HORSE_STOP_FORCE.get();

        for (LivingEntity other : blocker.level().getEntitiesOfClass(
                LivingEntity.class,
                blocker.getBoundingBox().inflate(0.6)
        )) {
            if (other == blocker) continue;

            // Allies don't push each other
            if (blocker.isAlliedTo(other)) continue;

            Vec3 push = other.position().subtract(blocker.position()).normalize();

            // Horse logic
            if (other instanceof Horse horse) {
                Vec3 slowed = horse.getDeltaMovement().scale(0.2);
                horse.setDeltaMovement(slowed);

                if (slowed.lengthSqr() < 0.1) {
                    horse.setDeltaMovement(Vec3.ZERO);
                }

                horse.push(push.x * horseStopForce * 0.2,
                        0,
                        push.z * horseStopForce * 0.2);
                continue;
            }

            // Normal pushback
            other.push(push.x * pushStrength, 0, push.z * pushStrength);

            Vec3 vel = other.getDeltaMovement();
            other.setDeltaMovement(vel.x * 0.1, vel.y, vel.z * 0.1);
        }
    }

    
    private static boolean isBlocking(LivingEntity e) {

        if (!e.isUsingItem()) return false;

        ItemStack stack = e.getUseItem();
        Item item = stack.getItem();

        // 1. Vanilla shields
        if (item instanceof ShieldItem)
            return true;

        // 2. Custom shields via tag
        return item.builtInRegistryHolder()
                .is(ItemTags.create(CUSTOM_SHIELDS_TAG));
    }
}

