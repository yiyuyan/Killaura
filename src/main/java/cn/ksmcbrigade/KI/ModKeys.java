package cn.ksmcbrigade.KI;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    public static final String KEY_CATEGORY_KL = "key.category.ki.kl";
    public static final String KEY_KILLAURA =  "key.ki.killaura";
    public static final String KEY_KILLAURA_CONFIG =  "key.ki.killaura_config";

    public static final KeyMapping KILLAURA_KEY = new KeyMapping(KEY_KILLAURA, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R,KEY_CATEGORY_KL);

    public static final KeyMapping KILLAURA_CONFIG_KEY = new KeyMapping(KEY_KILLAURA_CONFIG, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G,KEY_CATEGORY_KL);
}
