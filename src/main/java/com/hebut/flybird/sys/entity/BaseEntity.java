package com.hebut.flybird.sys.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by WangJL on 2017/5/5.
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Persistable<ID> {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private ID id;

    public BaseEntity() {
    }

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public boolean isNew() {
        return null == this.getId();
    }

    public boolean equals(Object obj) {
        if(null == obj) {
            return false;
        } else if(this == obj) {
            return true;
        } else if(!this.getClass().equals(obj.getClass())){
            return false;
        } else {
            BaseEntity that = (BaseEntity)obj;
            return null != this.getId() && this.getId().equals(that.getId());
        }
    }
    public int hashCode() {
        byte hashCode = 17;
        int hashCode1 = hashCode + (null == this.getId()?0:this.getId().hashCode() * 31);
        return hashCode1;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
