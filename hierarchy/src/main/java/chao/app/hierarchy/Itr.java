package chao.app.hierarchy;

import java.util.Iterator;

public abstract class Itr<V> implements Iterator<V> {
    public abstract void onCreate();

    public abstract void onDestroy();
}