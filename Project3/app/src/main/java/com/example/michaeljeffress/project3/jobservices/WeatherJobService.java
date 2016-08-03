package com.example.michaeljeffress.project3.jobservices;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by audreyeso on 8/3/16.
 */

@TargetApi(21)

public class WeatherJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
