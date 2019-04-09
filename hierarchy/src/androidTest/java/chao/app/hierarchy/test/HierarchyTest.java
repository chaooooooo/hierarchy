package chao.app.hierarchy.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import chao.app.hierarchy.Hierarchy;
import chao.app.hierarchy.HierarchyAction;
import chao.app.hierarchy.HierarchyFilter;
import chao.app.hierarchy.HierarchyResult;
import chao.app.hierarchy.HierarchyResultAction;
import chao.app.hierarchy.R;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author qinchao
 * @since 2019/4/8
 */
public class HierarchyTest {

    private View view;

    private ViewGroup viewGroup;

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        LayoutInflater inflater = LayoutInflater.from(appContext);
        View root = inflater.inflate(chao.app.hierarchy.R.layout.test_hierarchy_layout, null, false);
        view = root.findViewById(R.id.ami_content);
        viewGroup = (ViewGroup) view;
    }

    @Test
    public void testHierarchyOf() {

        Hierarchy.of(view).descendants().forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                return false;
            }
        });

        Hierarchy.of(viewGroup, View.class).ancestors().forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                return false;
            }
        });

        Hierarchy.of(null).brothers().forEach(new HierarchyAction<Object>() {
            @Override
            public boolean action(Object t) {
                return false;
            }
        });

        Hierarchy.of(null, View.class).brothers().forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                return false;
            }
        });

        Assert.assertNull(Hierarchy.of(new Object(), Object.class).brothers().selfNode().value());

        boolean hasHierarchyException = false;
        try {
            Hierarchy.of(viewGroup).root().descendants().forEach(new HierarchyAction<ViewGroup>() {
                @Override
                public boolean action(ViewGroup t) {
                    Assert.assertNotNull(t);
                    return false;
                }
            });
        } catch (ClassCastException he) {
            hasHierarchyException = true;
        } catch (Throwable e) {
            //ignore
        }
        Assert.assertTrue(hasHierarchyException);


    }

    @Test
    public void testHierarchyRegister() {
        Hierarchy.registerHierarchy(Context.class, ViewTestNode.class);
        Hierarchy.of(view.getContext(), Context.class).brothers();
    }

    @Test
    public void testHierarchyFilter() {
        Assert.assertTrue(
            Hierarchy.of(view).root().descendants().filter(new HierarchyFilter<View>() {
                @Override
                public boolean filter(View view) {
                    return view instanceof TextView;
                }
            }).forEach(new HierarchyResultAction<View, Boolean>() {
                @Override
                public boolean action(View view, HierarchyResult<Boolean> result) {
                    result.setResult(true);
                    Assert.assertTrue(view instanceof TextView);
                    return false;
                }
            }).result(false)
        );

    }

    @Test
    public void testHierarchySingletonFamily() {
        Assert.assertNotNull(view.getParent());
        Assert.assertNotNull(Hierarchy.of(view).parent().selfNode().value());
        Assert.assertNotNull(Hierarchy.of(view).root());


        final Checker checker = new Checker();
        checker.start();
        Hierarchy
            .of(view)
            .root()
            .forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View view) {
                checker.call();
                return false;
            }
        });
        checker.checkout();
    }

    ArrayList<Integer> ids = new ArrayList<>();

    @Test
    public void testHierarchyTraversalDeep() {
        ids.add(R.id.plugins);
        ids.add(R.id.drawer_layout);
        ids.add(R.id.ami_content);
        ids.add(R.id.drawer_component_content);

        ids.add(R.id.ami_content_fps);
        ids.add(R.id.ami_content_app_info);
        ids.add(R.id.ami_content_app_display);

        ids.add(R.id.ui_list);
        ids.add(R.id.component);

        ids.add(R.id.drawer_plugins_tab_layout);
        ids.add(R.id.drawer_plugins_view_pager);
        ids.add(R.id.ami_useless_tip_view);

        Hierarchy.of(view).root().descendants().deep(2).forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View view) {
                Assert.assertTrue(ids.contains(view.getId()));
                ids.remove((Integer) view.getId());
                return false;
            }
        });
        Assert.assertEquals(0, ids.size());
    }


    @Test
    public void testHierarchyFamily() {
        Hierarchy.of(view).forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                Assert.assertEquals(view, t);
                return false;
            }
        });

        Hierarchy.of(view).descendants().forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                if (view == t) {
                    return false;
                }
                boolean result = Hierarchy.of(t).ancestors().forEach(new HierarchyResultAction<View, Boolean>() {
                    @Override
                    public boolean action(View t, HierarchyResult<Boolean> result) {
                        if (t == view) {
                            result.setResult(true);
                            return true;
                        }
                        return false;
                    }
                }).result(false);

                Assert.assertTrue(result);
                return false;
            }
        });

        Hierarchy.of(view).ancestors().forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                if (view == t) {
                    return false;
                }
                boolean result = Hierarchy.of(t).descendants().forEach(new HierarchyResultAction<View, Boolean>() {
                    @Override
                    public boolean action(View t, HierarchyResult<Boolean> result) {
                        if (t == view) {
                            result.setResult(true);
                            return true;
                        }
                        return false;
                    }
                }).result(false);

                Assert.assertTrue(result);
                return false;
            }
        });

        Hierarchy.of(view).brothers().forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                boolean result = Hierarchy.of(t).brothers().forEach(new HierarchyResultAction<View, Boolean>() {
                    @Override
                    public boolean action(View t, HierarchyResult<Boolean> result) {
                        if (t == view) {
                            result.setResult(true);
                            return true;
                        }
                        return false;
                    }
                }).result(false);

                Assert.assertTrue(result);
                return false;
            }
        });

        Assert.assertTrue(
            Hierarchy.of(view).root().brothers().forEach(new HierarchyResultAction<View, Boolean>() {
                @Override
                public boolean action(View t, HierarchyResult<Boolean> result) {
                    result.setResult(true);
                    Assert.assertNotNull(t);
                    return false;
                }
            }
            ).result(false)
        );


        Hierarchy.of(view).all().forEach(new HierarchyAction<View>() {
            @Override
            public boolean action(View t) {
                Assert.assertNotNull(t);
                return false;
            }
        });

        Assert.assertTrue(
            Hierarchy.of(view).parent().children().forEach(new HierarchyResultAction<View, Boolean>() {
                @Override
                public boolean action(View t, HierarchyResult<Boolean> result) {
                    if (view == t) {
                        result.setResult(true);
                    }
                    return false;
                }
            }).result()
        );

        Hierarchy.of(view)
            .children()
            .forEach(new HierarchyAction<View>() {
                @Override
                public boolean action(View t) {
                    Assert.assertEquals(Hierarchy.of(t).parent().selfNode().value(), view);
                    return false;
                }
            });
    }
}
