package chao.app.hierarchy;

import android.view.View;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class HierarchySettings {

    private static HierarchySettings sInstance;

    HierarchyStrategy mStrategy;

    public static HierarchySettings instance() {
        if (sInstance == null) {
            sInstance = new HierarchySettings();
        }
        return sInstance;
    }


    private HierarchySettings() {
        mStrategy = new HierarchyStrategy();
        mStrategy.registerHierarchy(View.class, ViewHierarchyNode.class);
    }


}
