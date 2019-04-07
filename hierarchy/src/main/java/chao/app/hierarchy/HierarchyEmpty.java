package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/6
 */
public class HierarchyEmpty<V> extends Hierarchy<V> {

    public HierarchyEmpty() {
        super();
    }

    @Override
    protected HierarchyFamily<V> family() {
        return empty();
    }
}
