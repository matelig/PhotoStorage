package com.mycompany.photostorage.entity;
// Generated 2017-04-27 12:42:40 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Photo generated by hbm2java
 */
@Entity
@Table(name="photo"
    ,catalog="photokeeper"
)
public class Photo  implements java.io.Serializable {


     private Integer idp;
     private Category category;
     private User user;
     private Date date;
     private int size;
     private String format;
     private String resolution;
     private String description;
     private byte[] miniature;
     private byte isArchivised;
     private String path;
     private Set<Device> devices = new HashSet<Device>(0);
     private Set<Tag> tags = new HashSet<Tag>(0);

    public Photo() {
    }

	
    public Photo(User user, Date date, int size, String format, String resolution, byte[] miniature, byte isArchivised, String path) {
        this.user = user;
        this.date = date;
        this.size = size;
        this.format = format;
        this.resolution = resolution;
        this.miniature = miniature;
        this.isArchivised = isArchivised;
        this.path = path;
    }
    public Photo(Category category, User user, Date date, int size, String format, String resolution, String description, byte[] miniature, byte isArchivised, String path, Set<Device> devices, Set<Tag> tags) {
       this.category = category;
       this.user = user;
       this.date = date;
       this.size = size;
       this.format = format;
       this.resolution = resolution;
       this.description = description;
       this.miniature = miniature;
       this.isArchivised = isArchivised;
       this.path = path;
       this.devices = devices;
       this.tags = tags;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idp", unique=true, nullable=false)
    public Integer getIdp() {
        return this.idp;
    }
    
    public void setIdp(Integer idp) {
        this.idp = idp;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Category_idc")
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="User_idu", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date", nullable=false, length=10)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="size", nullable=false)
    public int getSize() {
        return this.size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }

    
    @Column(name="format", nullable=false, length=50)
    public String getFormat() {
        return this.format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }

    
    @Column(name="resolution", nullable=false, length=45)
    public String getResolution() {
        return this.resolution;
    }
    
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    
    @Column(name="description", length=500)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="miniature", nullable=false)
    public byte[] getMiniature() {
        return this.miniature;
    }
    
    public void setMiniature(byte[] miniature) {
        this.miniature = miniature;
    }

    
    @Column(name="isArchivised", nullable=false)
    public byte getIsArchivised() {
        return this.isArchivised;
    }
    
    public void setIsArchivised(byte isArchivised) {
        this.isArchivised = isArchivised;
    }

    
    @Column(name="path", nullable=false, length=500)
    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="device_has_photo", catalog="photokeeper", joinColumns = { 
        @JoinColumn(name="Photo_idp", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="Device_idd", nullable=false, updatable=false) })
    public Set<Device> getDevices() {
        return this.devices;
    }
    
    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="photo")
    public Set<Tag> getTags() {
        return this.tags;
    }
    
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }




}


