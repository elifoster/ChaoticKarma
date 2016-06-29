package santa.karma.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import santa.karma.ChaoticKarma;
import santa.karma.api.KarmaRegistry;
import santa.karma.player.IPlayerData;
import santa.karma.util.EntityUtil;

public class GainingKarmaHandler {
    @SubscribeEvent
    public void onTame(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        ItemStack held = player.getHeldItemMainhand();
        if (held == null) {
            held = player.getHeldItemOffhand();
        }
        // TODO: isBreedingItem and the taming item are different. Right now there is no way to check if an ItemStack is an animal's taming item.
        if (held != null && target instanceof EntityTameable) {
            EntityTameable tameableTarget = (EntityTameable) target;
            if (!tameableTarget.isTamed() && tameableTarget.isBreedingItem(held)) {
                KarmaRegistry.addKarma(player, 1);
            }
        }
    }

    @SubscribeEvent
    public void onMurder(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        EntityLivingBase entity = event.getEntityLiving();
        if (source.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) source.getSourceOfDamage();
            if (entity instanceof EntityPlayer) {
                IPlayerData targetData = EntityUtil.getPlayerData((EntityPlayer) entity);
                if (targetData.getKarma() < ChaoticKarma.DEFAULT_KARMA) {
                    KarmaRegistry.addKarma(player, 2);
                } else {
                    KarmaRegistry.removeKarma(player, 3);
                }
            } else if (entity instanceof EntityMob && !(entity instanceof EntityWither)) {
                if (entity instanceof EntityCreeper) {
                    EntityCreeper creeper = (EntityCreeper) entity;
                    if (creeper.getPowered()) {
                        KarmaRegistry.addKarma(player, 2);
                    }
                }
                KarmaRegistry.addKarma(player, 1);
            } else if (entity instanceof EntityVillager) {
                KarmaRegistry.removeKarma(player, 3);
            } else if (entity instanceof EntityAnimal) {
                KarmaRegistry.removeKarma(player, 1);
            } else if (entity instanceof EntityGolem) {
                KarmaRegistry.removeKarma(player, 2);
            } else if (entity instanceof EntityDragon || entity instanceof EntityWither) {
                KarmaRegistry.addKarma(player, 20);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.isCanceled()) {
            EntityPlayer player = event.getPlayer();
            if (player != null) {
                Block block = player.worldObj.getBlockState(event.getPos()).getBlock();
                if (block instanceof BlockMobSpawner) {
                    KarmaRegistry.addKarma(player, 2);
                } else if (block instanceof BlockSapling) {
                    KarmaRegistry.removeKarma(player, 1);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!event.isCanceled() && player != null) {
            Block block = event.getPlacedBlock().getBlock();
            if (block instanceof BlockSapling) {
                KarmaRegistry.addKarma(player, 1);
            }
        }
    }
}
