package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/6
 */
public class HierarchyEmptyNode<V> extends AbstractHierarchyNode<V> {

    private HierarchyEmptyNode emptyNode;

    public HierarchyEmptyNode() {
        super(null);
    }

    @Override
    public HierarchyNode<V> parent() {
        return empty();
    }

    @Override
    public HierarchyNode<V>[] children() {
        return new HierarchyNode[0];
    }

    @Override
    public HierarchyNode<V> childAt(int index) {
        return empty();
    }

    private HierarchyNode<V> empty() {
        if (emptyNode == null) {
            emptyNode = new HierarchyEmptyNode<>();
        }
        return emptyNode;
    }
}
