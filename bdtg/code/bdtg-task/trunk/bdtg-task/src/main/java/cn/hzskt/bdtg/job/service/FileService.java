package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.File;
import cn.hzskt.bdtg.job.domain.Folder;
import cn.hzskt.bdtg.job.mapper.FileMapper;
import cn.hzskt.bdtg.job.mapper.FolderMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  FileService extends BaseService<File,FileMapper> {
    @Autowired
    FolderMapper folderMapper;
    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<File> query(Map<String, String> paramMap) {
        Example example = new Example(File.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }
        String folderid = paramMap.get("folderid");
        if(folderid==null){
            folderid="";
        }
        criteria.andEqualTo("folder",folderid);
        return mapper.selectByExample(example);
    }
    /**
     * 根据文件查询文件
     * @param
     * @return
     */
    public List<File> getFileByNameAndId(String name) {
        Example example = new Example(File.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("name",name);
        return mapper.selectByExample(example);
    }
    /**
     * 查询文件
     * @param
     * @return
     */
    public List<Folder> getFolderByCodeandType(String pcode,Long type) {
        Example example = new Example(Folder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("pcode",pcode);
        criteria.andEqualTo("type",type);
        return folderMapper.selectByExample(example);
    }
    /**
     * 查询文件
     * @param
     * @return
     */
    public List<Folder> getFolderByTidandType(Long tid,Long type) {
        Example example = new Example(Folder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("tid",tid);
        criteria.andEqualTo("type",type);
        return folderMapper.selectByExample(example);
    }
}
