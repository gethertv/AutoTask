package dev.gether.autotask.task;

import dev.gether.autotask.AutoTask;
import dev.gether.autotask.data.EventData;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ThreadTask extends BukkitRunnable {

    private AutoTask plugin;
    DateTimeFormatter formatter;
    public ThreadTask(AutoTask plugin)
    {
        this.plugin = plugin;
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    @Override
    public void run() {
        LocalTime currentTime = LocalTime.now();

        for (EventData eventData : plugin.getEventData()) {
            String time = currentTime.format(formatter);
            String next = eventData.getEventTimes().format(formatter);
            if (time.equalsIgnoreCase(next)) {
                eventData.getCommands().forEach(cmd -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                });
            }
        }
    }
}
