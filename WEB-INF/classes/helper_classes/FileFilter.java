package helper_classes;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter{

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(".class")) {
            return true;
        }
        return false;
    }
    
}
