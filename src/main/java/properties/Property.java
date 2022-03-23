package properties;

import utils.FileUtils;

import java.util.Properties;

/**
 * Properties enum
 * @author Negar Anabestani
 */
public enum Property {
     MESSAGES_P(FileUtils.FilePath.MESSAGE_PROPERTIES)
    ,COMMANDS_P(FileUtils.FilePath.COMMAND_PROPERTIES);
    private final Properties properties;

     Property(FileUtils.FilePath path){
        this.properties = FileUtils.getInstance().
                loadProperties(path);
    }

    /**
     *  gets property values from proper .properties file using key
     * @param key the key used for getting value from properties
     * @return
     */
    public String get(String key){
        return this.properties.getProperty(key);
    }
}
