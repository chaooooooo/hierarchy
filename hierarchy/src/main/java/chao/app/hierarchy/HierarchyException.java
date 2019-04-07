package chao.app.hierarchy;

/**
 * @author qinchao
 * @since 2019/4/5
 */
public class HierarchyException extends RuntimeException {

    public HierarchyException() {
        super();
    }

    public HierarchyException(String message) {
        super(message);
    }

    public HierarchyException(String message, Throwable e) {
        super(message, e);
    }
}
