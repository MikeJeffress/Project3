package com.example.michaeljeffress.project3;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by audreyeso on 8/4/16.
 */
@RunWith(AndroidJUnit4.class)
public class MapScreenTesting {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

//    @Test
//    public void testMarker() throws Exception {
////        onView(withId(R.id.))
////                .perform(click());
//    }

    @Test
    public void testSetLocationButton() throws Exception {
        onView(withId(R.id.setLocationButton))
                .perform(click());
    }

    @Test
    public void testBusinessLocationButton() throws Exception {
        onView(withId(R.id.setTypeButton))
                .perform(click());
    }

    @Test
    public void testSearchBusinessEditText() throws Exception {
        onView(withId(R.id.editText_Main_Type))
                .perform(clearText(),typeText(""));
    }

    @Test
    public void testCurrentLocationEditText() throws Exception {
        onView(withId(R.id.editText_Main_Location))
                .perform(clearText(),typeText(""));
    }
    @Test
    public void testListSwitchButton() throws Exception {
        onView(withId(R.id.recylerViewButton))
                .perform(click());
    }
}
