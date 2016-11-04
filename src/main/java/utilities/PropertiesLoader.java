package utilities;

import lostandfound.exceptions.PropertyLoaderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadPropertiesFromPackage(String filePath) {
        Properties properties = new Properties();
        try (InputStream in = PropertiesLoader.class.getClassLoader().getResourceAsStream(filePath)){
            properties.load(in);
            return properties;
        }
        catch (IOException e){
            throw new PropertyLoaderException(e.getMessage());
        }
    }

}
