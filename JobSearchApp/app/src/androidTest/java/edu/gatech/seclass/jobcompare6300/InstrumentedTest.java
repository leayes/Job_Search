package edu.gatech.seclass.jobcompare6300;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    @Rule
    public ActivityScenarioRule<App> tActivityRule = new ActivityScenarioRule<>(
            App.class);

    private void replaceTextHelper(int viewId, String stringToBeSet) {
        // to reduce flaky test, https://stackoverflow.com/a/53430379/1326678
        onView(withId(viewId)).perform(clearText(), replaceText(stringToBeSet), closeSoftKeyboard());
    }

    @Before
    public void setUp() {
        // Initialize your test environment here
        App app = App.getApp();
        app.clearAllPersistedData();
    }

    // This method runs after each test. It's used to clean up the test environment.
    @After
    public void tearDown() {
        // Clean up your test environment here
        App app = App.getApp();
        app.clearAllPersistedData();
    }

    @Test
    public void CurrentJobAndSave() {
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.bCurrentJob)).perform(click());

        onView(withId(R.id.textFieldTitle)).check(matches(withText("Test Title")));
        onView(withId(R.id.textFieldCompany)).check(matches(withText("Test Company")));
        onView(withId(R.id.textFieldCity)).check(matches(withText("Test City")));
        onView(withId(R.id.textFieldState)).check(matches(withText("Test State")));
        onView(withId(R.id.textFieldCoL)).check(matches(withText("1")));
        onView(withId(R.id.textFieldSalary)).check(matches(withText("100")));
        onView(withId(R.id.textFieldBonus)).check(matches(withText("1")));
        onView(withId(R.id.textFieldStockOptions)).check(matches(withText("1")));
        onView(withId(R.id.textFieldHomeFund)).check(matches(withText("1")));
        onView(withId(R.id.textFieldPersonalHolidays)).check(matches(withText("1")));
        onView(withId(R.id.textFieldInternetStipend)).check(matches(withText("1")));
    }

    @Test
    public void CurrentJobAndCancel() {
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "BAD");
        replaceTextHelper(R.id.textFieldCompany, "BAD");
        replaceTextHelper(R.id.textFieldCity, "BAD");
        replaceTextHelper(R.id.textFieldState, "BAD");
        replaceTextHelper(R.id.textFieldCoL, "9");
        replaceTextHelper(R.id.textFieldSalary, "900");
        replaceTextHelper(R.id.textFieldBonus, "9");
        replaceTextHelper(R.id.textFieldStockOptions, "9");
        replaceTextHelper(R.id.textFieldHomeFund, "9");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "9");
        replaceTextHelper(R.id.textFieldInternetStipend, "9");

        onView(withId(R.id.bExit)).perform(click());
        onView(withId(R.id.bCurrentJob)).perform(click());

        onView(withId(R.id.textFieldTitle)).check(matches(withText("Test Title")));
        onView(withId(R.id.textFieldCompany)).check(matches(withText("Test Company")));
        onView(withId(R.id.textFieldCity)).check(matches(withText("Test City")));
        onView(withId(R.id.textFieldState)).check(matches(withText("Test State")));
        onView(withId(R.id.textFieldCoL)).check(matches(withText("1")));
        onView(withId(R.id.textFieldSalary)).check(matches(withText("100")));
        onView(withId(R.id.textFieldBonus)).check(matches(withText("1")));
        onView(withId(R.id.textFieldStockOptions)).check(matches(withText("1")));
        onView(withId(R.id.textFieldHomeFund)).check(matches(withText("1")));
        onView(withId(R.id.textFieldPersonalHolidays)).check(matches(withText("1")));
        onView(withId(R.id.textFieldInternetStipend)).check(matches(withText("1")));
    }

    @Test
    public void JobDataValidityCoL() {
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "-123");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.textFieldCoL)).check(matches(hasErrorText("Cost of living index is negative")));
    }

    @Test
    public void JobDataValiditySalary(){
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "abc");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.textFieldSalary)).check(matches(hasErrorText("Salary is not an integer")));
    }

    @Test
    public void JobDataValidityBonus(){
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "abc");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.textFieldBonus)).check(matches(hasErrorText("Bonus is not an integer")));

    }


    @Test
    public void JobDataValidityNumStockOptions(){
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "abc");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.textFieldStockOptions)).check(matches(hasErrorText("Number of stock options is not an integer")));

    }


    @Test
    public void JobDataValidityHomeBuyFund(){
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "100");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.textFieldHomeFund)).check(matches(hasErrorText("Home buying fund exceeds 15% of salary")));

    }

    @Test
    public void JobDataValidityPersonalDays(){
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "21");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.textFieldPersonalHolidays)).check(matches(hasErrorText("Personal choice holidays exceeds maximum of 20 days")));

    }

    @Test
    public void JobDataValidityInternetStipend(){
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "76");

        onView(withId(R.id.bSaveExit)).perform(click());

        onView(withId(R.id.textFieldInternetStipend)).check(matches(hasErrorText("Internet stipend exceeds maximum of $75")));

    }


    @Test
    public void NewJobOfferSingle(){
        // FIXME: Need to add a portion of the test to make sure that the jobs are saved
        onView(withId(R.id.bJobOffers)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());

        // onView(withId(R.id.textFieldInternetStipend)).check(matches(hasErrorText("Internet stipend exceeds maximum of $75")));

    }

    @Test
    public void NewJobOfferMultiple(){
        // FIXME: Need to add a portion of the test to make sure that the jobs are saved
        onView(withId(R.id.bJobOffers)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title 1");
        replaceTextHelper(R.id.textFieldCompany, "Test Company 1");
        replaceTextHelper(R.id.textFieldCity, "Test City 1");
        replaceTextHelper(R.id.textFieldState, "Test State 1");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveEnterAnother)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title 2");
        replaceTextHelper(R.id.textFieldCompany, "Test Company 2");
        replaceTextHelper(R.id.textFieldCity, "Test City 2");
        replaceTextHelper(R.id.textFieldState, "Test State 2");
        replaceTextHelper(R.id.textFieldCoL, "2");
        replaceTextHelper(R.id.textFieldSalary, "200");
        replaceTextHelper(R.id.textFieldBonus, "2");
        replaceTextHelper(R.id.textFieldStockOptions, "2");
        replaceTextHelper(R.id.textFieldHomeFund, "2");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "2");
        replaceTextHelper(R.id.textFieldInternetStipend, "2");

        onView(withId(R.id.bSaveExit)).perform(click());

        // onView(withId(R.id.textFieldInternetStipend)).check(matches(hasErrorText("Internet stipend exceeds maximum of $75")));

    }

    @Test
    public void NewJobOfferCompare() {
        // FIXME: Need to add a portion of the test to make sure that the jobs are saved
        onView(withId(R.id.bJobOffers)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title 1");
        replaceTextHelper(R.id.textFieldCompany, "Test Company 1");
        replaceTextHelper(R.id.textFieldCity, "Test City 1");
        replaceTextHelper(R.id.textFieldState, "Test State 1");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveEnterAnother)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title 2");
        replaceTextHelper(R.id.textFieldCompany, "Test Company 2");
        replaceTextHelper(R.id.textFieldCity, "Test City 2");
        replaceTextHelper(R.id.textFieldState, "Test State 2");
        replaceTextHelper(R.id.textFieldCoL, "2");
        replaceTextHelper(R.id.textFieldSalary, "200");
        replaceTextHelper(R.id.textFieldBonus, "2");
        replaceTextHelper(R.id.textFieldStockOptions, "2");
        replaceTextHelper(R.id.textFieldHomeFund, "2");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "2");
        replaceTextHelper(R.id.textFieldInternetStipend, "2");

        onView(withId(R.id.bSaveCompare)).perform(click());
    }

    @Test
    public void NewJobOfferCancel(){
        onView(withId(R.id.bJobOffers)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bExit)).perform(click());

        // onView(withId(R.id.textFieldInternetStipend)).check(matches(hasErrorText("Internet stipend exceeds maximum of $75")));

    }


    @Test
    public void ComparisonAdjustSave(){
        // FIXME: Doesn't seem like comp settings are being persisted
        onView(withId(R.id.bCompSettings)).perform(click());

        replaceTextHelper(R.id.compSalary, "1");
        replaceTextHelper(R.id.compBonus, "1");
        replaceTextHelper(R.id.compStock, "1");
        replaceTextHelper(R.id.compHomeFund, "1");
        replaceTextHelper(R.id.compPersonalHolidays, "1");
        replaceTextHelper(R.id.compInternetStipend, "1");

        onView(withId(R.id.bSave)).perform(click());
        onView(withId(R.id.bCompSettings)).perform(click());

        onView(withId(R.id.compSalary)).check(matches(withText("1")));
        onView(withId(R.id.compBonus)).check(matches(withText("1")));
        onView(withId(R.id.compStock)).check(matches(withText("1")));
        onView(withId(R.id.compHomeFund)).check(matches(withText("1")));
        onView(withId(R.id.compPersonalHolidays)).check(matches(withText("1")));
        onView(withId(R.id.compInternetStipend)).check(matches(withText("1")));

    }

    @Test
    public void ComparisonAdjustCancel(){
        // FIXME: Doesn't seem like comp settings are being persisted
        onView(withId(R.id.bCompSettings)).perform(click());

        replaceTextHelper(R.id.compSalary, "1");
        replaceTextHelper(R.id.compBonus, "1");
        replaceTextHelper(R.id.compStock, "1");
        replaceTextHelper(R.id.compHomeFund, "1");
        replaceTextHelper(R.id.compPersonalHolidays, "1");
        replaceTextHelper(R.id.compInternetStipend, "1");

        onView(withId(R.id.bSave)).perform(click());
        onView(withId(R.id.bCompSettings)).perform(click());

        replaceTextHelper(R.id.compSalary, "2");
        replaceTextHelper(R.id.compBonus, "2");
        replaceTextHelper(R.id.compStock, "2");
        replaceTextHelper(R.id.compHomeFund, "2");
        replaceTextHelper(R.id.compPersonalHolidays, "2");
        replaceTextHelper(R.id.compInternetStipend, "2");

        onView(withId(R.id.bExit)).perform(click());
        onView(withId(R.id.bCompSettings)).perform(click());

        onView(withId(R.id.compSalary)).check(matches(withText("1")));
        onView(withId(R.id.compBonus)).check(matches(withText("1")));
        onView(withId(R.id.compStock)).check(matches(withText("1")));
        onView(withId(R.id.compHomeFund)).check(matches(withText("1")));
        onView(withId(R.id.compPersonalHolidays)).check(matches(withText("1")));
        onView(withId(R.id.compInternetStipend)).check(matches(withText("1")));
    }
