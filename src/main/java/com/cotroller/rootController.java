package com.cotroller;

import com.dataSet.Folder;
import com.service.DBService;
import com.service.DBServiceImpl;
import com.validator.NameValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class rootController {

    @RequestMapping("/")
    public String getHome (Map<String, Object> model,
                           HttpSession httpSession) {
        DBService dbService = DBServiceImpl.instance();
        List<Folder> folders = (List<Folder>) dbService.getRootFolders();

        if (httpSession.getAttribute("folderForMove") != null) {
            model.put("folderName", ((Folder) httpSession.getAttribute("folderForMove")).getName());
            for (int i = 0; i < folders.size(); ++i) {
                if (folders.get(i).equals(httpSession.getAttribute("folderForMove"))) {
                    folders.remove(i);
                    break;
                }
            }
        }

        model.put("folders", folders);
        return "index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createFolder (@RequestParam("name") String name,
                                HttpSession httpSession) {
        DBService dbService = DBServiceImpl.instance();

        while (!NameValidator.nameCopyValidateForRootFolder(name)) {
            name = new StringBuilder(name).append("-").append("duplicate").toString();
        }

        Folder folder = new Folder(name, true);
        dbService.save(folder);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteFolder (@RequestParam("folder") long folderId) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderId);
        dbService.delete(folder);
        return "redirect:/";
    }

    @RequestMapping(value = "/renamepage", method = RequestMethod.POST)
    public String renamePageOpen (Map<String, Object> model,
                                @RequestParam("folder") long folderId) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderId);
        model.put("folder", folder);
        return "rename";
    }

    @RequestMapping(value = "/rename/{folderid}", method = RequestMethod.POST)
    public String renameFolder (@RequestParam("name") String name,
                                @PathVariable("folderid") long folderId) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderId);
        folder.setName(name);
        dbService.update(Folder.class.getName(), folder);
        return "redirect:/";
    }

    @RequestMapping(value = "/moveCondition", method = RequestMethod.POST)
    public String move (Map<String, Object> model,
                        @RequestParam("folder") long folderId,
                        HttpSession httpSession) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderId);
        httpSession.setAttribute("folderForMove", folder);
        httpSession.setAttribute("folderName", folder.getName());
        model.put("folder", folder);
        return "redirect:/";
    }

    @RequestMapping(value = "/move", method = RequestMethod.GET)
    public String move (HttpSession httpSession) {

        DBService dbService = DBServiceImpl.instance();
        Folder moveFolder = (Folder) httpSession.getAttribute("folderForMove");
        dbService.deleteRelation(moveFolder.getId());
        moveFolder.setRootFolder(true);
        dbService.update(Folder.class.getName(), moveFolder);
        httpSession.invalidate();

        return "redirect:/";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String calcelMoveCondition (HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }


}
