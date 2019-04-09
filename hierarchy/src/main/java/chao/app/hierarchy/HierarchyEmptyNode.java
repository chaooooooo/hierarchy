package chao.app.hierarchy;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public class HierarchyEmptyNode<V> extends AbstractHierarchyNode<V> {

    public HierarchyEmptyNode() {
        super(null);
    }

    @Override
    public V parent() {
        return null;
    }

    @NonNull
    @Override
    public List<V> children() {
        return new ArrayList<>();
    }

    @Override
    public V childAt(int index) {
        return null;
    }
}
