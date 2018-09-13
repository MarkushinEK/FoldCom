package com.service;

import com.config.DataConfig;
import com.dao.DAO;
import com.dataSet.Folder;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DBServiceImpl implements DBService {

    private volatile static DBService dbService;
    private SessionFactory sessionfactory;

    private DBServiceImpl() {
        sessionfactory = new DataConfig().getSessionFactory();
    }

    public static DBServiceImpl instance() {
        if (dbService == null)
            synchronized (DBServiceImpl.class) {
                if (dbService == null)
                    dbService = new DBServiceImpl();
            }
        return (DBServiceImpl) dbService;
    }

    @Override
    public void save(Object data) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        DAO dao = new DAO(session);
        dao.save(data);
        trx.commit();
        session.close();
    }

    @Override
    public void update(String className, Object object) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        DAO DAO = new DAO(session);
        DAO.update(className, object);
        trx.commit();
        session.close();
    }

    @Override
    public void delete(Folder folder) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        DAO DAO = new DAO(session);
        DAO.delete(folder);
        trx.commit();
        session.close();
    }

    @Override
    public List getRootFolders() {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        DAO dao = new DAO(session);
        List folders = dao.getRootFolders();
        trx.commit();
        session.close();
        return folders;
    }

    @Override
    public Folder getFolderById(String className, long id) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        DAO dao = new DAO(session);
        Folder folder = (Folder) dao.getFolderById(className, id);
        Hibernate.initialize(folder.getSubfolders());
        trx.commit();
        session.close();
        return folder;
    }

    @Override
    public List getPathToFolder(Folder folder) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        DAO dao = new DAO(session);
        List folders = dao.getPathToFolder(folder);
        trx.commit();
        session.close();
        return folders;
    }

    @Override
    public void deleteRelation(long id) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        DAO dao = new DAO(session);
        dao.deleteRealation(id);
        trx.commit();
        session.close();
    }

}
