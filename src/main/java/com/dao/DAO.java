package com.dao;

import com.dataSet.Folder;
import com.dataSet.RelationshipEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DAO {

    Session session;

    public DAO(Session session) {
        this.session = session;
    }

    public void save(Object data) {
        session.save(data);
    }

    public List getRootFolders() {
        return session.createCriteria(Folder.class).
                add(Restrictions.eq("isRootFolder", true)).
                list();
    }

    public Object getFolderById(String className, long id) {
        return session.get(className, id);
    }

    public void update(String className, Object object) {
        session.update(className, object);
    }

    public void delete(Folder folder) {
        Object object = session.get(RelationshipEntity.class.getName(), folder.getId());
        if (object != null)
            session.delete(object);
        session.delete(folder);

    }

    public List getPathToFolder(Folder folder) {
        ArrayList<Folder> pathToFolder = new ArrayList<>();
        pathToFolder.add(folder);
        while(!folder.isRootFolder()) {
            RelationshipEntity relationshipEntity = (RelationshipEntity) session.get(RelationshipEntity.class.getName(), folder.getId());
            folder = (Folder) session.get(Folder.class.getName(), relationshipEntity.getFolderId());
            pathToFolder.add(folder);
        }
        Collections.reverse(pathToFolder);
        return pathToFolder;
    }

    public void deleteRealation(long subfolderId) {
        Object object = session.get(RelationshipEntity.class.getName(), subfolderId);
        if (object != null)
            session.delete(object);
    }

}
