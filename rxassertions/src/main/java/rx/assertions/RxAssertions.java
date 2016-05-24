package rx.assertions;

import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class RxAssertions {

    public static <T> ObservableAssert<T> assertThat(Observable<T> observable) {
        return new ObservableAssert<>(observable);
    }

    public static <T> ObservableAssert<T> assertThat(Completable completable) {
        return assertThat(completable.<T>toObservable());
    }

    public static <T> ObservableAssert<T> assertThat(Single<T> single) {
        return assertThat(single.toObservable());
    }

    private RxAssertions() {
        throw new AssertionError("No instances, please");
    }
}
