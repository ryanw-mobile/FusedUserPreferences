# Fused User Preferences

This is an experimental project that tries to put the legacy SharedPreferences and the new Jetpack
Preferences Datastore side by side by means of dependency inversion to see how it would look if it
were to provide the same preferences storage at the data layer.

## Background

In my first few Android apps, which date back to 2010, we did not have any architecture to follow.
We did not have fragments, so we mostly allocated an activity class for each screen. There, if we
wanted to deal with user preferences, it was so easy that we could have placed the code under
onResume or even onCreated. SharedPreferences is non-blocking, so it works quickly and simply for
most small use cases when it does not break.

- Later, people suggested that SharedPreferences being synchronous can be a problem. That is
  sensible when developers abuse SharedPreferences by storing a massive amount of key pairs.
- Later, people came up with more different architectures, so we are not simply accessing user
  preferences right from the activity class.

Eventually, if we want to access user preferences, we can have many more boilerplate codes before
executing the few lines that do the work.

Now we have Jetpack Preference Datastore. It is asynchronous, which means that when we want to
retrieve user preferences, the result is not immediately available. Up to this moment, if we want to
observe preference changes, there is a known limitation: we are not being told which key pair has
changed. We know only _something_ has changed, so we probably have to propagate _all_ the keys we
are interested in, even if we know that only _one_ has changed.

## Approach

Whether for SharedPreferences or Jetpack Preferences Datastore, even if the core is about 10 lines
of code, this code project tries to put them in the right place when following the MVVM and Clean
architecture. That means the UI will reach the ViewModel, which will then connect to a repository
that invisibly talks to either the SharedPreferences or the Jetpack Preferences Datastore data
store.
