package core.factories;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFactory {

    private static Properties properties;

    public static Properties getInstance(String fileName) {
        if (properties == null) {
            properties = new Properties();
            InputStream file;
            try {
            	file = new FileInputStream(fileName);
                properties.load(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties;
    }


}
