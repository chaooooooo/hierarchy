package chao.app.hierarchy;

import android.support.annotation.NonNull;

public class HierarchyBuilder<V> {

    private static final int TRAVERSAL_DEEP_FIRST = 1;

    private static final int TRAVERSAL_BREADTH_FIRST = 2;

    final HierarchyNodeFactory<V> mNodeFactory;

    final HierarchyFamilyFactory<V> mFamilyFactory;

    private int traversalType = TRAVERSAL_BREADTH_FIRST;

    int traversalDeep = Integer.MAX_VALUE;

    FamilyEnum family = FamilyEnum.self;

    HierarchyFilter<V> filter;

    HierarchyNode<V> mNode;

    Class<V> mType;

    HierarchyBuilder(V v) {
        this(v, null);
    }

    <T extends V> HierarchyBuilder(T value, Class<V> clazz) {
        mType = clazz;
        mNodeFactory = new HierarchyNodeFactory<>(HierarchySettings.instance().mStrategy, mType);
        mNode = mNodeFactory.getHierarchyNode(value);
        mFamilyFactory = new HierarchyFamilyFactory<>(mNodeFactory, mNode);
    }

    public HierarchyBuilder breadthFirst() {
        traversalType = TRAVERSAL_BREADTH_FIRST;
        return this;
    }

    public HierarchyBuilder<V> deepFirst() {
        traversalType = TRAVERSAL_DEEP_FIRST;
        return this;
    }

    public HierarchyBuilder<V> deep(int deep) {
        traversalDeep = deep;
        return this;
    }

    public HierarchyBuilder<V> all() {
        family = FamilyEnum.all;
        return this;
    }

    public HierarchyBuilder<V> descendants() {
        family = FamilyEnum.descendants;
        return this;
    }

    public HierarchyBuilder<V> ancestors() {
        family = FamilyEnum.ancestors;
        return this;
    }

    public HierarchyBuilder<V> brothers() {
        family = FamilyEnum.brothers;
        return this;
    }

    public HierarchyBuilder<V> children() {
        family = FamilyEnum.children;
        return this;
    }

    public HierarchyBuilder<V> filter(HierarchyFilter<V> filter) {
        this.filter = filter;
        return this;
    }

    public HierarchyBuilder<V> root() {
        return new HierarchyBuilder<>(mFamilyFactory.root().get(), mType);
    }

    public HierarchyBuilder<V> parent() {
        return new HierarchyBuilder<>(mFamilyFactory.parent().get(), mType);
    }

    public HierarchyNode<V> selfNode() {
        return mNode;
    }

    @NonNull
    public IHierarchy<V> build() {
        if (mNode.value() == null) {
            return new HierarchyEmpty<>();
        }
        return new Hierarchy<>(this);
    }


    public void forEach(HierarchyAction<V> action) {
        HierarchyFamily<V> family = build().getFamily();
        for (V v: family) {
            if (action.action(v)) {
                break;
            }
        }
    }

    public <Result> HierarchyResult<Result> forEach(HierarchyResultAction<V, Result> action) {
        HierarchyFamily<V> family = build().getFamily();
        HierarchyResult<Result> result = new HierarchyResult<>();
        for (V v : family) {
            if (action.action(v, result)) {
                break;
            }
        }
        return result;
    }
}