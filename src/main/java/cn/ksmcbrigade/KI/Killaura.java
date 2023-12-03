package cn.ksmcbrigade.KI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Mod("ki")
@Mod.EventBusSubscriber
public class Killaura {

    public static Player player;

    public static final KillauraConfig config = new KillauraConfig("config/killaura-config.txt");

    public Killaura() {
        MinecraftForge.EVENT_BUS.register(this);
        ClientRegistry.registerKeyBinding(ModKeys.KILLAURA_CONFIG_KEY);
        ClientRegistry.registerKeyBinding(ModKeys.KILLAURA_KEY);
    }

    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event){
        player = event.player;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void OnKeyPressed(InputEvent.KeyInputEvent event){
        if (ModKeys.KILLAURA_KEY.isDown()) {
            Level world = player.getCommandSenderWorld();
            if(Minecraft.getInstance().hasSingleplayerServer()){
                final Vec3 _center = new Vec3(player.getX(), player.getY(), player.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(config.getReach() / 2d), e -> true).stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                for (Entity entityiterator : _entfound) {
                    if(entityiterator!=player && (entityiterator instanceof LivingEntity)){
                        player.attack(entityiterator);
                        if(config.isSwingHand()){
                            player.swing(player.getUsedItemHand());
                        }
                    }
                }
            }
            else{
                if(config.isEnabledInServer()){
                    final Vec3 _center = new Vec3(player.getX(), player.getY(), player.getZ());
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(config.getReach()), e -> true).stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                    for (Entity entityiterator : _entfound) {
                        if(entityiterator!=player && (entityiterator instanceof LivingEntity)){
                            Minecraft.getInstance().getConnection().getConnection().send(ServerboundInteractPacket.createAttackPacket(entityiterator,true));
                            if(config.isSwingHand()){
                                Minecraft.getInstance().getConnection().getConnection().send(new ServerboundSwingPacket(player.getUsedItemHand()));
                                player.swing(player.getUsedItemHand());
                            }
                        }
                    }
                }
                else{
                    player.sendMessage(Component.nullToEmpty(I18n.get("chat.ki.in_server_warning").replace("{key}",ModKeys.KILLAURA_CONFIG_KEY.getKey().getDisplayName().getString())),player.getUUID());
                }
            }
        }
        else if(ModKeys.KILLAURA_CONFIG_KEY.isDown()){
            Minecraft.getInstance().setScreen(new KillauraConfigGUI());
        }
    }
}
