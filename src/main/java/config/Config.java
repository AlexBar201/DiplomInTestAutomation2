package config;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final Properties properties;

    public Config(String filePath){
        properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(filePath)){
            properties.load(inputStream);
        } catch (IIOException | FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBaseUri(){
        return properties.getProperty("base.uri");
    }

    public int getTimeOut(){
        return Integer.parseInt(properties.getProperty("timeout", "30"));
    }

}
