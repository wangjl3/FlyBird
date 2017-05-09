package com.hebut.flybird.sys.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangJL on 2017/5/5.
 */
@Table(name = "t_flybird_user")
@Entity
public class User extends BaseEntity<Long>{
    public enum Gender {
        unkown(0), male(1), female(2);

        private int info;

        private static Map<Integer, Gender> map = Maps.newHashMap();

        static {
            for (Gender gender : Gender.values()) {
                map.put(gender.info, gender);
            }
        }

        Gender(int info) {
            this.info = info;
        }

        public int getInfo() {
            return info;
        }

        @Override
        public String toString() {
            return Integer.toString(info);
        }

        public static Gender valueOf(int info) {
            return map.get(info);
        }
    }

    public enum Status {
        normal(0), locked(1);

        private int info;
        private static Map<Integer, Status> map = Maps.newHashMap();

        static {
            for (Status status : Status.values()) {
                map.put(status.info, status);
            }
        }

        Status(int info) {
            this.info = info;
        }

        public int getInfo() {
            return info;
        }

        public static Status valueOf(int info) {
            return map.get(info);
        }

    }

    @Column(unique = true, nullable = false)
    private String account;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "salt", length = 50)
    private String salt;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String nickname;        //姓名
    @Enumerated(EnumType.ORDINAL)
    private Gender gender = Gender.unkown;          //性别
    private String headImage;  //头像链接
    private String country;         //国籍
    private String email;           //邮箱
    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.normal;          //状态
    private String watchword; //签名
    @Transient
    private String remark; //备注信息
    @Transient
    private Integer uplineStatus; //用户登录状态

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "birthday")
    private Date birthday;          //出生日期

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "registerTime")
    private Date registerTime;      //注册日期

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updateTime")
    private Date updateTime = new Date(); //最后更新时间

    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JSONField(serialize = false)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getRegisterTime() {
        if (isNew()) {
            registerTime = new Date();
        }
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @JSONField(serialize = false)
    public Gender getGender() {
        return gender;
    }

    @JSONField(serialize = false)
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @JSONField(name = "gender")
    public int getGenderCode() {
        return gender.getInfo();
    }

    @JSONField(name = "gender")
    public void setGenderCode(int genderCode) {
        gender = Gender.valueOf(genderCode);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JSONField(serialize = false)
    public Status getStatus() {
        return status;
    }

    @JSONField(serialize = false)
    public void setStatus(Status status) {
        this.status = status;
    }

    @JSONField(name = "status")
    public int getStatusCode() {
        if (status == null) {
            return Status.locked.getInfo();
        }
        return status.getInfo();
    }

    @JSONField(name = "status")
    public void setStatusCode(int statusCode) {
        status = Status.valueOf(statusCode);
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getWatchword() {
        return watchword;
    }

    public void setWatchword(String watchword) {
        this.watchword = watchword;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUplineStatus() {
        return uplineStatus;
    }

    public void setUplineStatus(Integer uplineStatus) {
        this.uplineStatus = uplineStatus;
    }
}
