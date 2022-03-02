How to test migrations written using [Mongock](https://mongock.io)?
Here is an example, but it does not work with Mongock 5. Mongock 4 works fine.

To reproduce run:
```shell
./gradlew check
```

Result:
```
2022-03-02 20:20:52.237 ERROR 81672 --- [    Test worker] i.m.r.core.executor.MongockRunnerImpl    : Mongock did not acquire process lock. EXITING WITHOUT RUNNING DATA MIGRATION

io.mongock.driver.api.lock.LockCheckException: Lock cannot be acquired after being cancelled
	at io.mongock.driver.core.lock.LockManagerDefault.initialize(LockManagerDefault.java:374) ~[mongock-driver-core-5.0.36.jar:na]
	at io.mongock.driver.core.lock.LockManagerDefault.acquireLockDefault(LockManagerDefault.java:145) ~[mongock-driver-core-5.0.36.jar:na]
	at io.mongock.runner.core.executor.operation.change.MigrationExecutorBase.executeMigration(MigrationExecutorBase.java:96) ~[mongock-runner-core-5.0.36.jar:na]
	at io.mongock.runner.core.executor.operation.change.MigrationExecutorBase.executeMigration(MigrationExecutorBase.java:46) ~[mongock-runner-core-5.0.36.jar:na]
```
