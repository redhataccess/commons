package com.redhat.ir.platform;

import com.redhat.common.AbstractBase;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Abstract base class for caching.
 *
 * @author sfloess
 */
public class Cache<T> extends AbstractBase {
    public static final long DEFAULT_INITIAL_PERIOD = 0;

    /**
     * Holds our refresh.
     */
    private final AtomicReference<T> cache;

    /**
     * Creates our cached data.
     */
    private final Supplier<T> supplier;

    protected void updateCache(final Supplier<T> supplier) {
        logInfo("Proceeding to cache...");

        final long startTime = System.currentTimeMillis();

        try {
            cache.set(supplier.get());
        } catch (Throwable t) {
            logError("Error while caching data " + t.getMessage(), t);
        }

        logInfo("Total time to cache:  [", (System.currentTimeMillis() - startTime), " ms]");
    }

    protected void updateCache() {
        updateCache(getSupplier());
    }

    protected T getCached() {
        return cache.get();
    }

    protected Supplier<T> getSupplier() {
        return supplier;
    }

    public Cache(final Supplier<T> supplier, final ScheduledExecutorService executorService, final long initialPeriod, final long period) {
        this.cache = new AtomicReference();
        this.supplier = Objects.requireNonNull(supplier, "Supplier cannot be null!");

        logInfo("Cache time out:  [", period, " ms]");

        // If the initial period is 0 (immediate), update the cache.
        if (initialPeriod < 1) {
            updateCache(supplier);
        }

        executorService.scheduleAtFixedRate(this::updateCache, initialPeriod, period, TimeUnit.MILLISECONDS);
    }

    public Cache(final Supplier<T> supplier, final long initialPeriod, final long period) {
        this(supplier, Executors.newScheduledThreadPool(1), initialPeriod, period);
    }

    public Cache(final Supplier<T> supplier, final ScheduledExecutorService executorService, final long period) {
        this(supplier, executorService, DEFAULT_INITIAL_PERIOD, period);
    }

    public Cache(final Supplier<T> supplier, final long period) {
        this(supplier, DEFAULT_INITIAL_PERIOD, period);
    }
}
