package chao.app.hierarchy;

import android.support.annotation.NonNull;
import java.util.List;

public class HierarchyBuilder<V> {

    private static final int TRAVERSAL_DEEP_FIRST = 1;

    private static final int TRAVERSAL_BREADTH_FIRST = 2;

    private int traversalType = TRAVERSAL_BREADTH_FIRST;

    FamilyEnum family;

    HierarchyFilter<V> filter;

    V mV;

    Class<V> mType;

    public HierarchyBuilder(V v) {
        mV = v;
    }

    public <T extends V> HierarchyBuilder(T value, Class<V> clazz) {
        mV = value;
        mType = clazz;
    }

    public HierarchyBuilder breadthFirst() {
        traversalType = TRAVERSAL_BREADTH_FIRST;
        return this;
    }

    public HierarchyBuilder<V> deepFirst() {
        traversalType = TRAVERSAL_DEEP_FIRST;
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
        return new HierarchyBuilder<>(build().root().value());
    }

    public HierarchyBuilder<V> parent() {
        return new HierarchyBuilder<>(build().parent().value());
    }

    @NonNull
    public Hierarchy<V> build() {
        if (mV == null) {
            return new HierarchyEmpty<>();
        }
        return new Hierarchy<>(this);
    }


    public void forEach(HierarchyAction<V> action) {
        HierarchyFamily<V> family = build().getFamily();
        for (HierarchyNode<V> hierarchyNode : family) {
            if (action.action(hierarchyNode)) {
                break;
            }
        }
    }

    public HierarchyNode<V> firstNode() {
        return allNodes().get(0);
    }

    public List<HierarchyNode<V>> allNodes() {
        return build().getFamily().members(new HierarchyFilter.AllAllowFilter<V>());
    }
}