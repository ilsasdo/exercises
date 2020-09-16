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

The flow goes like this:

1. The developer checkout the `master` branch
2. The developer creates the `feature/new-awesome-feature` branch starting from `master`
3. Every once in a while, the developer should *pull* from `master` to keep up to date and reduce the merge fatigue.
4. When the issue is done, he can make a *pull-request*
5. The pull request will start a CI/CD pipeline which will build and run the tests.
6. If the pipeline is *green* and the resulting merge is a *fast-forward* merge, then the pipeline automatically (or maybe after a manual approval) merges the branch.
7. If the pipeline is *red* for any reason, or it isn't a *fast-forward* merge, then the pull-request will not be merged and the developer will take care of fixing it.
8. When you want to deploy to production, you can use a hook to another branch, say, `production` where you push `master` when you want to release or, at any successful pull-request, 
   you can publish some sort of artifact (like a *war* or so) and then subordinate to something else the production deployment of that artifact.
