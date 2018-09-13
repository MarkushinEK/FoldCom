package com.dataSet;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "folder_folder")
public class RelationshipEntity implements Serializable {

    @Column(name = "subfolders_id")
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subfolderId;

    @Column(name = "folder_id")
    private long folderId;

    RelationshipEntity() {}

    public long getSubfolderId() {
        return subfolderId;
    }

    public void setSubfolderId(long subfolderId) {
        this.subfolderId = subfolderId;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }
}
