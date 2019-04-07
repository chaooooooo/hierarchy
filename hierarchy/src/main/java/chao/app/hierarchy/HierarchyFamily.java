package chao.app.hierarchy;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HierarchyFamily<V> implements Iterable<HierarchyNode<V>> {

    private Itr<V> itr;

    HierarchyFamily(Itr<V> itr) {
        this.itr = itr;
    }

    @NonNull List<HierarchyNode<V>> members(HierarchyFilter<V> filter) {
        if (filter == null) {
            filter = new HierarchyFilter.AllAllowFilter<>();
        }
        List<HierarchyNode<V>> members = new ArrayList<>();
        for (HierarchyNode<V> node: this) {
            if (filter.filter(node)) {
                members.add(node);
            }
        }
        return members;
    }

    @NonNull
    @Override
    public Iterator<HierarchyNode<V>> iterator() {
        itr.onCreate();
        return new Iterator<HierarchyNode<V>>() {
            @Override
            public boolean hasNext() {
                return itr.hasNext();
            }

            @Override
            public HierarchyNode<V> next() {
                return itr.next();
            }
        };
    }
}