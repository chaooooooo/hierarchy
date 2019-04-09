package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/7
 */
public interface HierarchyResultAction<V, R> {
    boolean action(V v, HierarchyResult<R> result);
}
