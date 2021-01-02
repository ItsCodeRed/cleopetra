package dk.aau.cs.ds306e18.tournament.rlbot.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Class for reading from a bots config file.
 */
public class BotConfig {

    public final static String LOCATIONS_CONFIGURATION_HEADER = "Locations";
    public final static String NAME = "name";
    public final static String PYTHON_FILE = "python_file";
    public final static String LOOKS_CONFIG = "looks_config";

    public final static String DETAILS_CONFIGURATIONS_HEADER = "Details";
    public final static String DEVELOPER = "developer";
    public final static String DESCRIPTION = "description";
    public final static String FUN_FACT = "fun_fact";
    public final static String GITHUB = "github";
    public final static String LANGUAGE = "language";

    private File configFile;
    private String name;
    private File pythonFile;
    private File looksConfig;
    private String developer;
    private String description;
    private String funFact;
    private String github;
    private String language;

    public BotConfig(File configFile) throws IOException {
        this.configFile = configFile;
        ConfigFile config = new ConfigFile(configFile);

        // Ensuring BotConfig has required fields available
        if (!config.hasSection(LOCATIONS_CONFIGURATION_HEADER) ||
                !config.hasValue(LOCATIONS_CONFIGURATION_HEADER, NAME) ||
                !config.hasValue(LOCATIONS_CONFIGURATION_HEADER, PYTHON_FILE) ||
                !config.hasValue(LOCATIONS_CONFIGURATION_HEADER, LOOKS_CONFIG)) {
            throw new IOException(configFile.toString() + " is missing fields in the " + LOCATIONS_CONFIGURATION_HEADER + " section and is probably not a bot.");
        }

        name = config.getString(LOCATIONS_CONFIGURATION_HEADER, NAME, "");
        pythonFile = new File(config.getString(LOCATIONS_CONFIGURATION_HEADER, PYTHON_FILE, ""));
        looksConfig = new File(config.getString(LOCATIONS_CONFIGURATION_HEADER, LOOKS_CONFIG, ""));
        developer = config.getString(DETAILS_CONFIGURATIONS_HEADER, DEVELOPER, "");
        description = config.getString(DETAILS_CONFIGURATIONS_HEADER, DESCRIPTION, "");
        funFact = config.getString(DETAILS_CONFIGURATIONS_HEADER, FUN_FACT, "");
        github = config.getString(DETAILS_CONFIGURATIONS_HEADER, GITHUB, "");
        language = config.getString(DETAILS_CONFIGURATIONS_HEADER, LANGUAGE, "");
    }

    public File getConfigFile() {
        return configFile;
    }

    public String getName() {
        return name;
    }

    public File getPythonFile() {
        return pythonFile;
    }

    public File getLooksConfig() {
        return looksConfig;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getDescription() {
        return description;
    }

    public String getFunFact() {
        return funFact;
    }

    public String getGithub() {
        return github;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotConfig botConfig = (BotConfig) o;
        return Objects.equals(configFile, botConfig.configFile) &&
                Objects.equals(name, botConfig.name) &&
                Objects.equals(pythonFile, botConfig.pythonFile) &&
                Objects.equals(looksConfig, botConfig.looksConfig) &&
                Objects.equals(developer, botConfig.developer) &&
                Objects.equals(description, botConfig.description) &&
                Objects.equals(funFact, botConfig.funFact) &&
                Objects.equals(github, botConfig.github) &&
                Objects.equals(language, botConfig.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configFile, name, pythonFile, looksConfig, developer, description, funFact, github, language);
    }
}
