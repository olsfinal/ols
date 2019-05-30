package service;

import bean.BeanInfo;
import dao.InfoDao;
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

//    添加地址
    public void addInfo(BeanInfo beanInfo) throws Exception {
        try {
            infoDao.addInfo(beanInfo);
        }catch (Exception e){
            throw new Exception("新建地址失败");
        }
    }

//    获取某用户所有地址
    public Collection<BeanInfo> getInfos(String user_id) throws Exception{
        try {
            List<BeanInfo> infos = infoDao.loadInfos(user_id);
            if(infos==null) throw new Exception("您还没有添加地址");
            Collections.sort(infos);
            return infos;
        }catch (Exception e){
            throw new Exception("获取地址失败");
        }
    }

//    获取单个地址
    public BeanInfo getInfos(int info_id) throws Exception{
        try{
            return infoDao.loadInfoById(info_id);
        }catch (Exception e){
            throw new Exception("获取地址失败");
        }
    }

//    修改地址
    public void modifyInfo(BeanInfo info) throws Exception {
        BeanInfo info1 = infoDao.loadInfoById(info.getInfo_id());
        if(info1==null) throw new Exception("获取地址失败");
        if(info.getUser_id()!=info1.getUser_id())info1.setUser_id(info.getUser_id());
        if(info.getAddress()!=info1.getAddress())info1.setAddress(info.getAddress());
        if(info.getI_name()!=info1.getI_name())info1.setI_name(info.getI_name());
        if(info.getTel()!=info1.getTel())info1.setTel(info.getTel());
        infoDao.updateInfo(info1);
    }

//    删除地址
    public void deleteInfo(int info_id){
        infoDao.delInfo(info_id);
    }
}
