package rx.assertions.tests;

import rx.assertions.ObservableAssert;
import org.junit.Test;
import rx.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import rx.functions.Action0;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class ObservableAssertTests {

    @Test public void completesAssertion() {
        Observable<String> obs = Observable.just("a", "b");
        new ObservableAssert<>(obs).completes();
    }

    @Test public void notCompletesAssertion() {
        Observable obs = Observable.error(new IllegalStateException());
        new ObservableAssert<>(obs).notCompletes();
    }

    @Test public void emissionsAssertion() {
        Observable<Integer> obs = Observable.range(1, 3);
        new ObservableAssert<>(obs).emissionsCount(3);
    }

    @Test public void failsAssertion() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).fails();
    }

    @Test public void failsWithThrowableAssertion() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).failsWithThrowable(RuntimeException.class);
    }

    @Test(expected = AssertionError.class)
    public void completesChainingWithFail() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).fails().completes();
    }

    @Test public void emitisNothingAssertion() {
        Observable obs = Observable.from(Collections.emptyList());
        new ObservableAssert<>(obs).emitsNothing();
    }

    @Test public void receivesTerminalEventOnCompleted() {
        Observable<String> obs = Observable.just("c", "d");
        new ObservableAssert<>(obs).receivedTerminalEvent();
    }

    @Test public void receivesTerminalEventsOnError() {
        Observable obs = Observable.error(new RuntimeException());
        new ObservableAssert<>(obs).receivedTerminalEvent();
    }

    @Test public void withoutErrors() {
        Observable<String> obs = Observable.just("RxJava");
        new ObservableAssert<>(obs).withoutErrors();
    }

    @Test public void withExpectedSingleValue() {
        Observable<String> obs = Observable.just("Expected");
        new ObservableAssert<>(obs).expectedSingleValue("Expected");
    }

    @Test(expected = AssertionError.class)
    public void withUnexpectedSingleValue() {
        Observable<String> obs = Observable.just("Unexpected");
        new ObservableAssert<>(obs).expectedSingleValue("Expected");
    }

    @Test public void withExpectedMultipleValues() {
        List<String> expected = Arrays.asList("Expected", "Values");
        Observable<String> obs = Observable.from(expected);
        new ObservableAssert<>(obs).expectedValues("Expected", "Values");
    }

    @Test public void withExpectedFalse() {
        List<String> expected = Arrays.asList("Expected", "Values");
        Observable<Boolean> obs = Observable.from(expected).isEmpty();
        new ObservableAssert<>(obs).expectedFalse();
    }

    @Test public void withExpectedTrue() {
        List<String> expected = Collections.emptyList();
        Observable<Boolean> obs = Observable.from(expected).isEmpty();
        new ObservableAssert<>(obs).expectedTrue();
    }

    @Test public void withExpectedValuesWithEmpty() {
        Observable<String> obs = Observable.empty();
        new ObservableAssert<>(obs).expectedValues();
    }

    @Test public void unsubscribe() {
        Observable<String> obs = Observable.empty();
        new ObservableAssert<>(obs).expectedValues().unsubscribe();
        new ObservableAssert<>(obs).expectedValues().unsubscribe().expectedValues().unsubscribe();
    }

    @Test public void then() {
        final List<String> expected = new ArrayList<>();
        Observable<String> obs = Observable.from(expected);
        new ObservableAssert<>(obs).expectedValues().then(new Action0() {
            @Override public void call() {
                expected.add("hello");
            }
        }).expectedValues();
        assertThat(expected).contains("hello");
    }

}
