package com.zooverse.activities;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.zooverse.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
public class SpeciesSearchActivityTest {
	
	@Rule
	public ActivityScenarioRule<SubjectCatalogueSearchActivity> mNoteListActivityRule =
			new ActivityScenarioRule<>(SubjectCatalogueSearchActivity.class);
	
	@Test
	public void selectSpeciesTest() {
		onView(withId(R.id.searchCriterionEditText)).perform(typeText("Gira"));
		onView(withId(R.id.speciesList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
		onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class)))).check(matches(withText("Giraffe")));
	}
}