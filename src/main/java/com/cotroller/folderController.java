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
public class folderController {

    @RequestMapping(value = "/folder", method = RequestMethod.POST)
    public String getFolder(@RequestParam("folder") long folderId) {
        return "redirect:/folder/" + folderId;
    }

    @RequestMapping(value = "/folder/{rootid}", method = RequestMethod.GET)
    public String createFolder(Map<String, Object> model,
                               HttpSession httpSession,
                               @PathVariable("rootid") long rootId) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), rootId);

        List<Folder> pathToFolder = (List<Folder>) dbService.getPathToFolder(folder);
        List<Folder> folders = folder.getSubfolders();

        if (httpSession.getAttribute("folderForMove") != null) {
            model.put("folderName", ((Folder) httpSession.getAttribute("folderForMove")).getName());
            for (int i = 0; i < folders.size(); ++i) {
                if (folders.get(i).equals(httpSession.getAttribute("folderForMove"))) {
                    folders.remove(i);
                    break;
                }
            }
        }

        model.put("path", pathToFolder);
        model.put("folders", folders);
        model.put("folderid", rootId);
        return "index";
    }

    @RequestMapping(value = "/folder/{rootid}/create", method = RequestMethod.POST)
    public String createFolder(@RequestParam("name") String name,
                               @PathVariable("rootid") long rootId) {

        while (!NameValidator.nameCopyValidateForAnotherFolder(name, rootId)) {
            name = new StringBuilder(name).append("-").append("duplicate").toString();
        }

        DBService dbService = DBServiceImpl.instance();
        Folder folder = new Folder(name, false);
        Folder rootFolder = (Folder) dbService.getFolderById(Folder.class.getName(), rootId);
        rootFolder.addSubfolder(folder);
        dbService.save(folder);
        dbService.update(Folder.class.getName(), rootFolder);

        return "redirect:/folder/{rootid}";
    }

    @RequestMapping(value = "/folder/{rootid}/delete", method = RequestMethod.POST)
    public String deleteFolder(@RequestParam("folder") long folderIdForDelete) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderIdForDelete);
        dbService.delete(folder);
        return "redirect:/folder/{rootid}";
    }

    @RequestMapping(value = "/folder/{rootid}/renamepage", method = RequestMethod.POST)
    public String renamePageOpen(Map<String, Object> model,
                                 @RequestParam("folder") long folderId) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderId);
        model.put("folder", folder);
        return "rename";
    }

    @RequestMapping(value = "/folder/{rootid}/rename/{folderid}", method = RequestMethod.POST)
    public String renameFolder(@RequestParam("name") String name,
                               @PathVariable("folderid") long folderId) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderId);
        folder.setName(name);
        dbService.update(Folder.class.getName(), folder);
        return "redirect:/folder/{rootid}";
    }

    @RequestMapping(value = "/folder/{rootid}/moveCondition", method = RequestMethod.POST)
    public String move(Map<String, Object> model,
                       @RequestParam("folder") long folderId,
                       HttpSession httpSession) {
        DBService dbService = DBServiceImpl.instance();
        Folder folder = (Folder) dbService.getFolderById(Folder.class.getName(), folderId);
        httpSession.setAttribute("folderForMove", folder);
        model.put("folder", folder);
        return "redirect:/folder/{rootid}";
    }

    @RequestMapping(value = "/folder/{rootid}/move", method = RequestMethod.GET)
    public String move(HttpSession httpSession,
                       @PathVariable("rootid") long rootId) {

        DBService dbService = DBServiceImpl.instance();
        Folder moveFolder = (Folder) httpSession.getAttribute("folderForMove");
        moveFolder.setRootFolder(false);
        dbService.deleteRelation(moveFolder.getId());
        Folder moveFolderTo = (Folder) dbService.getFolderById(Folder.class.getName(), rootId);
        moveFolderTo.addSubfolder(moveFolder);
        dbService.update(Folder.class.getName(), moveFolderTo);

        httpSession.invalidate();

        return "redirect:/folder/{rootid}";

    }
}
