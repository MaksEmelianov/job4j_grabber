package ru.job4j.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

@FunctionalInterface
public interface Grab {

    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}