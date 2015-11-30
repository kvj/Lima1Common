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
        return add(listener, false);
    }

    public boolean add(T listener, boolean top) {
        if (null == listener) {
            return false;
        }
        synchronized (lock) {
            if (listeners.contains(listener)) { // Already there
                return false;
            }
            if (top)
                listeners.add(0, listener);
            else
                listeners.add(listener);
            onAdd(listener);
            return true;
        }
    }

    public boolean remove(T listener) {
        if (null == listener) {
            return true;
        }
        synchronized (lock) {
            if (!listeners.contains(listener)) { // There
                return false;
            }
            listeners.remove(listener);
            onRemove(listener);
            return true;
        }
    }

    public boolean clear() {
        synchronized (lock) {
            if (!listeners.isEmpty()) {
                for (T listener : listeners) { // $COMMENT
                    onRemove(listener);
                }
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

    protected void onAdd(T listener) {}
    protected void onRemove(T listener) {}
}
