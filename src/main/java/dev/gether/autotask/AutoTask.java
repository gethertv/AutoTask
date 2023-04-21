package dev.gether.autotask;

import dev.gether.autotask.data.EventData;
import dev.gether.autotask.placeholder.TaskTimePlaceholder;
import dev.gether.autotask.task.ThreadTask;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public final class AutoTask extends JavaPlugin {

    private static AutoTask instance;
    private List<EventData> eventData;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        loadEventTime();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            TaskTimePlaceholder taskTimePlaceholder = new TaskTimePlaceholder();
            taskTimePlaceholder.setStartDate(LocalDateTime.of(getConfig().getInt("start.rok"),
                    getConfig().getInt("start.miesiac"),
                    getConfig().getInt("start.dzien"),
                    getConfig().getInt("start.godzina"),
                    getConfig().getInt("start.minuta")
                    ));
            taskTimePlaceholder.register();
        }

        new ThreadTask(this).runTaskTimer(this, 0L, 20L);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new TaskTimePlaceholder()).unregister();
        }
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
    }

    private void loadEventTime() {

        eventData = new ArrayList<>();

        for(String key : getConfig().getConfigurationSection("autotask").getKeys(false))
        {
            String[] date = getConfig().getString("autotask."+key+".time").split(":");
            List<String> cmds = new ArrayList<>();
            cmds.addAll(getConfig().getStringList("autotask."+key+".commands"));

            eventData.add(new EventData(key, LocalTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1])), cmds));
        }

    }

    public static AutoTask getInstance() {
        return instance;
    }

    public List<EventData> getEventData() {
        return eventData;
    }
}
