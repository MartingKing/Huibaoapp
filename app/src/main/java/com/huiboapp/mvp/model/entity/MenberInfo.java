package com.huiboapp.mvp.model.entity;


import java.io.Serializable;
import java.util.List;

public class MenberInfo {

    private String ackmsg;
    private String ackmsgid;
    private int result;
    private String reason;
    private DataBean data;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String memberid;
        private String nickname;
        private int sex;
        private String birthday;
        private String realname;
        private String idnumber;
        private String identification_front;
        private String identification_back;
        private String loginname;
        private String msisdn;
        private String photo;
        private String token;
        private int balance;
        private Object approvalstatus;
        private String approvememo;
        private String approvetime;
        private String userid;
        private List<PlatelistBean> platelist;

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
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

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public Object getApprovalstatus() {
            return approvalstatus;
        }

        public void setApprovalstatus(Object approvalstatus) {
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

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public List<PlatelistBean> getPlatelist() {
            return platelist;
        }

        public void setPlatelist(List<PlatelistBean> platelist) {
            this.platelist = platelist;
        }

        //车牌列表
        public static class PlatelistBean implements Serializable {
            /**
             * id : 1fda6840-3621-11eb-8099-3f919ce4ba00
             * plate : 辽N69Z72
             * platecolor : blue
             * autopay : false
             * license : null
             * vin : LSGJA52U0CS312055
             * brandmodel : null
             * engineno : null
             * owner : 123
             * registdate : 2020-12-17
             * cartype : null
             * usecharacter : null
             * inspectionexpire : 367
             * approvalstatus : tobeapproved
             * approvememo :
             */

            private String id;
            private String plate;
            private String platecolor;
            private boolean autopay;
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

            @Override
            public String toString() {
                return "PlatelistBean{" +
                        "id='" + id + '\'' +
                        ", plate='" + plate + '\'' +
                        ", platecolor='" + platecolor + '\'' +
                        ", autopay=" + autopay +
                        ", license='" + license + '\'' +
                        ", vin='" + vin + '\'' +
                        ", brandmodel='" + brandmodel + '\'' +
                        ", engineno='" + engineno + '\'' +
                        ", owner='" + owner + '\'' +
                        ", registdate='" + registdate + '\'' +
                        ", cartype='" + cartype + '\'' +
                        ", usecharacter='" + usecharacter + '\'' +
                        ", inspectionexpire=" + inspectionexpire +
                        ", approvalstatus='" + approvalstatus + '\'' +
                        ", approvememo='" + approvememo + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public boolean isAutopay() {
                return autopay;
            }

            public void setAutopay(boolean autopay) {
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
}
