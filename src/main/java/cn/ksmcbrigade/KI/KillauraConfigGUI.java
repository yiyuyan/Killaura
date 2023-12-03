package cn.ksmcbrigade.KI;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class KillauraConfigGUI extends Screen {

    private OptionsList optionsList;

    public KillauraConfigGUI() {
        super(new TranslatableComponent("key.ki.killaura_config"));
    }

    @Override
    protected void init() {

        this.optionsList = new OptionsList(
                this.minecraft, this.width, this.height,
                24,
                this.height - 32,
                25
        );

        this.optionsList.addBig(CycleOption.createOnOff("gui.ki.killaura_config_enabled",(p_168280_) -> {
            return Killaura.config.isEnabledInServer();
        }, (p_168282_, p_168283_, p_168284_) -> {
            Killaura.config.setEnabledInServer(p_168284_);
        }));

        this.optionsList.addBig(CycleOption.createOnOff("gui.ki.killaura_config_swing_hand",(p_168280_) -> {
            return Killaura.config.isSwingHand();
        }, (p_168282_, p_168283_, p_168284_) -> {
            Killaura.config.setSwingHand(p_168284_);
        }));

        this.optionsList.addBig(new ProgressOption("gui.ki.killaura_config_reach",1.0F,100.0F,0.25F,(p_168123_) -> {
            return Double.valueOf(Killaura.config.getReach());
        }, (p_168226_, p_168227_) -> {
            Killaura.config.setReach((float)p_168227_.doubleValue());
        }, (p_168274_, p_168275_) -> {
            double d0 = p_168275_.get(p_168274_);
            return (Component)(d0 == 0.0D ? CommonComponents.optionStatus(this.title, false) : new TranslatableComponent("options.generic_value", new TranslatableComponent("gui.ki.killaura_config_reach"), d0));
        }));

        this.addWidget(this.optionsList);

        this.addRenderableWidget(new Button((this.width - 200) / 2,this.height - 25,200,20, CommonComponents.GUI_DONE,(p_96057_) -> {
            this.onClose();
        }));
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(poseStack);
        this.optionsList.render(poseStack,mouseX,mouseY,partialTicks);
        drawCenteredString(poseStack, font,this.title.getString(),this.width / 2, 8, 16777215);
        super.render(poseStack,mouseX,mouseY,partialTicks);
    }

    @Override
    public void onClose() {
        Killaura.config.saveToFile();
        super.onClose();
    }

    protected Component percentValueLabel(double p_91763_) {
        return new TranslatableComponent("options.percent_value",new TranslatableComponent("gui.ki.killaura_config_reach"), (int)(p_91763_ * 100.0D));
    }
}
