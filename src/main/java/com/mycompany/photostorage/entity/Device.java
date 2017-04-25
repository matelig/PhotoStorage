package com.mycompany.photostorage.entity;
// Generated 2017-04-25 22:04:09 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Device generated by hbm2java
 */
@Entity
@Table(name="device"
    ,catalog="photokeeper"
    , uniqueConstraints = {@UniqueConstraint(columnNames="idd"), @UniqueConstraint(columnNames="name")} 
)
public class Device  implements java.io.Serializable {


     private DeviceId id;
     private Typeofdevice typeofdevice;
     private String name;
     private String capacity;
     private String freeSpace;
     private byte isStoring;
     private Set<Photo> photos = new HashSet<Photo>(0);

    public Device() {
    }

	
    public Device(DeviceId id, Typeofdevice typeofdevice, String name, String capacity, String freeSpace, byte isStoring) {
        this.id = id;
        this.typeofdevice = typeofdevice;
        this.name = name;
        this.capacity = capacity;
        this.freeSpace = freeSpace;
        this.isStoring = isStoring;
    }
    public Device(DeviceId id, Typeofdevice typeofdevice, String name, String capacity, String freeSpace, byte isStoring, Set<Photo> photos) {
       this.id = id;
       this.typeofdevice = typeofdevice;
       this.name = name;
       this.capacity = capacity;
       this.freeSpace = freeSpace;
       this.isStoring = isStoring;
       this.photos = photos;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="idd", column=@Column(name="idd", unique=true, nullable=false) ), 
        @AttributeOverride(name="typeOfDeviceIdtod", column=@Column(name="TypeOfDevice_idtod", nullable=false) ) } )
    public DeviceId getId() {
        return this.id;
    }
    
    public void setId(DeviceId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TypeOfDevice_idtod", nullable=false, insertable=false, updatable=false)
    public Typeofdevice getTypeofdevice() {
        return this.typeofdevice;
    }
    
    public void setTypeofdevice(Typeofdevice typeofdevice) {
        this.typeofdevice = typeofdevice;
    }

    
    @Column(name="name", unique=true, nullable=false, length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="capacity", nullable=false, length=50)
    public String getCapacity() {
        return this.capacity;
    }
    
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    
    @Column(name="freeSpace", nullable=false, length=50)
    public String getFreeSpace() {
        return this.freeSpace;
    }
    
    public void setFreeSpace(String freeSpace) {
        this.freeSpace = freeSpace;
    }

    
    @Column(name="isStoring", nullable=false)
    public byte getIsStoring() {
        return this.isStoring;
    }
    
    public void setIsStoring(byte isStoring) {
        this.isStoring = isStoring;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="device_has_photo", catalog="photokeeper", joinColumns = { 
        @JoinColumn(name="Device_idd", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="Photo_idp", nullable=false, updatable=false) })
    public Set<Photo> getPhotos() {
        return this.photos;
    }
    
    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }




}

