package chao.app.hierarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public class HierarchyFamilyFactory<V> {

    private HierarchyNode<V> mNode;

    private HierarchyNode<V> mRoot;

    private HierarchyNodeFactory<V> mNodeFactory;

    public HierarchyFamilyFactory(HierarchyNodeFactory<V> nodeFactory, HierarchyNode<V> node) {
        mNode = node;
        mNodeFactory = nodeFactory;
    }

    /**
     * 从树的根开始遍历
     *
     * @return family包含树的所有成员
     */
    public HierarchyFamily<V> all() {
        return new HierarchyFamily<>(new Itr<V>() {

            private ArrayList<V> all = new ArrayList<>();

            int index;
            int size;

            @Override
            public void onCreate() {
                index = -1;
                all.clear();
                V root = root().get();
                all.add(root);
                merge(root);
                size = all.size();
            }

            @Override
            public void onDestroy() {
                all.clear();
            }

            @Override
            public boolean hasNext() {
                return index + 1 < size;
            }

            @Override
            public V next() {
                index++;
                return all.get(index);
            }

            private void merge(V node) {
                List<V> children = mNodeFactory.getHierarchyNode(node).children();
                all.addAll(children);
                for (V child: children) {
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

            private List<V> nodes;

            @Override
            public void onCreate() {
                index = 0;
                nodes = mNode.children();
            }

            @Override
            public void onDestroy() {
                nodes = null;
            }

            @Override
            public boolean hasNext() {
                return index < nodes.size();
            }

            @Override
            public V next() {
                return nodes.get(index++);
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

            private ArrayList<V> descendants = new ArrayList<>();

            int index;
            int size;

            @Override
            public void onCreate() {
                index = -1;
                descendants.clear();
                descendants.add(mNode.value());
                merge(mNode.value());
                size = descendants.size();
            }

            @Override
            public void onDestroy() {
                index = -1;
                descendants.clear();
            }

            @Override
            public boolean hasNext() {
                return index + 1 < size;
            }

            @Override
            public V next() {
                index++;
                return descendants.get(index);
            }

            private void merge(V node) {
                List<V> children = mNodeFactory.getHierarchyNode(node).children();
                descendants.addAll(children);
                for (V child: children) {
                    merge(child);
                }
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public HierarchyFamily<V> ancestors() {

        return new HierarchyFamily<>(new Itr<V>() {

            private HierarchyNode<V> mPoint;

            @Override
            public void onCreate() {
                mPoint = mNode;
            }

            @Override
            public void onDestroy() {
                mPoint = null;
            }

            @Override
            public boolean hasNext() {
                return mPoint.parent() != null;
            }

            @Override
            public V next() {
                V parent = mPoint.parent();
                mPoint = mNodeFactory.getHierarchyNode(parent);
                return parent;
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
            private List<V> children;
            private int index = 0;

            @Override
            public void onCreate() {
                parent = mNodeFactory.getHierarchyNode(parent().get());
                index = 0;
                children = parent.children();
                if (!children.contains(mNode.value())) {
                    children.add(mNode.value());
                }
            }

            @Override
            public void onDestroy() {
                parent = null;
                children = null;
            }

            @Override
            public boolean hasNext() {
                return index < children.size();
            }

            @Override
            public V next() {
                return children.get(index++);
            }
        });
    }

    public SingletonFamily parent(){
        return new SingletonFamily(mNode.parent());
    }

    @SuppressWarnings("WeakerAccess")
    public SingletonFamily root() {
        if (mRoot == null) {
            V parent;
            HierarchyNode<V> node = mNode;
            while ((parent = node.parent()) != null) {
                node = mNodeFactory.getHierarchyNode(parent);
            }
            mRoot = mNodeFactory.getHierarchyNode(node.value());
        }
        return new SingletonFamily(mRoot.value());
    }

    public HierarchyFamily<V> self() {
        return new SingletonFamily(mNode.value());
    }

    class SingletonFamily extends ListFamily {

        private V v;

        SingletonFamily(V v) {
            super(Collections.singletonList(v));
            this.v = v;
        }

        public V get() {
            return v;
        }
    }

    private class ListFamily extends HierarchyFamily<V> {

        ListFamily(List<V> list) {
            super(new ListItr(list));
        }
    }

    private class ListItr extends Itr<V> {

        private int index = 0;

        private List<V> mList;

        public ListItr(List<V> vlist) {
            if (vlist == null) {
                mList = new ArrayList<>();
            } else {
                mList = new ArrayList<>(vlist);
            }
        }

        @Override
        public void onCreate() {
            index = 0;
        }

        @Override
        public void onDestroy() {
            mList.clear();
        }

        @Override
        public boolean hasNext() {
            return index < mList.size();
        }

        @Override
        public V next() {
            return mList.get(index++);
        }
    }
}
