package com.huiboapp.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
public class UserInfoEntity implements Serializable {

    private int sex;
    private String memberid;
    private String token;
    private String nickname;
    private String birthday;
    private String msisdn;
    private String loginname;
    private String realname;
    private String idnumber;
    private String identification_front;
    private String identification_back;
    private String photo;
    private String approvalstatus;
    private String approvememo;
    private String approvetime;
    private int balance;
    private String ackmsg;
    private String ackmsgid;
    private String result;
    private String reason;
    private List<CarList> platelist;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getIdentification_front() {
        return identification_front;
    }

    public void setIdentification_front(String identification_front) {
        this.identification_front = identification_front;
    }

    public String getIdentification_back() {
        return identification_back;
    }

    public void setIdentification_back(String identification_back) {
        this.identification_back = identification_back;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public String getApprovememo() {
        return approvememo;
    }

    public void setApprovememo(String approvememo) {
        this.approvememo = approvememo;
    }

    public String getApprovetime() {
        return approvetime;
    }

    public void setApprovetime(String approvetime) {
        this.approvetime = approvetime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAckmsg() {
        return ackmsg;
    }

    public void setAckmsg(String ackmsg) {
        this.ackmsg = ackmsg;
    }

    public String getAckmsgid() {
        return ackmsgid;
    }

    public void setAckmsgid(String ackmsgid) {
        this.ackmsgid = ackmsgid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<CarList> getPlatelist() {
        return platelist;
    }

    public void setPlatelist(List<CarList> platelist) {
        this.platelist = platelist;
    }

    public class CarList{
        private String plate;
        private String platecolor;
        private String id;
        private String autopay;
        private String license;
        private String vin;
        private String brandmodel;
        private String engineno;
        private String owner;
        private String registdate;
        private String cartype;
        private String usecharacter;
        private int inspectionexpire;
        private String approvalstatus;
        private String approvememo;

        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        public String getPlatecolor() {
            return platecolor;
        }

        public void setPlatecolor(String platecolor) {
            this.platecolor = platecolor;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAutopay() {
            return autopay;
        }

        public void setAutopay(String autopay) {
            this.autopay = autopay;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public String getBrandmodel() {
            return brandmodel;
        }

        public void setBrandmodel(String brandmodel) {
            this.brandmodel = brandmodel;
        }

        public String getEngineno() {
            return engineno;
        }

        public void setEngineno(String engineno) {
            this.engineno = engineno;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getRegistdate() {
            return registdate;
        }

        public void setRegistdate(String registdate) {
            this.registdate = registdate;
        }

        public String getCartype() {
            return cartype;
        }

        public void setCartype(String cartype) {
            this.cartype = cartype;
        }

        public String getUsecharacter() {
            return usecharacter;
        }

        public void setUsecharacter(String usecharacter) {
            this.usecharacter = usecharacter;
        }

        public int getInspectionexpire() {
            return inspectionexpire;
        }

        public void setInspectionexpire(int inspectionexpire) {
            this.inspectionexpire = inspectionexpire;
        }

        public String getApprovalstatus() {
            return approvalstatus;
        }

        public void setApprovalstatus(String approvalstatus) {
            this.approvalstatus = approvalstatus;
        }

        public String getApprovememo() {
            return approvememo;
        }

        public void setApprovememo(String approvememo) {
            this.approvememo = approvememo;
        }
    }
}
