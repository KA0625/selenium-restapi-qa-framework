package naco.reusablecomponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private Properties prop;

    public ConfigReader() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/global.properties");
        prop.load(fis);
    }

    public String getBaseUrl() {
        return System.getProperty("baseUrl") != null
                ? System.getProperty("baseUrl")
                : prop.getProperty("baseUrl");
    }

    public String getBrowser() {
        return System.getProperty("browser") != null
                ? System.getProperty("browser")
                : prop.getProperty("browser");
    }
    
    public boolean getRunOnGrid() {
        return System.getProperty("runOnGrid") != null
                ? Boolean.parseBoolean(System.getProperty("runOnGrid"))
                : Boolean.parseBoolean(prop.getProperty("runOnGrid"));
    }


}
