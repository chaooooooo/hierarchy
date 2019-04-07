package chao.app.hierarchy;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class Hierarchy<V> {

    private HierarchyNode<V> mT;

    private HierarchyNode<V> mPoint;

    private HierarchyNode<V> mRoot;

    private HierarchyBuilder<V> mBuilder;

    Hierarchy(){
        mBuilder = new HierarchyBuilder<>(null);
    }

    Hierarchy(HierarchyBuilder<V> builder) {
        mBuilder = builder;
        mT = settings().mStrategy.findTypeNode(mBuilder.mV, mBuilder.mType);
    }

    public static <T> HierarchyBuilder<T> of(T t) {
        return new HierarchyBuilder<>(t);
    }

    public static <V, T extends V> HierarchyBuilder<V> of(T value, Class<V> clazz) {
        return new HierarchyBuilder<>(value, clazz);
    }


    public static HierarchySettings settings() {
        return HierarchySettings.instance();
    }


    @NonNull HierarchyFamily<V> getFamily() {
        return new HierarchyFamily<>(new Itr<V>() {

            private List<HierarchyNode<V>> members;

            private int index;
            @Override
            public void onCreate() {
                HierarchyFamily<V> family = family();
                members = family.members(mBuilder.filter);
                index = 0;
            }

            @Override
            public boolean hasNext() {
                return members != null && index < members.size();
            }

            @Override
            public HierarchyNode<V> next() {
                return members.get(index++);
            }
        });
    }

    protected HierarchyFamily<V> family() {
        HierarchyFamily<V> family;
        switch (mBuilder.family) {
            case all:
                family = all();
                break;
            case descendants:
                family = descendants();
                break;
            case brothers:
                family = brothers();
                break;
            case ancestors:
                family = ancestors();
                break;
            case children:
                family = children();
                break;
            default:
                family = empty();
        }
        return family;
    }

    protected HierarchyFamily<V> empty() {
        return new HierarchyFamily<>(new Itr<V>() {
            @Override
            public void onCreate() {

            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public HierarchyNode<V> next() {
                return null;
            }
        });
    }

    /**
     * 从树的根开始遍历
     *
     * @return family包含树的所有成员
     */
    public HierarchyFamily<V> all() {
        return new HierarchyFamily<>(new Itr<V>() {

            private ArrayList<HierarchyNode<V>> all = new ArrayList<>();

            int index;
            int size;

            @Override
            public void onCreate() {
                index = -1;
                all.clear();
                HierarchyNode<V> root = root();
                all.add(root);
                merge(root);
                size = all.size();
            }

            @Override
            public boolean hasNext() {
                return index + 1 < size;
            }

            @Override
            public HierarchyNode<V> next() {
                index++;
                return all.get(index);
            }

            private void merge(HierarchyNode<V> node) {
                HierarchyNode<V>[] children = node.children();
                all.addAll(Arrays.asList(children));
                for (HierarchyNode<V> child: children) {
                    merge(child);
                }
            }
        });
    }

    /**
     * 所有直接子节点
     */
    public HierarchyFamily<V> children() {
        return new HierarchyFamily<>(new Itr<V>() {

            private int index;

            private HierarchyNode<V>[] nodes = mT.children();

            @Override
            public void onCreate() {
                index = 0;
            }

            @Override
            public boolean hasNext() {
                return index < nodes.length;
            }

            @Override
            public HierarchyNode<V> next() {
                index++;
                return nodes[index];
            }
        });
    }

    /**
     * 所有子孙节点,包括直接子node和子node的子node
     * 也包括自己
     *
     * @return 所有子孙节点
     */
    @SuppressWarnings("unused | WeakerAccess")
    public HierarchyFamily<V> descendants() {
        return new HierarchyFamily<>(new Itr<V>() {

            private ArrayList<HierarchyNode<V>> descendants = new ArrayList<>();

            int index;
            int size;

            @Override
            public void onCreate() {
                index = -1;
                descendants.clear();
                if (mT.value() != null) {
                    descendants.add(mT);
                }
                merge(mT);
                size = descendants.size();
            }

            @Override
            public boolean hasNext() {
                return index + 1 < size;
            }

            @Override
            public HierarchyNode<V> next() {
                index++;
                return descendants.get(index);
            }

            private void merge(HierarchyNode<V> node) {
                HierarchyNode<V>[] children = node.children();
                descendants.addAll(Arrays.asList(children));
                for (HierarchyNode<V> child: children) {
                    merge(child);
                }
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public HierarchyFamily<V> ancestors() {
        return new HierarchyFamily<>(new Itr<V>() {

            @Override
            public void onCreate() {
                mPoint = mT;
            }

            @Override
            public boolean hasNext() {
                return mPoint.parent() != null;
            }

            @Override
            public HierarchyNode<V> next() {
                mPoint = mPoint.parent();
                return mPoint;
            }
        });
    }

    /**
     * brothers 包含自己
     */
    @SuppressWarnings("unused | WeakerAccess")
    public HierarchyFamily<V> brothers() {
        return new HierarchyFamily<>(new Itr<V>() {
            private HierarchyNode<V> parent;
            private HierarchyNode<V>[] children;
            private int index = 0;

            @Override
            public void onCreate() {
                parent = parent();
                index = 0;
                if (parent == null) {
                    return;
                }
                children = parent.children();
            }

            @Override
            public boolean hasNext() {
                return parent != null && index < children.length;
            }

            @Override
            public HierarchyNode<V> next() {
                HierarchyNode<V> child = children[index];
                index ++;
                return child;
            }
        });
    }

    public HierarchyNode<V> parent(){
        HierarchyNode<V> parent = mT.parent();
        if (parent != null) {
            return parent;
        }
        return new HierarchyEmptyNode<>();
    }

    @SuppressWarnings("WeakerAccess")
    public HierarchyNode<V> root() {
        if (mRoot == null) {
            for (HierarchyNode<V> t: ancestors()) {
                if(t.parent() == null) {
                    mRoot = t;
                    break;
                }
            }
        }
        if (mRoot != null) {
            return mRoot;
        }
        return new HierarchyEmptyNode<>();
    }

    @SuppressWarnings("unused")
    public HierarchyNode<V> childAt(int index) {
        return mT.childAt(index);
    }

}
