package com.service;

import com.dataSet.Folder;

import java.util.List;

public interface DBService {

    void save(Object data);

    void update(String entity, Object object);

    void delete(Folder folder);

    List getRootFolders();

    Object getFolderById(String className, long id);

    List getPathToFolder(Folder folder);

    void deleteRelation(long id);

}
