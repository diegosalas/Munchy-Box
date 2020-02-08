package com.cabify.cabistore

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class Excercise4Test {

  @Rule
  @JvmField
  var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun excercise4Test() {
    val appCompatImageView = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 0), 4), isDisplayed()))
    appCompatImageView.perform(click())

    val appCompatImageView2 = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 1), 4), isDisplayed()))
    appCompatImageView2.perform(click())

    val appCompatImageView3 = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 0), 4), isDisplayed()))
    appCompatImageView3.perform(click())

    val appCompatImageView4 = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 4), 4), isDisplayed()))
    appCompatImageView4.perform(click())

    val appCompatImageView5 = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 2), 4), isDisplayed()))
    appCompatImageView5.perform(click())

    val appCompatImageView6 = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 1), 4), isDisplayed()))
    appCompatImageView6.perform(click())

    val appCompatImageView7 = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 1), 4), isDisplayed()))
    appCompatImageView7.perform(click())

    val appCompatImageView8 = onView(allOf(withId(R.id.addButton), childAtPosition(childAtPosition(withId(R.id.recyclerView), 0), 4), isDisplayed()))
    appCompatImageView8.perform(click())
  }

  private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(position)
      }
    }
  }
}
