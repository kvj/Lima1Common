package org.kvj.bravo7.util;

/**
 * Created by kvorobyev on 4/26/15.
 */
public class DataUtil {

    public static class Wrapper<T> {

        private T data = null;

        public Wrapper(T data) {
            this.data = data;
        }

        public T data() {
            return data;
        }

        public void data(T data) {
            this.data = data;
        }
    }
}
