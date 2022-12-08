package ru.job4j.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.job4j.grabber.parses.Parse;
import ru.job4j.grabber.store.Store;

@FunctionalInterface
public interface Grab {

    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
