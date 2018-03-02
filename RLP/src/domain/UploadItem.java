/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Vadya
 */

import org.springframework.web.multipart.MultipartFile;


public class UploadItem {

    private String name;
    private MultipartFile fileData;

    public UploadItem() {
    }

    public UploadItem(String name, MultipartFile fileData) {
        this.name = name;
        this.fileData = fileData;
    }

    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }
}
