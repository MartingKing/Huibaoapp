package com.huiboapp.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 首页资源实体类
 */
public class HomeBannerIconEntity implements Serializable {

    private List<BannerBean> banner;
    private List<IconBean> icon;
    private List<Tab3PageBean> tab3Page;
    private List<TabMatchHeadPageBean> tabMatchHeadPage;
    private List<TabMatchBottomPageBean> tabMatchBottomPage;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<IconBean> getIcon() {
        return icon;
    }

    public void setIcon(List<IconBean> icon) {
        this.icon = icon;
    }

    public List<Tab3PageBean> getTab3Page() {
        return tab3Page;
    }

    public void setTab3Page(List<Tab3PageBean> tab3Page) {
        this.tab3Page = tab3Page;
    }

    public List<TabMatchHeadPageBean> getTabMatchHeadPage() {
        return tabMatchHeadPage;
    }

    public void setTabMatchHeadPage(List<TabMatchHeadPageBean> tabMatchHeadPage) {
        this.tabMatchHeadPage = tabMatchHeadPage;
    }

    public List<TabMatchBottomPageBean> getTabMatchBottomPage() {
        return tabMatchBottomPage;
    }

    public void setTabMatchBottomPage(List<TabMatchBottomPageBean> tabMatchBottomPage) {
        this.tabMatchBottomPage = tabMatchBottomPage;
    }

    public static class BannerBean {
        /**
         * id : 1
         * resourceUrl :
         * ifRedirect : 0
         * resourceName : 首页1
         * status : 1
         * type : 1
         * typeSortNum : 1
         * resourceTitle : 首页1
         * resourceSecondPic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/134223509.png
         * resourceSecondBackground : #ff9c2a
         * resourcePic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/134122454.png
         */

        private int id;
        private String resourceUrl;
        private int ifRedirect;
        private String resourceName;
        private int status;
        private int type;
        private int typeSortNum;
        private String resourceTitle;
        private String resourceSecondPic;
        private String resourceSecondBackground;
        private String resourcePic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public int getIfRedirect() {
            return ifRedirect;
        }

        public void setIfRedirect(int ifRedirect) {
            this.ifRedirect = ifRedirect;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTypeSortNum() {
            return typeSortNum;
        }

        public void setTypeSortNum(int typeSortNum) {
            this.typeSortNum = typeSortNum;
        }

        public String getResourceTitle() {
            return resourceTitle;
        }

        public void setResourceTitle(String resourceTitle) {
            this.resourceTitle = resourceTitle;
        }

        public String getResourceSecondPic() {
            return resourceSecondPic;
        }

        public void setResourceSecondPic(String resourceSecondPic) {
            this.resourceSecondPic = resourceSecondPic;
        }

        public String getResourceSecondBackground() {
            return resourceSecondBackground;
        }

        public void setResourceSecondBackground(String resourceSecondBackground) {
            this.resourceSecondBackground = resourceSecondBackground;
        }

        public String getResourcePic() {
            return resourcePic;
        }

        public void setResourcePic(String resourcePic) {
            this.resourcePic = resourcePic;
        }
    }

    public static class IconBean {
        /**
         * id : 7
         * resourceUrl :
         * ifRedirect : 1
         * resourceName : 芝麻分贷
         * status : 1
         * type : 2
         * typeSortNum : 1
         * resourceTitle : 芝麻分贷
         * resourceSecondPic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/133646556.png
         * resourceSecondBackground : #ff9c2a
         * resourcePic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/095712212.png
         */

        private int id;
        private String resourceUrl;
        private int ifRedirect;
        private String resourceName;
        private int status;
        private int type;
        private int typeSortNum;
        private String resourceTitle;
        private String resourceSecondPic;
        private String resourceSecondBackground;
        private String resourcePic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public int getIfRedirect() {
            return ifRedirect;
        }

        public void setIfRedirect(int ifRedirect) {
            this.ifRedirect = ifRedirect;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTypeSortNum() {
            return typeSortNum;
        }

        public void setTypeSortNum(int typeSortNum) {
            this.typeSortNum = typeSortNum;
        }

        public String getResourceTitle() {
            return resourceTitle;
        }

        public void setResourceTitle(String resourceTitle) {
            this.resourceTitle = resourceTitle;
        }

        public String getResourceSecondPic() {
            return resourceSecondPic;
        }

        public void setResourceSecondPic(String resourceSecondPic) {
            this.resourceSecondPic = resourceSecondPic;
        }

        public String getResourceSecondBackground() {
            return resourceSecondBackground;
        }

        public void setResourceSecondBackground(String resourceSecondBackground) {
            this.resourceSecondBackground = resourceSecondBackground;
        }

        public String getResourcePic() {
            return resourcePic;
        }

        public void setResourcePic(String resourcePic) {
            this.resourcePic = resourcePic;
        }
    }

    public static class Tab3PageBean {
        /**
         * id : 5
         * resourceUrl :
         * ifRedirect : 1
         * resourceName : tab3顶部
         * status : 1
         * type : 3
         * typeSortNum : 1
         * resourceTitle : tab3顶部
         * resourceSecondPic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190507/112845297.png
         * resourceSecondBackground : #fff
         * resourcePic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/133857804.png
         */

