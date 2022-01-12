package util;

import java.util.Arrays;
import java.util.Objects;

public class GenericList<T> {

    private T[] ts;

    @SafeVarargs
    public GenericList(T... ts) {
        this.ts = ts;
    }

    public boolean add(T t) {
        int length = this.ts.length;
        if (length == Integer.MAX_VALUE) return false;
        this.ts = Arrays.copyOf(ts, length + 1);
        this.ts[length] = t;
        return true;
    }

    @SafeVarargs
    public final boolean add(T t, T... ts) {
        if (!add(t)) return false;

        for (T t1 : ts) {
            if (!add(t1)) return false;
        }

        return true;
    }

    public boolean addAll(T[] ts) {
        for (T t : ts) {
            if (!add(t)) return false;
        }

        return true;
    }

    public T get(int index) {
        return this.ts[index];
    }

    public int size() {
        return this.ts.length;
    }

    public T remove(int index) {
        if (index < 0 || index >= this.ts.length)
            return null;

        T t = this.ts[index];
        System.arraycopy(ts, index + 1, this.ts, index, this.ts.length - 1 - index);
        this.ts = Arrays.copyOf(ts, this.ts.length - 1);

        return t;
    }

    public boolean contains(T t) {
        for (T t1 : this.ts) {
            if (Objects.equals(t1, t))
                return true;
        }

        return false;
    }

    public int indexOf(T t) {
        for (int i = 0; i < ts.length; i++) {
            if (Objects.equals(ts[i], t))
                return i;
        }
        return -1;
    }

    public T[] toArray() {
//        return Arrays.copyOf(ts, ts.length);
        return this.ts;
    }

    public void clear() {
        setAll();
    }

    @SafeVarargs
    private void setAll(T... ts) {
        this.ts = ts;
    }
}
