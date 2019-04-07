package chao.app.hierarchy;

import java.util.Iterator;

public abstract class Itr<V> implements Iterator<HierarchyNode<V>> {
    public abstract void onCreate();
}