package com.example.michaeljeffress.project3;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by audreyeso on 8/4/16.
 */
@RunWith(AndroidJUnit4.class)
public class MapScreenTesting {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

}
