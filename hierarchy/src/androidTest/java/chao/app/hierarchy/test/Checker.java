package chao.app.hierarchy.test;

import org.junit.Assert;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public class Checker {

    private boolean flag = false;

    public void start() {
        flag = false;
    }

    public void reset() {
        flag = false;
    }

    public void call () {
        flag = true;
    }

    public void checkout() {
        Assert.assertTrue(flag);
    }
}
