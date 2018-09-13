package com.dataSet;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder")
public class Folder implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private boolean isRootFolder;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Folder> subfolders = new ArrayList<>();

    @ManyToOne()
    private Folder root;

    public Folder() {}

    public Folder(String name, boolean isRootFolder) {
        this.name = name;
        this.isRootFolder = isRootFolder;
    }

    public void addSubfolder(Folder folder) {
        subfolders.add(folder);
    }

    public boolean equals(Object folder) {
        if (folder instanceof Folder)
            return this.id == ((Folder) folder).getId();
        return false;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRootFolder() {
        return isRootFolder;
    }

    public void setRootFolder(boolean rootFolder) {
        isRootFolder = rootFolder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Folder> getSubfolders() {
        return subfolders;
    }

    public void setSubfolders(List<Folder> subfolders) {
        this.subfolders = subfolders;
    }
}
