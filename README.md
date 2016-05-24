# RxAssertions

[![JitPack](https://img.shields.io/github/tag/yongjhih/rxassertions.svg?label=JitPack)](https://jitpack.io/#yongjhih/rxassertions)
[![javadoc](https://img.shields.io/github/tag/yongjhih/rxassertions.svg?label=javadoc)](https://jitpack.io/com/github/yongjhih/rxassertions/-SNAPSHOT/javadoc/)
[![Build Status](https://travis-ci.org/yongjhih/rxassertions.svg)](https://travis-ci.org/yongjhih/rxassertions)
[![Coverage Status](https://coveralls.io/repos/github/yongjhih/rxassertions/badge.svg)](https://coveralls.io/github/yongjhih/rxassertions)

![RxAssertions](https://github.com/yongjhih/rxassertions/raw/master/art/rxassertions.png)

RxAssertions is a simple idea for better RxJava assertions.

I found the original idea from the guys of [Ribot](https://github.com/ribot/assertj-rx) : in fact, I think this a good idea and helps to keep tests clean.

However, Ribot guys deprecated their original repo some time ago in favor of vanilla [TestSubscriber](http://reactivex.io/RxJava/javadoc/rx/observers/TestSubscriber.html), so I decided to take my own shot on this.

This library mimics and improves the original Ribot`s idea with the following goodies :

1. AssertJ powered assertions for RxJava (as the original one)
2. All tests rely on **BlockingObservable** internally
2. Internal API rely 100% on **TestSubscriber**
3. Improved public API, covering most of TestSubscriber provided assertions
4. Improved _Assertions_ entry points from factory methods, with **Observable**, **BlockingObservable**, **Single** and **Completable** support

We can see some code diet :

**Regular assertions with TestSubscriber**
```java
TestSubscriber<String> testSubscriber = new TestSubscriber<>();
Observable.just("RxJava", "Assertions").toBlocking().subscribe(testSubscriber);
testSubscriber.assertCompleted();
testSubscriber.assertNoErrors();
testSubscriber.assertValues("RxJava", "Assertions");
```

**Assertions with RxAssertions**
```java
RxAssertions.assertThat(Observable.just("RxJava", "Assertions"))
		.completes()
		.withoutErrors()
		.expectedValues("RxJava", "Assertions");
```
or

```java
RxAssertions.assertThat(Observable.empty())
		.emitsNothing()
		.completes()
		.withoutErrors();
```
or

```java
Single<String> single = Single.fromCallable(() -> "RxJava");
RxAssertions.assertThat(single).completes().expectedSingleValue("RxJava");
```

You can find other examples at `test` folder

## Setup

Add it in your `build.gradle`

```groovy
repositories {
	...
	maven { url "https://jitpack.io" }

}
```
Add the dependency

```groovy
dependencies {
	...
	testCompile 'com.github.yongjhih:rxassertions:-SNAPSHOT'
	// testCompile 'com.github.yongjhih:rxassertions:0.2.1'
}
```
RxAssertions uses RxJava `1.1.5` and AssertJ `2.4.1` as dependencies.

## TODO

```java
Observable.just("RxJava", "Assertions")
    .assertHasSize(2)
    .filter(s -> "RxJava".equals(s))
    .assertHasSize(1)
    .assertWithoutErrors()
    .assertValues("RxJava")
    .subscribe();
```

## Contributing

PRs are wellcome. :rocket:

## Credits

- Ribot guys for the original idea
- RxJava and AssertJ guys for these awesome libraries

## License

```
Copyright (C) 2016 Andrew Chen
Copyright (C) 2016 Ubiratan Soares

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

