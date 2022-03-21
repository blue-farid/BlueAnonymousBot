package properties;

import utils.FileUtils;

import java.util.Properties;

public enum Property {
     MESSAGES_P(FileUtils.FilePath.MESSAGE_PROPERTIES)
    ,COMMANDS_P(FileUtils.FilePath.COMMAND_PROPERTIES);
    Properties properties;

     Property(FileUtils.FilePath path){
        this.properties = FileUtils.getInstance().
                loadProperties(path);
    }
    public String get(String key){
        return this.properties.getProperty(key);
    }
}
