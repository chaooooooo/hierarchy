package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/6
 */
public class HierarchyEmpty<V> implements IHierarchy<V> {

    private HierarchyFamily<V> emptyFamily;

    private HierarchyFamily<V> emptyFamily() {
        if (emptyFamily == null) {

            emptyFamily = new HierarchyFamily<>(new Itr<V>() {
                @Override
                public void onCreate() {

                }

                @Override
                public void onDestroy() {

                }

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public V next() {
                    return null;
                }
            });
        }
        return emptyFamily;
    }

    @Override
    public HierarchyFamily<V> getFamily() {
        return emptyFamily();
    }
}
