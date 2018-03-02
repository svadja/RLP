/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import domain.FileStore;
import domain.UploadItem;
import domain.WordFromText;
import domain.TextFromArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.FileUtilImpl;
import service.WordAndTextServiceImpl;

/**
 *
 * @author Vadya
 */
@Controller
public class FileController {

    private static Logger log = Logger.getLogger(WelcomeController.class.getName());
    @Autowired
    private FileUtilImpl fileUtilImpl;
    @Autowired
    private WordAndTextServiceImpl wordAndTextServiceImpl;

    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public String getUploadForm(Model model) {
        // log.info(userService.getUserById("Vadim").getPassword());
        model.addAttribute("uploadItem", new UploadItem());
        model.addAttribute("fromTextArea", new TextFromArea());
        return "uploadfile";
    }

    @RequestMapping(value = "/guest/guestuploadtext", method = RequestMethod.GET)
    public String getGuestUploadForm(Model model) {
        // log.info(userService.getUserById("Vadim").getPassword());
        model.addAttribute("fromTextArea", new TextFromArea());
        return "guestuploadtext";
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public ModelAndView uploadFile(@ModelAttribute("uploadItem") UploadItem uploadItem, BindingResult result) throws FileNotFoundException, IOException {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }
            return new ModelAndView("redirect:uploadfile");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        long idnewfile = fileUtilImpl.saveFile(uploadItem, name);
        return new ModelAndView("redirect:myfileslist?showfile=" + idnewfile);
    }

    @RequestMapping(value = "/uploadtextfromarea", method = RequestMethod.POST)
    public ModelAndView uploadTextFromArea(@ModelAttribute("fromTextArea") TextFromArea fromTextArea, BindingResult result) throws FileNotFoundException, IOException {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }
            return new ModelAndView("redirect:uploadfile");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        long idnewfile = fileUtilImpl.saveFile(new UploadItem(fromTextArea.getName(), new MockMultipartFile("tempMockMF.txt", fromTextArea.getBody().getBytes())), name);
        return new ModelAndView("redirect:myfileslist?showfile=" + idnewfile);
    }

    @RequestMapping(value = "/analyzetextfromarea", method = RequestMethod.POST)
    public String analyzeTextFromArea(@ModelAttribute("fromTextArea") TextFromArea fromTextArea, Model model, BindingResult result) throws FileNotFoundException, IOException {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }
            return "uploadfile";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        WordFromText[] words = wordAndTextServiceImpl.analyzeFromText(fromTextArea.getBody());
        Arrays.sort(words);
        model.addAttribute("words", words);
        model.addAttribute("myworddic", wordAndTextServiceImpl.loadAllMyWords(name));
        return "listWordsJs";
    }

    @RequestMapping(value = "/guest/guestuploadtext", method = RequestMethod.POST)
    public String guestUploadText(@ModelAttribute("fromTextArea") TextFromArea fromTextArea, BindingResult result, Model model) throws FileNotFoundException, IOException {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }

            return "guestuploadtext";
        }
        WordFromText[] words = wordAndTextServiceImpl.analyzeFromText(fromTextArea.getBody());
        Arrays.sort(words);
        model.addAttribute("words", words);
        return "listWordsJs";
    }

//    @RequestMapping(value = "/f/{file_name}", method = RequestMethod.GET)
//    public void getFile(@PathVariable("file_name") String fileId, HttpServletResponse response) throws FileNotFoundException, IOException {
//        InputStream is = new FileInputStream(fileUtilImpl.getFileFromStore(Long.parseLong(fileId)));
//        IOUtils.copy(is, response.getOutputStream());
//        response.flushBuffer();
//    }
    @RequestMapping(value = "/f/{file_name}", method = RequestMethod.GET)
    public String showText(@PathVariable("file_name") String fileId, Model model) throws FileNotFoundException, IOException {
        FileStore fs = fileUtilImpl.getFileStore(Long.parseLong(fileId));
        String title = fs.getName();
        InputStream is = new FileInputStream(new File(fs.getFile()));
        String result = IOUtils.toString(is);
        model.addAttribute("textfile", result);
        model.addAttribute("titlefile", title);
        return "showtext";
    }

    @RequestMapping(value = "/fileslist", method = RequestMethod.GET)
    public String generateList(Model model) {
        List<FileStore> files = java.util.Collections.emptyList();
        files = fileUtilImpl.getAllFileFromStore();
        Collections.sort(files);
        model.addAttribute("files", files);
        return "listFiles";
    }

    @RequestMapping(value = "/myfileslist", method = RequestMethod.GET)
    public String generateMyList(Model model) {
        List<FileStore> files = java.util.Collections.emptyList();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        files = fileUtilImpl.getAllFileFromStore(name);
        Collections.sort(files);
        model.addAttribute("files", files);
        return "listMyFiles";
    }

    @RequestMapping(value = "/myfileslist", params = "showfile", method = RequestMethod.GET)
    public String generateMyOneFile(@RequestParam("showfile") long showfile, Model model) {
        List<FileStore> files = new ArrayList<FileStore>();
        FileStore fs = fileUtilImpl.getFileStore(showfile);
        files.add(fs);
        model.addAttribute("files", files);
        return "listMyFiles";
    }

    @RequestMapping(value = "/deletefile", params = "fileid", method = RequestMethod.GET)
    public String deleteFile(@RequestParam("fileid") long fileid, Model model) {
        fileUtilImpl.deleteFileStore(fileid);
        List<FileStore> files = java.util.Collections.emptyList();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        files = fileUtilImpl.getAllFileFromStore(name);
        Collections.sort(files);
        model.addAttribute("files", files);
        return "listMyFiles";
    }
}
