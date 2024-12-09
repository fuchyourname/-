package com.and.music.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;

@Configuration
public class QuartzConfig {

    @Resource
    private AutowireCapableBeanFactory capableBeanFactory;

    @Resource
    private JobDetail hotAndNewSongsJobDetail;

    @Resource
    private JobDetail addPlayCountJobDetail;

    @Resource
    private Trigger hotAndNewSongsJobTrigger;

    @Resource
    private Trigger addPlayCountJobTrigger;

    @Bean
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    private final class AutowiringSpringBeanJobFactory extends AdaptableJobFactory {
        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            capableBeanFactory.autowireBean(job);
            return job;
        }
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setOverwriteExistingJobs(true);
        factory.setStartupDelay(5); // 延迟5秒启动
        factory.setAutoStartup(true); // 自动启动
        factory.setJobDetails(hotAndNewSongsJobDetail, addPlayCountJobDetail);
        factory.setTriggers(hotAndNewSongsJobTrigger, addPlayCountJobTrigger);
        return factory;
    }
}
