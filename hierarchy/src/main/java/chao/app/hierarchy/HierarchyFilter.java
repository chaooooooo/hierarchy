package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/6
 */
public interface HierarchyFilter<V> {
    boolean filter(V v);

    class AllAllowFilter<V> implements HierarchyFilter<V> {

        @Override
        public boolean filter(V hierarchyNode) {
            return true;
        }
    }
}
