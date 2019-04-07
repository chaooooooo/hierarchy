package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public abstract class AbstractHierarchyNode<V> implements HierarchyNode<V> {

    private V value;

    public AbstractHierarchyNode(V v) {
        this.value = v;
    }

    @Override
    public V value() {
        return value;
    }
}
