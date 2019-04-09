package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public abstract class AbstractHierarchyNode<V> implements HierarchyNode<V> {

    private V mV;

    public AbstractHierarchyNode(V v) {
        mV = v;
    }

    @Override
    public V value() {
        return mV;
    }

}
