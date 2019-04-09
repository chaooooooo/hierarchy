package chao.app.hierarchy;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public interface HierarchyNode<V> {

    V value();

    V parent();

    @NonNull
    List<V> children();

    V childAt(int index);

}
