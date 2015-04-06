package org.kvj.bravo7.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for organizing listeners to events
 * Created by kvorobyev on 3/13/15.
 */
public class Listeners<T> {

    public static interface ListenerEmitter<T> {
        public boolean emit(T listener);
    }

    private List<T> listeners = new ArrayList<>();
    private final Object lock = new Object();

    public boolean add(T listener) {
        synchronized (lock) {
            if (listeners.contains(listener)) { // Already there
                return false;
            }
            listeners.add(listener);
            return true;
        }
    }

    public boolean remove(T listener) {
        synchronized (lock) {
            if (!listeners.contains(listener)) { // There
                return false;
            }
            listeners.remove(listener);
            return true;
        }
    }

    public boolean clear() {
        synchronized (lock) {
            if (!listeners.isEmpty()) {
                listeners.clear();
                return true;
            }
        }
        return false;
    }

    public boolean emit(ListenerEmitter<T> emitter) {
        synchronized (lock) {
            for (T listener : listeners) { // $COMMENT
                if (!emitter.emit(listener)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int size() {
        return listeners.size();
    }
}
