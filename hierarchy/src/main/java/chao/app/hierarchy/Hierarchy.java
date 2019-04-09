package chao.app.hierarchy;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class Hierarchy<V> implements IHierarchy<V>{

    private HierarchyNode<V> mNode;

    private HierarchyBuilder<V> mBuilder;

    private HierarchyFamilyFactory<V> mFamilyFactory;

    Hierarchy(HierarchyBuilder<V> builder) {
        init(builder);
    }

    private void init(HierarchyBuilder<V> builder) {
        mBuilder = builder;
        mNode = mBuilder.mNode;
        mFamilyFactory = mBuilder.mFamilyFactory;
    }

    public static <T> HierarchyBuilder<T> of(T t) {
        return new HierarchyBuilder<>(t);
    }

    public static <V, T extends V> HierarchyBuilder<V> of(T value, Class<V> clazz) {
        return new HierarchyBuilder<>(value, clazz);
    }


    public static <T> void registerHierarchy(Class<T> tClass, Class<? extends HierarchyNode<T>> nodeClass) {
        HierarchySettings.instance().mStrategy.registerHierarchy(tClass, nodeClass);
    }


    @NonNull
    @Override
    public HierarchyFamily<V> getFamily() {
        return new HierarchyFamily<>(new Itr<V>() {

            private List<V> members;

            private int index;
            @Override
            public void onCreate() {
                HierarchyFamily<V> family = family();
                members = family.members(mBuilder.filter);
                index = 0;
            }

            @Override
            public void onDestroy() {
                members.clear();
            }

            @Override
            public boolean hasNext() {
                return members != null && index < members.size();
            }

            @Override
            public V next() {
                return members.get(index++);
            }
        });
    }

    protected HierarchyFamily<V> family() {
        HierarchyFamily<V> family;
        switch (mBuilder.family) {
            case all:
                family = mFamilyFactory.all();
                break;
            case descendants:
                family = mFamilyFactory.descendants();
                break;
            case brothers:
                family = mFamilyFactory.brothers();
                break;
            case ancestors:
                family = mFamilyFactory.ancestors();
                break;
            case children:
                family = mFamilyFactory.children();
                break;
            default:
                family = mFamilyFactory.self();
        }
        return family;
    }


    @SuppressWarnings("unused")
    public V childAt(int index) {
        return mNode.childAt(index);
    }

}
