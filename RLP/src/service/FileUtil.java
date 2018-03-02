/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.FileStore;
import domain.UploadItem;
import java.io.File;
import java.util.List;

/**
 *
 * @author Vadya
 */
public interface FileUtil {

    public long saveFile(UploadItem uI, String usr);

    public File getFileFromStore(long id);
    
    public FileStore getFileStore(long id);

    public boolean deleteFileStore(long id);
    
    public List<FileStore> getAllFileFromStore();

    public List<FileStore> getAllFileFromStore(String usr);
}
