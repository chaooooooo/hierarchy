package chao.app.hierarchy.impl;

import android.support.annotation.NonNull;
import chao.app.hierarchy.AbstractHierarchyNode;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qinchao
 * @since 2019/4/9
 */
public class FileNode extends AbstractHierarchyNode<File> {

    private List<File> mChildren;

    public FileNode(File file) {
        super(file);
        mChildren = new ArrayList<>();
        if (file == null || file.isFile()) {
            return;
        }
        mChildren.addAll(Arrays.asList(file.listFiles()));
    }

    @Override
    public File parent() {
        if (value() != null) {
            return value().getParentFile();
        }
        return null;
    }

    @NonNull
    @Override
    public List<File> children() {
        return mChildren;
    }

    @Override
    public File childAt(int index) {
        return mChildren.get(index);
    }
}
