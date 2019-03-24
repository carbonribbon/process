package com.carbonribbon.proc;

import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Reference implementation of {@link ProcessChain}.
 *
 * @author Patrik Dallmann (@carbonribbon.com)
 * @version 1.0
 */
public class LinkedProcessChain<T> implements ProcessChain<T> {

    private final Deque<MetaLink<T>> links;
    private final ProcessLinkFactory factory;

    /**
     * Create a new {@link ProcessChain} using the {@link DefaultProcessLinkFactory}
     */
    public LinkedProcessChain() {
        links = new ConcurrentLinkedDeque<>();
        factory = new DefaultProcessLinkFactory();
    }

    /**
     * Create a new {@link ProcessChain} using the provided {@link ProcessLinkFactory}
     *
     * @param factory {@link ProcessLinkFactory} to use for {@link ProcessLink} objects
     */
    public LinkedProcessChain(ProcessLinkFactory factory) {
        links = new ConcurrentLinkedDeque<>();
        this.factory = factory;
    }

    @Override
    public void addLink(ProcessLink<T> processLink) {
        if (processLink == null) {
            throw new NullPointerException();
        }
        synchronized (this) {
            links.add(new MetaLink<>(processLink));
        }
    }

    @Override
    public void addLink(Class<? extends ProcessLink<T>> clazz) {
        ProcessLink<T> clazzInst = factory.newProcessLink(clazz);
        synchronized (this) {
            links.add(new MetaLink<>(clazzInst));
        }
    }

    @Override
    public void addLink(Class<? extends ProcessLink<T>> clazz, Predicate<T> test) {
        if (test == null) {
            throw new NullPointerException();
        }
        ProcessLink<T> clazzInst = factory.newProcessLink(clazz);
        synchronized (this) {
            links.add(new MetaLink<>(clazzInst, test));
        }
    }

    @Override
    public T applyChain(final T t) {
        T tt = t;
        for (MetaLink<T> link : links) {
            if (link.test(t)) {
                tt = link.getProcessLink().apply(tt);
            }
        }
        return tt;
    }

    @Override
    public Collection<T> applyChain(Collection<T> tt) {
        Stream<T> s = tt.stream();
        for (MetaLink<T> link : links) {
            s = s.map(t -> {
                if (link.test(t)) {
                    return link.getProcessLink().apply(t);
                }
                return t;
            });
        }
        return s.collect(toList());
    }

    private class MetaLink<V> {
        final ProcessLink<V> processLink;
        final Predicate<V> test;

        private MetaLink(ProcessLink<V> processLink) {
            this.processLink = processLink;
            test = (tt) -> true;
        }

        private MetaLink(ProcessLink<V> processLink, Predicate<V> test) {
            this.processLink = processLink;
            this.test = test;
        }

        private ProcessLink<V> getProcessLink() {
            return processLink;
        }

        private boolean test(V v) {
            return test.test(v);
        }
    }
}
