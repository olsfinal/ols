package service;

import bean.BeanInfo;
import dao.InfoDao;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service("infoService")
public class InfoService {
    @Resource(name = "infoDao")
    private InfoDao infoDao;

    public InfoDao getInfoDao() {
        return infoDao;
    }

    public void setInfoDao(InfoDao infoDao) {
        this.infoDao = infoDao;
    }

    public void addInfo(BeanInfo beanInfo) throws Exception {
        try {
            infoDao.addInfo(beanInfo);
        }catch (Exception e){
            throw new Exception("新建地址失败");
        }
    }

    public Collection<BeanInfo> getInfos(String user_id) throws Exception{
        try {
            List<BeanInfo> infos = infoDao.loadInfos(user_id);
            if(infos==null) throw new Exception("您还没有添加地址");
            Collections.sort(infos);
        }catch (Exception e){
            throw new Exception("获取地址失败");
        }
    }
}
