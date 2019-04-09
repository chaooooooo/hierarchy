package chao.app.hierarchy.impl;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import chao.app.hierarchy.AbstractHierarchyNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public class ViewNode extends AbstractHierarchyNode<View> {

    public ViewNode(View view) {
        super(view);
    }


    @Override
    public View parent() {
        ViewParent parent = value().getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }

    @NonNull
    @Override
    public List<View> children() {
        List<View> children = new ArrayList<>();
        if (value() instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) value();
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                children.add(vg.getChildAt(i));
            }
        }
        return children;
    }

    @Override
    public View childAt(int index) {
        if (value() instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) value();
            return vg.getChildAt(index);
        }
        return null;
    }
}
