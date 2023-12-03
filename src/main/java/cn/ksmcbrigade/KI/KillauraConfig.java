package cn.ksmcbrigade.KI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KillauraConfig {

    private final String Filepath;
    private float reach;
    private boolean swingHand;

    private boolean enabledInServer;

    public KillauraConfig(String path) {
        this.Filepath = path;
        try{
            if(!(new File(this.Filepath).exists())){
                Files.write(Paths.get(this.Filepath),"3.25;true;false".getBytes());
            }
            String[] config = Files.readString(Paths.get(Filepath)).split(";");
            this.reach = Float.parseFloat(config[0]);
            this.swingHand = Boolean.parseBoolean(config[1]);
            this.enabledInServer = Boolean.parseBoolean(config[2]);
        }
        catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "KillauraConfig{" +
                "Filepath='" + Filepath + '\'' +
                ", reach=" + reach +
                ", swingHand=" + swingHand +
                ", enabledInServer=" + enabledInServer +
                '}';
    }

    public String getFilepath() {
        return Filepath;
    }

    public float getReach() {
        return reach;
    }

    public boolean isSwingHand() {
        return swingHand;
    }

    public boolean isEnabledInServer() {
        return enabledInServer;
    }

    public void setReach(Float reach){
        this.reach = reach;
    }

    public void setSwingHand(boolean swing){
        this.swingHand = swing;
    }

    public void setEnabledInServer(boolean enabledInServer) {
        this.enabledInServer = enabledInServer;
    }

    public void saveToFile(){
        try{
            Files.write(Paths.get(this.Filepath),String.valueOf(getReach()+";"+isSwingHand()+";"+isEnabledInServer()).getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
