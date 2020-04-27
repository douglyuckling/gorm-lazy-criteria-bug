# GORM Lazy Criteria Bug

This repo is a minimum working example to reproduce what I believe to be a serious bug in GORM. You can reproduce the
issue simply by running:

```
./grailsw test-app
```

In short, the bug is that `DetachedCriteria.deleteAll()` does not evaluate the `lazyQuery` closure, resulting in _all_
records in the table being deleted instead of only those matching the intended criteria.

There are two other factors that make this bug particularly insidious:

* Non-qualified invocations of `where()` are apparently replaced by `whereLazy()` at runtime, which causes this bug to
  manifest itself in simple helper methods like this one in the example `Product` class:
  
  ```
  static void removeAllByColor(String givenColor) {
      where { color == givenColor }.deleteAll()
  }
  ```
  
  Note that this bug does not occur when using a qualified reference to `where()` (i.e. `Product.where()`) because in
  that case the invocation is not dynamiclly converted to `whereLazy()`.
  Of course, the qualifier should not be necessary here (and IntelliJ dutifully shows a warning if we use it).

* This bug is not caught by unit tests that implement `DataTest` because the implementation of `Session.deleteAll()`
  used in that case happens to be based on an invocation of `criteria.list()`, which properly handles `lazyQuery`.

It looks like the same bug exists for `DetachedCriteria.updateAll()`. 