        private int id;
        private String resourceUrl;
        private int ifRedirect;
        private String resourceName;
        private int status;
        private int type;
        private int typeSortNum;
        private String resourceTitle;
        private String resourceSecondPic;
        private String resourceSecondBackground;
        private String resourcePic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public int getIfRedirect() {
            return ifRedirect;
        }

        public void setIfRedirect(int ifRedirect) {
            this.ifRedirect = ifRedirect;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTypeSortNum() {
            return typeSortNum;
        }

        public void setTypeSortNum(int typeSortNum) {
            this.typeSortNum = typeSortNum;
        }

        public String getResourceTitle() {
            return resourceTitle;
        }

        public void setResourceTitle(String resourceTitle) {
            this.resourceTitle = resourceTitle;
        }

        public String getResourceSecondPic() {
            return resourceSecondPic;
        }

        public void setResourceSecondPic(String resourceSecondPic) {
            this.resourceSecondPic = resourceSecondPic;
        }

        public String getResourceSecondBackground() {
            return resourceSecondBackground;
        }

        public void setResourceSecondBackground(String resourceSecondBackground) {
            this.resourceSecondBackground = resourceSecondBackground;
        }

        public String getResourcePic() {
            return resourcePic;
        }

        public void setResourcePic(String resourcePic) {
            this.resourcePic = resourcePic;
        }
    }

    public static class TabMatchHeadPageBean {
        /**
         * id : 6
         * resourceUrl :
         * ifRedirect : 1
         * resourceName : tab3二级顶部
         * status : 1
         * type : 4
         * typeSortNum : 1
         * resourceTitle : tab3二级顶部
         * resourceSecondPic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190507/112845297.png
         * resourceSecondBackground : #fff
         * resourcePic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/133929464.png
         */

        private int id;
        private String resourceUrl;
        private int ifRedirect;
        private String resourceName;
        private int status;
        private int type;
        private int typeSortNum;
        private String resourceTitle;
        private String resourceSecondPic;
        private String resourceSecondBackground;
        private String resourcePic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public int getIfRedirect() {
            return ifRedirect;
        }

        public void setIfRedirect(int ifRedirect) {
            this.ifRedirect = ifRedirect;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTypeSortNum() {
            return typeSortNum;
        }

        public void setTypeSortNum(int typeSortNum) {
            this.typeSortNum = typeSortNum;
        }

        public String getResourceTitle() {
            return resourceTitle;
        }

        public void setResourceTitle(String resourceTitle) {
            this.resourceTitle = resourceTitle;
        }

        public String getResourceSecondPic() {
            return resourceSecondPic;
        }

        public void setResourceSecondPic(String resourceSecondPic) {
            this.resourceSecondPic = resourceSecondPic;
        }

        public String getResourceSecondBackground() {
            return resourceSecondBackground;
        }

        public void setResourceSecondBackground(String resourceSecondBackground) {
            this.resourceSecondBackground = resourceSecondBackground;
        }

        public String getResourcePic() {
            return resourcePic;
        }

        public void setResourcePic(String resourcePic) {
            this.resourcePic = resourcePic;
        }
    }

    public static class TabMatchBottomPageBean {
        /**
         * id : 12
         * resourceUrl :
         * ifRedirect : 1
         * resourceName : 最新口子
         * status : 1
         * type : 5
         * typeSortNum : 2
         * resourceTitle : 最新口子
         * resourceSecondPic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/154907351.png
         * resourceSecondBackground : #ff9c2a
         * resourcePic : http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/113832744.png
         */

        private int id;
        private String resourceUrl;
        private int ifRedirect;
        private String resourceName;
        private int status;
        private int type;
        private int typeSortNum;
        private String resourceTitle;
        private String resourceSecondPic;
        private String resourceSecondBackground;
        private String resourcePic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public int getIfRedirect() {
            return ifRedirect;
        }

        public void setIfRedirect(int ifRedirect) {
            this.ifRedirect = ifRedirect;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTypeSortNum() {
            return typeSortNum;
        }

        public void setTypeSortNum(int typeSortNum) {
            this.typeSortNum = typeSortNum;
        }

        public String getResourceTitle() {
            return resourceTitle;
        }

        public void setResourceTitle(String resourceTitle) {
            this.resourceTitle = resourceTitle;
        }

        public String getResourceSecondPic() {
            return resourceSecondPic;
        }

        public void setResourceSecondPic(String resourceSecondPic) {
            this.resourceSecondPic = resourceSecondPic;
        }

        public String getResourceSecondBackground() {
            return resourceSecondBackground;
        }

        public void setResourceSecondBackground(String resourceSecondBackground) {
            this.resourceSecondBackground = resourceSecondBackground;
        }

        public String getResourcePic() {
            return resourcePic;
        }

        public void setResourcePic(String resourcePic) {
            this.resourcePic = resourcePic;
        }
    }
}
