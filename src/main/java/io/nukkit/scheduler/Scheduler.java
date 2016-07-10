package io.nukkit.scheduler;

import io.nukkit.plugin.Plugin;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface Scheduler {

    /**
     * Schedules a once off task to occur after a delay.
     * <p>
     * This task will be executed by the main server thread.
     *
     * @param plugin Plugin that owns the task
     * @param task   Task to be executed
     * @param delay  Delay in server ticks before executing task
     * @return Task id number (-1 if scheduling failed)
     */
    int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay);

    /**
     * @deprecated Use {@link NukkitRunnable#runTaskLater(Plugin, long)}
     */
    @Deprecated
    int scheduleSyncDelayedTask(Plugin plugin, NukkitRunnable task, long delay);

    /**
     * Schedules a once off task to occur as soon as possible.
     * <p>
     * This task will be executed by the main server thread.
     *
     * @param plugin Plugin that owns the task
     * @param task   Task to be executed
     * @return Task id number (-1 if scheduling failed)
     */
    int scheduleSyncDelayedTask(Plugin plugin, Runnable task);

    /**
     * @deprecated Use {@link NukkitRunnable#runTask(Plugin)}
     */
    @Deprecated
    int scheduleSyncDelayedTask(Plugin plugin, NukkitRunnable task);

    /**
     * Schedules a repeating task.
     * <p>
     * This task will be executed by the main server thread.
     *
     * @param plugin Plugin that owns the task
     * @param task   Task to be executed
     * @param delay  Delay in server ticks before executing first repeat
     * @param period Period in server ticks of the task
     * @return Task id number (-1 if scheduling failed)
     */
    int scheduleSyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period);

    /**
     * @deprecated Use {@link NukkitRunnable#runTaskTimer(Plugin, long, long)}
     */
    @Deprecated
    int scheduleSyncRepeatingTask(Plugin plugin, NukkitRunnable task, long delay, long period);

    /**
     * <b>Asynchronous tasks should never access any API in Nukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules a once off task to occur after a delay. This task will be
     * executed by a thread managed by the scheduler.
     *
     * @param plugin Plugin that owns the task
     * @param task   Task to be executed
     * @param delay  Delay in server ticks before executing task
     * @return Task id number (-1 if scheduling failed)
     * @deprecated This name is misleading, as it does not schedule "a sync"
     * task, but rather, "an async" task
     */
    @Deprecated
    int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay);

    /**
     * <b>Asynchronous tasks should never access any API in Nukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules a once off task to occur as soon as possible. This task will
     * be executed by a thread managed by the scheduler.
     *
     * @param plugin Plugin that owns the task
     * @param task   Task to be executed
     * @return Task id number (-1 if scheduling failed)
     * @deprecated This name is misleading, as it does not schedule "a sync"
     * task, but rather, "an async" task
     */
    @Deprecated
    int scheduleAsyncDelayedTask(Plugin plugin, Runnable task);

    /**
     * <b>Asynchronous tasks should never access any API in Nukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules a repeating task. This task will be executed by a thread
     * managed by the scheduler.
     *
     * @param plugin Plugin that owns the task
     * @param task   Task to be executed
     * @param delay  Delay in server ticks before executing first repeat
     * @param period Period in server ticks of the task
     * @return Task id number (-1 if scheduling failed)
     * @deprecated This name is misleading, as it does not schedule "a sync"
     * task, but rather, "an async" task
     */
    @Deprecated
    int scheduleAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period);

    /**
     * Calls a method on the main thread and returns a Future object. This
     * task will be executed by the main server thread.
     * <ul>
     * <li>Note: The Future.get() methods must NOT be called from the main
     * thread.
     * <li>Note2: There is at least an average of 10ms latency until the
     * isDone() method returns true.
     * </ul>
     *
     * @param <T>    The callable's return type
     * @param plugin Plugin that owns the task
     * @param task   Task to be executed
     * @return Future Future object related to the task
     */
    <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task);

    /**
     * Removes task from scheduler.
     *
     * @param taskId Id number of task to be removed
     */
    void cancelTask(int taskId);

    /**
     * Removes all tasks associated with a particular plugin from the
     * scheduler.
     *
     * @param plugin Owner of tasks to be removed
     */
    void cancelTasks(Plugin plugin);

    /**
     * Removes all tasks from the scheduler.
     */
    void cancelAllTasks();

    /**
     * Check if the task currently running.
     * <p>
     * A repeating task might not be running currently, but will be running in
     * the future. A task that has finished, and does not repeat, will not be
     * running ever again.
     * <p>
     * Explicitly, a task is running if there exists a thread for it, and that
     * thread is alive.
     *
     * @param taskId The task to check.
     *               <p>
     * @return If the task is currently running.
     */
    boolean isCurrentlyRunning(int taskId);

    /**
     * Check if the task queued to be run later.
     * <p>
     * If a repeating task is currently running, it might not be queued now
     * but could be in the future. A task that is not queued, and not running,
     * will not be queued again.
     *
     * @param taskId The task to check.
     *               <p>
     * @return If the task is queued to be run.
     */
    boolean isQueued(int taskId);

    /**
     * Returns a list of all active workers.
     * <p>
     * This list contains asynch tasks that are being executed by separate
     * threads.
     *
     * @return Active workers
     */
    List<Worker> getActiveWorkers();

    /**
     * Returns a list of all pending tasks. The ordering of the tasks is not
     * related to their order of execution.
     *
     * @return Active workers
     */
    List<Task> getPendingTasks();

    /**
     * Returns a task that will run on the next server tick.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @return a Task that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    Task runTask(Plugin plugin, Runnable task) throws IllegalArgumentException;

    /**
     * @deprecated Use {@link NukkitRunnable#runTask(Plugin)}
     */
    @Deprecated
    Task runTask(Plugin plugin, NukkitRunnable task) throws IllegalArgumentException;

    /**
     * <b>Asynchronous tasks should never access any API in Nukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Returns a task that will run asynchronously.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @return a Task that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    Task runTaskAsynchronously(Plugin plugin, Runnable task) throws IllegalArgumentException;

    /**
     * @deprecated Use {@link NukkitRunnable#runTaskAsynchronously(Plugin)}
     */
    @Deprecated
    Task runTaskAsynchronously(Plugin plugin, NukkitRunnable task) throws IllegalArgumentException;

    /**
     * Returns a task that will run after the specified number of server
     * ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     * @return a Task that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    Task runTaskLater(Plugin plugin, Runnable task, long delay) throws IllegalArgumentException;

    /**
     * @deprecated Use {@link NukkitRunnable#runTaskLater(Plugin, long)}
     */
    @Deprecated
    Task runTaskLater(Plugin plugin, NukkitRunnable task, long delay) throws IllegalArgumentException;

    /**
     * <b>Asynchronous tasks should never access any API in Nukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Returns a task that will run asynchronously after the specified number
     * of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     * @return a Task that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    Task runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay) throws IllegalArgumentException;

    /**
     * @deprecated Use {@link NukkitRunnable#runTaskLaterAsynchronously(Plugin, long)}
     */
    @Deprecated
    Task runTaskLaterAsynchronously(Plugin plugin, NukkitRunnable task, long delay) throws IllegalArgumentException;

    /**
     * Returns a task that will repeatedly run until cancelled, starting after
     * the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @return a Task that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    Task runTaskTimer(Plugin plugin, Runnable task, long delay, long period) throws IllegalArgumentException;

    /**
     * @deprecated Use {@link NukkitRunnable#runTaskTimer(Plugin, long, long)}
     */
    @Deprecated
    Task runTaskTimer(Plugin plugin, NukkitRunnable task, long delay, long period) throws IllegalArgumentException;

    /**
     * <b>Asynchronous tasks should never access any API in Nukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Returns a task that will repeatedly run asynchronously until cancelled,
     * starting after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param task   the task to be run
     * @param delay  the ticks to wait before running the task for the first
     *               time
     * @param period the ticks to wait between runs
     * @return a Task that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalArgumentException if task is null
     */
    Task runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period) throws IllegalArgumentException;

    /**
     * @deprecated Use {@link NukkitRunnable#runTaskTimerAsynchronously(Plugin, long, long)}
     */
    @Deprecated
    Task runTaskTimerAsynchronously(Plugin plugin, NukkitRunnable task, long delay, long period) throws IllegalArgumentException;
}