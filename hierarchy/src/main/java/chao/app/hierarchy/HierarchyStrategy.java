package chao.app.hierarchy;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class HierarchyStrategy {

    private Map<Class<?>, Class<? extends HierarchyNode<?>>> mStrategyNodes = new HashMap<>();


    public HierarchyStrategy() {
    }


    <V> HierarchyNode<V> findTypeNode(V v, Class<V> type, HierarchyNodeFactory<V> factory) {
        if (v == null) {
            return factory.getOrCreateHierarchyEmptyNode();
        }
        Class tClazz = type;
        Class<? extends HierarchyNode<?>> nClass = null;
        if (tClazz == null) {
            tClazz = v.getClass();
            tClazz.getSuperclass();

            Class clazz = tClazz;
            while (clazz != null) {
                nClass = mStrategyNodes.get(clazz);
                if (nClass != null) {
                    break;
                }
                clazz = clazz.getSuperclass();
            }
        } else {
            nClass = mStrategyNodes.get(tClazz);
        }

        if (nClass == null) {
            return factory.getOrCreateHierarchyEmptyNode();
        }

        checkType(v, nClass);


        HierarchyNode hierarchyNode = null;
        Constructor<?>[] constructors = nClass.getDeclaredConstructors();
        for (Constructor<?> constructor: constructors) {
            constructor.setAccessible(true);
            Class<?>[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length != 1) {
                continue;
            }
            if (!(paramTypes[0].isAssignableFrom(v.getClass()))) {
                continue;
            }
            try {
                hierarchyNode = (HierarchyNode) constructor.newInstance(v);
            } catch (Throwable e) {
                return factory.getOrCreateHierarchyEmptyNode();
            }
        }

        return hierarchyNode;
    }

    private <T> void checkType(T t, Class clazz) {
        ParameterizedType parameterizedType;

        Type type = clazz.getGenericSuperclass();
        Type[] interfaceTypes = clazz.getGenericInterfaces();

        Type[] types = new Type[interfaceTypes.length + 1];
        types[0] = type;
        System.arraycopy(interfaceTypes, 0, types, 1, interfaceTypes.length);

        for (Type it: types) {
            if (!(it instanceof ParameterizedType)) {
                continue;
            }
            parameterizedType = (ParameterizedType) it;
            Type raw = parameterizedType.getRawType();
            if (raw == null) {
                continue;
            }
            if (!(raw instanceof Class)) {
                continue;
            }
            Class rawClazz = (Class) raw;
            if (!(HierarchyNode.class.isAssignableFrom(rawClazz))) {
                continue;
            }
            Type actual = parameterizedType.getActualTypeArguments()[0];
            if (actual instanceof Class) {
                Class<?> actualClazz = (Class) actual;
                if (actualClazz.isAssignableFrom(t.getClass())) {
                    return;
                }
            }
        }

        throw new HierarchyException("");
    }

    public <T> void registerHierarchy(Class<T> tClass, Class<? extends HierarchyNode<T>> nodeClass) {
        mStrategyNodes.put(tClass, nodeClass);
    }
}
