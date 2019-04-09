package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public class HierarchyNodeFactory<V> {


    private HierarchyEmptyNode<V> emptyNode;

    private HierarchyStrategy strategy;

    private Class<V> valueType;

    public HierarchyNodeFactory(HierarchyStrategy strategy, Class<V> valueType) {
        this.strategy = strategy;
        this.valueType = valueType;
    }

    public HierarchyEmptyNode<V> getOrCreateHierarchyEmptyNode() {
        if (emptyNode == null) {
            emptyNode = new HierarchyEmptyNode<>();
        }
        return emptyNode;
    }

    public boolean isEmptyNode(HierarchyNode<V> node) {
        return node == emptyNode;
    }

    public HierarchyNode<V> getHierarchyNode(V v) {
        return strategy.findTypeNode(v, valueType, this);
    }

}
