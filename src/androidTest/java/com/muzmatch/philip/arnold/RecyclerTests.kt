package com.muzmatch.philip.arnold

import android.graphics.drawable.VectorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.graphics.drawable.BitmapDrawable
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4::class)
class RecyclerTests {
    val entryText1 = "Text One"
    val entryText2 = "Text Two"
    val entryText3 = "Text Three"
    val entryText4 = "Text Four"

    @get:Rule
    private val activityRule = ActivityTestRule(
        MainActivity::class.java, false, false)

    @Before
    fun setup() {
        activityRule.launchActivity(null)
    }

    @Test
    fun addNewMessage() {
        onView(withId(R.id.editText)).perform(ViewActions.typeText(entryText1))
        onView(withId(R.id.entryButton)).perform(click())

        onView(withId(R.id.recycler)).check(matches(EspressoTestUtils.atPosition(0, hasDescendant(withText(entryText1)))))
    }

    @Test
    fun addMultipleMessage() {
        onView(withId(R.id.editText)).perform(ViewActions.typeText(entryText1))
        onView(withId(R.id.entryButton)).perform(click())
        onView(withId(R.id.editText)).perform(ViewActions.typeText(entryText2))
        onView(withId(R.id.entryButton)).perform(click())
        onView(withId(R.id.editText)).perform(ViewActions.typeText(entryText3))
        onView(withId(R.id.entryButton)).perform(click())
        onView(withId(R.id.editText)).perform(ViewActions.typeText(entryText4))
        onView(withId(R.id.entryButton)).perform(click())

        onView(withId(R.id.recycler)).check(matches(EspressoTestUtils.atPosition(3, hasDescendant(withText(entryText1)))))
        onView(withId(R.id.recycler)).check(matches(EspressoTestUtils.atPosition(2, hasDescendant(withText(entryText2)))))
        onView(withId(R.id.recycler)).check(matches(EspressoTestUtils.atPosition(1, hasDescendant(withText(entryText3)))))
        onView(withId(R.id.recycler)).check(matches(EspressoTestUtils.atPosition(0, hasDescendant(withText(entryText4)))))
    }


    fun checkBackgroundDrawable(view: View, res: Int): Boolean {
        val expected = ContextCompat.getDrawable(view.getContext(), res)

        if (expected == null) {
            return false
        }

        val actual = view.getBackground()

        if (expected is VectorDrawable) {
            if (actual is VectorDrawable) {
                return (expected as BitmapDrawable).bitmap.sameAs((actual as BitmapDrawable).bitmap)
            }
        }
        return true;
    }
}