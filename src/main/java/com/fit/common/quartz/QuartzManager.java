package com.fit.common.quartz;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务管理类
 */
public class QuartzManager {

    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();  //创建一个SchedulerFactory工厂实例
    private static String JOB_GROUP_NAME = "FH_JOB_GROUP_NAME";                    //任务组
    private static String TRIGGER_GROUP_NAME = "FH_TRIGGER_GROUP_NAME";            //触发器组

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param jobName 任务名
     * @param cls     任务
     * @param time    时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, Class<? extends Job> cls, String time) {
        addJob(jobName, cls, time, null);
    }

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名  （带参数）
     *
     * @param jobName 任务名
     * @param cls     任务
     * @param time    时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, Class<? extends Job> cls, String time, Map<String, Object> parameter) {
        addJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, cls, time, parameter);
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                              Class<? extends Job> jobClass, String time) {
        addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, time, null);
    }

    /**
     * 添加一个定时任务  （带参数）
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                              Class<? extends Job> jobClass, String time, Map<String, Object> parameter) {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();// 任务名，任务组，任务执行类
            if (parameter != null) {
                jobDetail.getJobDataMap().put("parameterList", parameter);                                //传参数
            }
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();      // 启动
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName 任务名
     * @param time    新的时间设置
     */
    public static void modifyJobTime(String jobName, String time) {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();                            //通过SchedulerFactory构建Scheduler对象
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);        //通过触发器名和组名获取TriggerKey
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);              //通过TriggerKey获取CronTrigger
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);                        //通过任务名和组名获取JobKey
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                Class<? extends Job> objJobClass = jobDetail.getJobClass();
                removeJob(jobName);
                addJob(jobName, objJobClass, time);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      任务名称
     * @param triggerGroupName 传过来的任务名称
     * @param time             更新后的时间规则
     */
    public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();                            //通过SchedulerFactory构建Scheduler对象
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);    //通过触发器名和组名获取TriggerKey
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);                //通过TriggerKey获取CronTrigger
            if (trigger == null) return;
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(trigger.getCronExpression());
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                trigger = (CronTrigger) trigger.getTriggerBuilder()        //重新构建trigger
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .withSchedule(CronScheduleBuilder.cronSchedule(time))
                        .build();
                sched.rescheduleJob(triggerKey, trigger);                //按新的trigger重新设置job执行
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName 任务名称
     */
    public static void removeJob(String jobName) {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);    //通过触发器名和组名获取TriggerKey
            JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);                        //通过任务名和组名获取JobKey
            scheduler.pauseTrigger(triggerKey);     // 停止触发器
            scheduler.unscheduleJob(triggerKey);    // 移除触发器
            scheduler.deleteJob(jobKey);            // 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);    //通过触发器名和组名获取TriggerKey
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);                            //通过任务名和组名获取JobKey
            sched.pauseTrigger(triggerKey);    // 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器  
            sched.deleteJob(jobKey);        // 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sche = gSchedulerFactory.getScheduler();
            sche.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}  