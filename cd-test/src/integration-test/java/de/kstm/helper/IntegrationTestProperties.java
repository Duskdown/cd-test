package de.kstm.helper;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class IntegrationTestProperties {
    private static Properties INTEGRATION_PROPERTIES;
    private static String PROPERTIES_FILE_NAME = "test.integration.yml";
    
    public static String getProperty(String key) throws IOException {
        return getProperty(key, null);
    }
    
    public static String getProperty(String key, String defaultValue) throws IOException {
        if(INTEGRATION_PROPERTIES == null) {
            INTEGRATION_PROPERTIES = new Properties();
            INTEGRATION_PROPERTIES.load(new FileReader(new ClassPathResource(PROPERTIES_FILE_NAME).getFile()));
        }
        
        String property = INTEGRATION_PROPERTIES.getProperty(key);
        if(property == null || property.isEmpty()) {
            property = defaultValue;
        }
        
        return property;
    }
}
