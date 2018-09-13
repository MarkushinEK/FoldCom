package com.validator;

import com.dataSet.Folder;
import com.service.DBService;
import com.service.DBServiceImpl;

import java.util.List;

public class NameValidator {

    public static boolean nameCopyValidateForRootFolder(String name) {
        DBService dbService = DBServiceImpl.instance();
        List<Folder> rootFolders = (List<Folder>) dbService.getRootFolders();
        for (Folder folder:rootFolders) {
            if (folder.getName().equals(name)) return false;
        }
        return true;
    }

    public static boolean nameCopyValidateForAnotherFolder(String name, long rootId) {
        DBService dbService = DBServiceImpl.instance();
        Folder rootFolder = (Folder) dbService.getFolderById(Folder.class.getName(), rootId);
        for (Folder folder:rootFolder.getSubfolders()) {
            if (folder.getName().equals(name)) return false;
        }
        return true;
    }

}
