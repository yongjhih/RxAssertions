package rx.assertions;

import org.assertj.core.api.AbstractAssert;
import rx.Notification;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import rx.functions.Action0;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class ObservableAssert<T>
        extends AbstractAssert<ObservableAssert<T>, Observable<T>> {

    private List<Throwable> onErrorEvents;
    private List<T> onNextEvents;
    private List<Notification<T>> onCompletedEvents;

    public ObservableAssert(Observable<T> actual) {
        super(actual, ObservableAssert.class);
        TestSubscriber<T> subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);
        onErrorEvents = subscriber.getOnErrorEvents();
        onNextEvents = subscriber.getOnNextEvents();
        onCompletedEvents = subscriber.getOnCompletedEvents();
    }

    public ObservableAssert<T> then(Action0 action) {
        action.call();
        return this;
    }

    public ObservableAssert<T> completes() {
        assertThat(onCompletedEvents).isNotNull().isNotEmpty();
        return this;
    }

    public ObservableAssert<T> notCompletes() {
        assertThat(onCompletedEvents).isNullOrEmpty();
        return this;
    }

    public ObservableAssert<T> emissionsCount(int count) {
        assertThat(onNextEvents).isNotNull().isNotEmpty().hasSize(count);
        return this;
    }

    public ObservableAssert<T> fails() {
        assertThat(onErrorEvents).isNotNull().isNotEmpty();
        return this;
    }

    public ObservableAssert<T> failsWithThrowable(Class<? extends Throwable> thowableClazz) {
        assertThat(onErrorEvents).isNotNull();
        assertThat(onErrorEvents.get(0)).isInstanceOf(thowableClazz);
        return this;
    }

    public ObservableAssert<T> emitsNothing() {
        assertThat(onNextEvents).isEmpty();
        return this;
    }

    public ObservableAssert<T> receivedTerminalEvent() {
        assertThat(onCompletedEvents).isNotNull();
        assertThat(onErrorEvents).isNotNull();
        assertThat(onCompletedEvents.size() + onErrorEvents.size()).isEqualTo(1);
        return this;
    }

    public ObservableAssert<T> withoutErrors() {
        assertThat(onErrorEvents).isNotNull().isEmpty();
        return this;
    }

    public ObservableAssert<T> expectedSingleValue(T expected) {
        assertThat(onNextEvents)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(onNextEvents.get(0)).isEqualTo(expected);
        return this;
    }

    public ObservableAssert<T> expectedValues() {
        return emitsNothing();
    }

    public ObservableAssert<T> expectedValues(T... expected) {
        return expectedValues(Arrays.asList(expected));
    }

    public ObservableAssert<T> expectedValues(Collection<T> expected) {
        assertThat(onNextEvents)
                .isNotNull()
                .isNotEmpty()
                .hasSameElementsAs(expected);
        return this;
    }

    @SuppressWarnings("unchecked")
    public ObservableAssert<T> expectedBoolean(Boolean expected) {
        return expectedSingleValue((T) expected); // FIXME
    }

    public ObservableAssert<T> expectedTrue() {
        return expectedBoolean(true);
    }

    public ObservableAssert<T> expectedFalse() {
        return expectedBoolean(false);
    }
}
