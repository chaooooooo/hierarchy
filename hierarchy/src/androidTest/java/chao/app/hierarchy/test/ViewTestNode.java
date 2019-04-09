package chao.app.hierarchy.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;
import chao.app.hierarchy.AbstractHierarchyNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public class ViewTestNode extends AbstractHierarchyNode<Context> {

    public ViewTestNode() {
        super(null);
    }

    public ViewTestNode(TextView object) {
        super(null);
    }


    public ViewTestNode(Context context) {
        super(context);
    }

    @Override
    public Context parent() {
        return null;
    }

    @NonNull
    @Override
    public List<Context> children() {
        return new ArrayList<>();
    }

    @Override
    public Context childAt(int index) {
        return null;
    }
}
