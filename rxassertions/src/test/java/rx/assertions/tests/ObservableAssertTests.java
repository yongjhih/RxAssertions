package rx.assertions.tests;

import rx.assertions.ObservableAssert;
import org.junit.Test;
import rx.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class ObservableAssertTests {

    @Test public void completesAssertion_ShouldPass() {
        Observable<String> obs = Observable.just("a", "b");
        new ObservableAssert<>(obs).completes();
    }

    @Test public void notCompletesAssertion_ShouldPass() {
        Observable obs = Observable.error(new IllegalStateException());
        new ObservableAssert<>(obs).notCompletes();
    }

    @Test public void emissionsAssertion_ShouldPass() {
        Observable<Integer> obs = Observable.range(1, 3);
        new ObservableAssert<>(obs).emissionsCount(3);
    }

    @Test public void failsAssertion_ShouldPass() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).fails();
    }

    @Test public void failsWithThrowableAssertion_ShouldPass() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).failsWithThrowable(RuntimeException.class);
    }

    @Test(expected = AssertionError.class)
    public void completes_ChainingWithFail_ShouldFail() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).fails().completes();
    }

    @Test public void emitisNothingAssertion_ShouldPass() {
        Observable obs = Observable.from(Collections.emptyList());
        new ObservableAssert<>(obs).emitsNothing();
    }

    @Test public void receivesTerminalEvent_OnCompleted_ShouldPass() {
        Observable<String> obs = Observable.just("c", "d");
        new ObservableAssert<>(obs).receivedTerminalEvent();
    }

    @Test public void receivesTerminalEvents_OnError_ShouldPass() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).receivedTerminalEvent();
    }

    @Test public void withoutErrors_ShouldPass() {
        Observable<String> obs = Observable.just("RxJava");
        new ObservableAssert<>(obs).withoutErrors();
    }

    @Test public void withExpectedSingleValue_ShouldPass() {
        Observable<String> obs = Observable.just("Expected");
        new ObservableAssert<>(obs).expectedSingleValue("Expected");
    }

    @Test(expected = AssertionError.class)
    public void withUnexpectedSingleValue_ShouldFail() {
        Observable<String> obs = Observable.just("Unexpected");
        new ObservableAssert<>(obs).expectedSingleValue("Expected");
    }

    @Test public void withExpectedMultipleValues_ShouldPass() {
        List<String> expected = Arrays.asList("Expected", "Values");
        Observable<String> obs = Observable.from(expected);
        new ObservableAssert<>(obs).expectedValues("Expected", "Values");
    }

    @Test public void withExpectedFalse_ShouldPass() {
        List<String> expected = Arrays.asList("Expected", "Values");
        Observable<Boolean> obs = Observable.from(expected).isEmpty();
        new ObservableAssert<>(obs).expectedFalse();
    }

    @Test public void withExpectedTrue_ShouldPass() {
        List<String> expected = Collections.emptyList();
        Observable<Boolean> obs = Observable.from(expected).isEmpty();
        new ObservableAssert<>(obs).expectedTrue();
    }


}
