## Assignment

Suppose we have a single production environment (no staging, dev, or test). 
How would you design the development and release process of a web application that makes it possible to release the "master" branch at any time? 
How would you manage incrementally developing multiple features in parallel while frequently releasing to production?

## Answer

I would make `master` as *read-only*.
You can merge on to master only via a *fast-forward* merge coming from a pull-request.
Every feature will be developed in a separate branch.
Every feature should have automatic tests.
The pull-request will be accepted only if all tests passes and only if it's a *fast-forward* merge.
In this way, anyone can develop separately in its own branch, the `master` will be considered *stable* and *safe* because of the tests.
