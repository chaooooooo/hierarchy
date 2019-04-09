package chao.app.hierarchy;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HierarchyFamily<V> implements Iterable<V> {

    private Itr<V> itr;

    HierarchyFamily(Itr<V> itr) {
        this.itr = itr;
    }

    @NonNull List<V> members(HierarchyFilter<V> filter) {
        if (filter == null) {
            filter = new HierarchyFilter.AllAllowFilter<>();
        }
        List<V> members = new ArrayList<>();
        for (V node: this) {
            if (filter.filter(node)) {
                members.add(node);
            }
        }
        return members;
    }

    @NonNull
    @Override
    public Iterator<V> iterator() {
        itr.onCreate();
        return new Iterator<V>() {
            @Override
            public boolean hasNext() {
                boolean hasNext = itr.hasNext();
                if (!hasNext) {
                    itr.onDestroy();
                }
                return hasNext;
            }

            @Override
            public V next() {
                return itr.next();
            }
        };
    }
}