package chao.app.hierarchy;

public class HierarchyResult<Result> {

    private Result mResult;

    public void setResult(Result result) {
        mResult = result;
    }

    public Result result(Result defaultResult) {
        if (mResult != null) {
            return mResult;
        }
        return defaultResult;
    }

    public Result result() {
        return mResult;
    }
}