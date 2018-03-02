/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.Dao;
import domain.FileStore;
import domain.UploadItem;
import domain.WordFromText;
import domain.security.User;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Vadya
 */
//!!!! Хрень, нада весь цей функціонал перенести на клас ФайлСторе
public class FileUtilImpl implements FileUtil {

    @Autowired
    private Dao daoI;
    @Autowired
    private WordAndTextServiceImpl wordAndTextServiceImpl;
    @Value("${fileutil.folder}")
    private String rootFolder;

    @Override
    public long saveFile(UploadItem uI, String usr) {
        FileStore fs = new FileStore();
        // создаю времений файлсторе, щоб получить ід, під ним і зберігатимем файл на диску
        daoI.saveOrUpdate(fs);
        String pathFile = rootFolder + String.valueOf(fs.getId()) + ".txt";
        File physicalFile = new File(pathFile);
        try {
            uI.getFileData().transferTo(physicalFile);
        } catch (IOException ex) {
            Logger.getLogger(FileUtilImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } catch (IllegalStateException ex) {
            Logger.getLogger(FileUtilImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        fs.setName(uI.getName());
        fs.setFile(pathFile);
        if (!usr.isEmpty()) {
            User user = (User) daoI.getById(User.class.getName(), usr);
            if (user != null) {
                fs.setUser(user);
            }

        }
        fs.setDateCr(new Date().getTime());
        daoI.saveOrUpdate(fs);
        return fs.getId();
    }

    @Override
    public File getFileFromStore(long id) {

        FileStore fs = (FileStore) daoI.getById(FileStore.class.getName(), id);
        File file = new File(fs.getFile());
        return file;
    }

    @Override
    public FileStore getFileStore(long id) {
        FileStore fs = (FileStore) daoI.getById(FileStore.class.getName(), id);
        return fs;
    }

    @Override
    public List<FileStore> getAllFileFromStore() {
        return (List<FileStore>) daoI.getAll(FileStore.class);
    }

    @Override
    public List<FileStore> getAllFileFromStore(String usr) {
        //  return (List<FileStore>) daoI.getAll(FileStore.class);
        String[] values = {usr};
        return (List<FileStore>) daoI.getByQuery("from FileStore where userId=?", values);
    }

    @Override
    public boolean deleteFileStore(long id) {

        boolean flACL = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = null;
        if (auth != null) {
            name = auth.getName();
            List<GrantedAuthority> authL = (List<GrantedAuthority>) auth.getAuthorities();
            if (authL != null) {
                for (GrantedAuthority au : authL) {
                    if ("ROLE_ADMIN".equals(au.getAuthority())) {
                        flACL = true;
                    }

                }
            }
        }

        FileStore fs = getFileStore(id);
        System.err.println("++++++++++++++++" + fs);
        if ((fs != null) && ((flACL) || (fs.getUser().getUsername().equals(name)))) {
            //delete all word from this text
            if (fs.isAnalyzed()) {

                WordFromText[] lwft = wordAndTextServiceImpl.getListFromSavedFile(fs.getId());
                for (WordFromText wft : lwft) {
                    daoI.delete(wft);
                }
            }
            File file = new File(fs.getFile());
            file.delete();
            daoI.delete(fs);
            return true;
        } else {
            return false;
        }

    }
}
