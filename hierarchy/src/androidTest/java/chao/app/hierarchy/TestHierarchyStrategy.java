package chao.app.hierarchy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.widget.LinearLayout;
import org.junit.Test;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class TestHierarchyStrategy {

    @Test
    public void testGetNodeType() {

        Context appContext = InstrumentationRegistry.getTargetContext();

        HierarchyStrategy strategy = new HierarchyStrategy();
        strategy.registerHierarchy(View.class, ViewHierarchyNode.class);

        View view = new View(appContext);
        HierarchyNode node = strategy.findTypeNode(view, View.class);
        assert node != null;

        LinearLayout linearLayout = new LinearLayout(appContext);
        node = strategy.findTypeNode(linearLayout, View.class);
        assert  node != null;
    }

}
