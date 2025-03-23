package com.and.music.config;

import com.and.music.job.AddPlayCountJob;
import com.and.music.job.HotAndNewSongsJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzJobConfig {

    @Bean
    public JobDetail hotAndNewSongsJobDetail() {
        return JobBuilder.newJob(HotAndNewSongsJob.class)
                .withIdentity("hotAndNewSongsJob", "DEFAULT")
                .storeDurably()
                .build();
    }

    @Bean
    public JobDetail addPlayCountJobDetail() {
        return JobBuilder.newJob(AddPlayCountJob.class)
                .withIdentity("addPlayCountJob", "DEFAULT")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger hotAndNewSongsJobTrigger(JobDetail hotAndNewSongsJobDetail) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(1)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(hotAndNewSongsJobDetail)
                .withIdentity("hotAndNewSongsJobTrigger", "DEFAULT")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger addPlayCountJobTrigger(JobDetail addPlayCountJobDetail) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(1)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(addPlayCountJobDetail)
                .withIdentity("addPlayCountJobTrigger", "DEFAULT")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
