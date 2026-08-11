package naco.reusablecomponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties prop;

    public ConfigReader() {
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/global.properties");
            prop.load(fis);
        } catch (IOException e) {
            System.err.println("Could not load global.properties, using defaults.");
        }
    }

    public String getBaseUrl() {
        String env = System.getenv("BASE_URL");
        String sys = System.getProperty("baseUrl");
        return env != null ? env : (sys != null ? sys : prop.getProperty("baseUrl"));
    }

    public String getBrowser() {
        String env = System.getenv("BROWSER");
        String sys = System.getProperty("browser");
        return env != null ? env : (sys != null ? sys : prop.getProperty("browser", "chrome"));
    }

    public boolean getRunOnGrid() {
        String env = System.getenv("RUN_ON_GRID");
        String sys = System.getProperty("runOnGrid");
        return env != null ? Boolean.parseBoolean(env)
                : (sys != null ? Boolean.parseBoolean(sys)
                : Boolean.parseBoolean(prop.getProperty("runOnGrid", "true")));
    }
}
