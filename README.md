# Fused User Preferences

This is an experimental project, trying to put the legacy SharedPreferences and the new Jetpack Preferences Datastore side by side by means of dependency inversion, to see how it would look if it were to provide the same preferences storages at the data layer.

## Background

In my first few Android apps dated back to 2010, we did not have any architecture to follow. We did not have fragments, so mostly we allocate an activity class for each screen. There, if we want to deal with user preferences, it was so easy that we could have placed the code under onResume or even onCreated. SharedPreferences is non-blocking, so that when it does not break, it works quick and simple for most small use cases.

Later on, people suggested that being non-blocking (and synchronus) can be a problem.
Later on, people came up with more different architectures.

Eventually if we want to access user preferences, we have many more boilerplate code before actually executing the few lines that really do the work.

Now we have Jetpack Preference Datastore. It is asynchrous, which means when we want to retrieve user perference, the result is not immediately available. Up to this moment, if we want to observe preferences changes, there is a known limitation that we are not being told which preference key has changed. We know only _something_ has changed, so that we probably have to propagate _all_ the keys we are interested in, whenever we know _one_ of them has changed.

## Approach

No matter it is for SharedPreferences or Jetpack Preferences Datastore, even the core is just about less than 10 lines of code, this code project tries to put them to the right place when following the MVVM and Clean architecture. That means the UI will reach the ViewModel, which will then connect to a repository that invisiblty talk to either the SharedPreferences or the Jetpack Preferences Datastore data store.

