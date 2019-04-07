package chao.app.hierarchy;

public class HierarchyResult<R> {

    private R mDefaultValue;

    private R mR;

    public void setResult(R r) {
        mR = r;
    }

    public HierarchyResult<R> defaultValue(R v) {
        mDefaultValue = v;
        return this;
    }

    public R result() {
        if (mR != null) {
            return mR;
        }
        return mDefaultValue;
    }
}