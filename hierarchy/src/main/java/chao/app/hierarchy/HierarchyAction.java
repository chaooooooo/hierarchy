package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/6
 */
public interface HierarchyAction<V> {
    boolean action(HierarchyNode<V> t);
}
