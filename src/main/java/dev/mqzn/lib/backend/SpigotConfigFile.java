package dev.mqzn.lib.backend;

import com.google.common.base.Objects;
import com.google.common.io.ByteStreams;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;

public class SpigotConfigFile extends SpigotFile {

    private FileConfiguration config;

    public SpigotConfigFile(Plugin plugin, String fileName, boolean fromResources) {
        super(plugin, fileName, fromResources);
        config = YamlConfiguration.loadConfiguration(file);
    }


    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public void saveConfig() {
        try {
            config.save(getFile());
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void reloadConfig() {
        if(!file.exists()) {

            try {
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream stream = plugin.getResource(this.getFileName());
            YamlConfiguration defConfig = new YamlConfiguration();
            if(stream != null) {
                try {
                    String text = getContentsFromStream(stream);
                    defConfig.loadFromString(text);
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                    plugin.getLogger().log(Level.SEVERE, "Unexpected failure reading/loading  " + this.getFileName(), e);
                }

            }

            this.config.setDefaults(defConfig);
        }

    }

    private String getContentsFromStream(InputStream stream) throws IOException {
        byte[] contents = ByteStreams.toByteArray(stream);
        return new String(contents, Charset.defaultCharset());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpigotConfigFile)) return false;
        if (!super.equals(o)) return false;
        SpigotConfigFile that = (SpigotConfigFile) o;
        return super.equals(o) && Objects.equal(getConfig(), that.getConfig());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getConfig());
    }



}
