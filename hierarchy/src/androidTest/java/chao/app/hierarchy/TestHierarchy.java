package chao.app.hierarchy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.view.LayoutInflater;
import android.view.View;
import org.junit.Test;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class TestHierarchy {

    @Test
    public void test() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        LayoutInflater inflater = LayoutInflater.from(appContext);

        View view = new View(appContext);
        Hierarchy.of(view);
    }
}
