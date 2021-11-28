package dev.mqzn.lib.backend;

import com.google.common.base.Objects;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SpigotFile {

    protected final Plugin plugin;
    private final String fileName;
    protected File file;


    public SpigotFile(Plugin plugin, String fileName, boolean fromResource) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);

        if(!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdir()) {
            Bukkit.getConsoleSender().sendMessage(Translator.color("&cFailed to create the Data Folder for " + plugin.getName()));
            return;
        }

        if(!file.exists()) {

            if(fromResource) {
                plugin.saveResource(fileName, false);
            }else {

                int countOfTries = 0;
                boolean success = false;

                try {
                    success = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                do {
                    String msg = !success ? ("&cCouldn't create the file " +
                            fileName + "for " + plugin.getName())
                            : ("&aCreated the file " + fileName + "for " + plugin.getName());

                    Bukkit.getConsoleSender().sendMessage(Translator.color(msg));
                    countOfTries++;
                } while (!success && countOfTries < 2);

                if (countOfTries >= 2) {
                    Bukkit.getConsoleSender()
                            .sendMessage(Translator
                                    .color("&cFailed 2 Attempts to create the file " + fileName));
                }

            }
        }

    }


    public File getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpigotFile)) return false;
        SpigotFile that = (SpigotFile) o;
        return Objects.equal(plugin, that.plugin) &&
                Objects.equal(getFileName(), that.getFileName()) &&
                Objects.equal(getFile(), that.getFile());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plugin, getFileName(), getFile());
    }

}
