package com.mycompany.photostorage.entity;
// Generated 2017-04-27 12:42:40 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Tag generated by hbm2java
 */
@Entity
@Table(name="tag"
    ,catalog="photokeeper"
)
/**
 * Class representing tag
 */
public class Tag  implements java.io.Serializable {


     private Integer idt;
     private Photo photo;
     private String value;

    /**
     * Basic constructor
     */
    public Tag() {
    }

    /**
     * Constructor assigning photo
     * @param photo photo having this tag
     */
    public Tag(Photo photo) {
        this.photo = photo;
    }
    
    /**
     * Constructor assigning values
     * @param photo photo having this tag
     * @param value tag text
     */
    public Tag(Photo photo, String value) {
       this.photo = photo;
       this.value = value;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idt", unique=true, nullable=false)
    public Integer getIdt() {
        return this.idt;
    }
    
    public void setIdt(Integer idt) {
        this.idt = idt;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Photo_idp", nullable=false)
    public Photo getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    
    @Column(name="value", length=45)
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }




}


