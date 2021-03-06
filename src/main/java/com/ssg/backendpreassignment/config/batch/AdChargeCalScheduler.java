package com.ssg.backendpreassignment.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring 내장 스케쥴러를 이용하여 AdChargeCalBatchConfig에 구성한 배치 Job을 수행하는 스케쥴러
 */
@Component
@RequiredArgsConstructor
public class AdChargeCalScheduler {
    private final JobLauncher jobLauncher;
    private final AdChargeCalBatchConfig batchConfig;

    @Scheduled(cron="0 0 0 * * *") // 초 분 시 . . .
    public void AdChargeCal() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(batchConfig.job(), jobParameters);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
