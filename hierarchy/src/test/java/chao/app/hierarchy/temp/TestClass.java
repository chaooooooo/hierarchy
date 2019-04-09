package chao.app.hierarchy.temp;

import chao.app.hierarchy.HierarchyNode;
import chao.app.hierarchy.ViewHierarchyNode;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.junit.Test;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class TestClass {

    @Test
    public void testGetGenericSuperclass() {
        Class c = ViewHierarchyNode.class;
        Type t = c.getGenericSuperclass();
        System.out.println(t);

        Type[] its = c.getGenericInterfaces();

        for (Type it: its) {
            if (!(it instanceof ParameterizedType)) {
                continue;
            }
            ParameterizedType parameterizedType = (ParameterizedType) it;
            Type ownType = parameterizedType.getOwnerType();
            if (ownType instanceof HierarchyNode) {
                return;
            }
            Type[] types = parameterizedType.getActualTypeArguments();
            System.out.println(Arrays.toString(types));

        }
    }
}
