package org.kvj.bravo7.util;

/**
 * Created by kvorobyev on 4/26/15.
 */
public class DataUtil {

    public static class Value<T> {

        private T data = null;

        public Value(T data) {
            this.data = data;
        }

        public T data() {
            return data;
        }

        public void data(T data) {
            this.data = data;
        }

        public boolean set() {
            return data != null;
        }
    }

    public interface Callback<T> {

        public boolean call(T value);
    }
}