//    FIXME: Waiting for comparison to work
//    CompareOrdering

//    @Test
//    public void CompareNoJobs() {
//        // FIXME: Doesn't seem like comp settings are being persisted
//        onView(withId(R.id.bCompare)).perform(click());
//        onView(withId(R.id.textView9)).check(matches(withText("Main Menu")));
//    }

//    CompareOneJob
    //CompareOneCurrentJob
    @Test
    public void CompareOneJobAndCurrentJob() {
        onView(withId(R.id.bCurrentJob)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveExit)).perform(click());
        onView(withId(R.id.bJobOffers)).perform(click());

        replaceTextHelper(R.id.textFieldTitle, "Test Title");
        replaceTextHelper(R.id.textFieldCompany, "Test Company");
        replaceTextHelper(R.id.textFieldCity, "Test City");
        replaceTextHelper(R.id.textFieldState, "Test State");
        replaceTextHelper(R.id.textFieldCoL, "1");
        replaceTextHelper(R.id.textFieldSalary, "100");
        replaceTextHelper(R.id.textFieldBonus, "1");
        replaceTextHelper(R.id.textFieldStockOptions, "1");
        replaceTextHelper(R.id.textFieldHomeFund, "1");
        replaceTextHelper(R.id.textFieldPersonalHolidays, "1");
        replaceTextHelper(R.id.textFieldInternetStipend, "1");

        onView(withId(R.id.bSaveCompare)).perform(click());

    }
//
//    CompareTwoJobsNoCurrentJob
//    CompareJobsSelection
//    CompareJobsCoLAdjustment

}

