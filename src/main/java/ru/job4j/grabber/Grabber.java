package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.grabber.parses.HabrCareerParse;
import ru.job4j.grabber.parses.Parse;
import ru.job4j.grabber.store.PsqlStore;
import ru.job4j.grabber.store.Store;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {

    private final Properties config = new Properties();

    public Store store() {
        return new PsqlStore(config);
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public void config() throws IOException {
        try (InputStream in = Grabber.class.getClassLoader().getResourceAsStream("app.properties")) {
            config.load(in);
        }
    }

    public Parse parse() {
        return new HabrCareerParse(new HabrCareerDateTimeParser());
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("parse", parse);
        data.put("store", store);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(config.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public static class GrabJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap data = context.getJobDetail().getJobDataMap();
            Store store = (Store) data.get("store");
            Parse parse = (Parse) data.get("parse");
            List<Post> posts = parse.list(HabrCareerParse.FULL_LINK);
            posts.forEach(store::save);
        }
    }

    public static void main(String[] args) {
        try {
            Grabber grabber = new Grabber();
            grabber.config();
            Scheduler scheduler = grabber.scheduler();
            Store store = grabber.store();
            Parse parse = grabber.parse();
            grabber.init(parse, store, scheduler);
        } catch (IOException ioException) {
            throw new IllegalArgumentException("Error in getting config");
        } catch (SchedulerException e) {
            throw new IllegalArgumentException("Error in running scheduler");
        }
    }
}
