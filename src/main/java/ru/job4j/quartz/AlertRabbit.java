package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            var interval = getIntervalFromFile();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static int getIntervalFromFile() {
        Properties properties = new Properties();
        var path = new File("src\\main\\resources\\rabbit.properties").getAbsolutePath();
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        var rsl = properties.get("rabbit.interval").toString();
        return Integer.parseInt(rsl);
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}
