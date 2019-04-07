package hierarchy.app.chao.hierarchys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import chao.app.hierarchy.Hierarchy;
import chao.app.hierarchy.HierarchyAction;
import chao.app.hierarchy.HierarchyFilter;
import chao.app.hierarchy.HierarchyNode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_launcher);

        ViewGroup view = findViewById(R.id.drawer_layout);


        Hierarchy.of(view, View.class)
            .descendants()
            .filter(new HierarchyFilter<View>() {
                @Override
                public boolean filter(HierarchyNode<View> hierarchyNode) {
                    View value = hierarchyNode.value();

                    return value instanceof ViewGroup;
                }
            })
            .forEach(new HierarchyAction<View>() {
                @Override
                public boolean action(HierarchyNode<View> t) {
                    Log.i("qinchao", t.value().toString());
                    return false;
                }
            });

        Hierarchy.of(null).descendants().filter(new HierarchyFilter<Object>() {
            @Override
            public boolean filter(HierarchyNode<Object> hierarchyNode) {
                Log.i("qinchao", "filter: " + hierarchyNode.toString());
                return false;
            }
        }).forEach(new HierarchyAction<Object>() {
            @Override
            public boolean action(HierarchyNode<Object> t) {
                Log.i("qinchao", "action: " + t.toString());
                return false;
            }
        });
    }
}
