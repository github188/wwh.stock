package com.javaear.koubeiblog.system.controller.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.dto.ArticleDTO;
import com.javaear.koubeiblog.system.entity.model.ArticleModel;
import com.javaear.koubeiblog.system.entity.model.CategoryModel;
import com.javaear.koubeiblog.system.entity.model.TagModel;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import com.javaear.koubeiblog.system.util.RequestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author aooer
 */
@Controller
public class ArticleManagerController extends AbstractManagerController<ArticleModel> {

    @RequestMapping("article-management")
    public void list(ModelMap modelMap) {
        ArticleModel serachModel = new ArticleModel();
        String searchCateId = request.getParameter("category");
        if (StringUtils.isNotBlank(searchCateId) && StringUtils.isNumeric(searchCateId))
            serachModel.setCateId(Integer.parseInt(searchCateId));
        String searchStatus = request.getParameter("status");
        if (StringUtils.isNotBlank(searchStatus) && StringUtils.isNumeric(searchStatus))
            serachModel.setStatus(Integer.parseInt(searchStatus));
        String searchTitle = request.getParameter("title");
        if (StringUtils.isNotBlank(searchTitle))
            serachModel.setTitle(searchTitle);
        pageQuery(modelMap, new EntityWrapper<>(serachModel));

        List<CategoryModel> categoryModels = cacheContext.getCacheDTO().getCategoryModelList();
        List<UserModel> userModels = generalMapper.selectList(new EntityWrapper<>(new UserModel()));
        modelMap.put("articles", ((List<ArticleModel>) modelMap.get("models")).stream()
                .map(m -> new ArticleDTO(categoryModels, userModels, m)).collect(Collectors.toList()));
        modelMap.put("searchCateId", searchCateId);
        modelMap.put("searchStatus", searchStatus);
        modelMap.put("searchTitle", searchTitle);
        modelMap.put("title", "文章管理");
    }


    @RequestMapping("article-add")
    public String add(ModelMap modelMap) {
        String type = request.getParameter("type");
        ArticleModel articleModel = RequestUtils.copyProperties(request, ArticleModel.class);
        if ((!StringUtils.isEmpty(type) && "show".equals(type)) || articleModel == null) {
            modelMap.put("title", "新建文章");
            return String.format(ADD_URL, MODEL_NAME);
        }
        articleModel.check();
        //更新分类文章总数+1
        int cateArticleTotalCount = generalMapper.selectById(articleModel.getCateId(), CategoryModel.class).getCount();
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(articleModel.getCateId());
        categoryModel.setCount(cateArticleTotalCount + 1);
        generalMapper.updateSelectiveById(categoryModel);
        //更新用户文章总数
        int userArticleTotalCount = generalMapper.selectById(articleModel.getUserId(), UserModel.class).getArticles();
        UserModel userModel = new UserModel();
        userModel.setId(articleModel.getUserId());
        userModel.setArticles(userArticleTotalCount + 1);
        generalMapper.updateSelectiveById(userModel);
        //更新标签信息
        String tagStr = request.getParameter("tags");
        if (StringUtils.isNotBlank(tagStr)) {
            String[] tagArray = tagStr.split("、");
            Set<Integer> tagIdArray = new HashSet<>();
            for (String s : tagArray) {
                TagModel searchTagModel = new TagModel();
                searchTagModel.setName(s);
                TagModel resultTagModel = generalMapper.selectOne(searchTagModel);
                if (resultTagModel == null) {
                    //新标签增加
                    searchTagModel.setCount(1);
                    generalMapper.insertSelective(searchTagModel);
                    tagIdArray.add(searchTagModel.getId());
                } else {
                    //已有标签修改标签文章总数
                    resultTagModel.setCount(resultTagModel.getCount() + 1);
                    generalMapper.updateSelectiveById(resultTagModel);
                    tagIdArray.add(resultTagModel.getId());
                }
            }
            articleModel.setTagIds(JSON.toJSONString(tagIdArray));
        }
        return String.format(generalMapper.insertSelective(articleModel) > 0 ? MNG_URL : ADD_URL, MODEL_NAME);
    }

    private Set<String> articleOldTags = new HashSet<>();

    @RequestMapping("article-edit")
    public String edit(ModelMap modelMap) {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id))
            return String.format(MNG_URL, MODEL_NAME);
        String type = request.getParameter("type");
        ArticleModel articleModel = RequestUtils.copyProperties(request, ArticleModel.class);
        if ((StringUtils.isNotBlank(type) && "show".equals(type)) || articleModel == null) {
            ArticleModel articleModel1 = generalMapper.selectById(Integer.parseInt(id), ArticleModel.class);
            modelMap.put("model", articleModel1);
            if (StringUtils.isNotBlank(articleModel1.getTagIds())) {
                List<TagModel> tagModels = generalMapper.selectBatchIds(JSON.parseObject(articleModel1.getTagIds(), List.class), TagModel.class);
                if (CollectionUtils.isNotEmpty(tagModels)) {
                    articleOldTags.addAll(tagModels.stream().map(TagModel::getName).collect(Collectors.toList()));
                    modelMap.put("tags", articleOldTags.stream().reduce((t1, t2) -> t1.concat("、").concat(t2)));
                }
            }
            modelMap.put("title", "编辑文章");
            return String.format(EDT_URL, MODEL_NAME);
        }
        //更新标签信息
        String tagStr = request.getParameter("tags");
        if (StringUtils.isNotBlank(tagStr)) {
            String[] tagArray = tagStr.split("、");
            Set<Integer> tagIdArray = new HashSet<>();
            for (String tag : tagArray) {
                TagModel searchTagModel = new TagModel();
                searchTagModel.setName(tag);
                TagModel resultTagModel = generalMapper.selectOne(searchTagModel);
                if (resultTagModel == null) {
                    //新标签插入
                    searchTagModel.setCount(1);
                    generalMapper.insertSelective(searchTagModel);
                    tagIdArray.add(searchTagModel.getId());
                } else if (!articleOldTags.contains(resultTagModel.getName())) {
                    //已有标签不存在原有文章列表
                    resultTagModel.setCount(resultTagModel.getCount() + 1);
                    generalMapper.updateSelectiveById(resultTagModel);
                    tagIdArray.add(resultTagModel.getId());
                }
                //已有标签若有此，删除此项处理
                articleOldTags.remove(tag);
            }
            //新标签列表，依然为处理的旧标签文章数-1
            articleOldTags.stream().forEach(tag -> {
                TagModel searchTagModel = new TagModel();
                searchTagModel.setName(tag);
                TagModel resultTagModel = generalMapper.selectOne(searchTagModel);
                if (resultTagModel != null) {
                    resultTagModel.setCount(resultTagModel.getCount() - 1);
                    generalMapper.updateSelectiveById(resultTagModel);
                }
            });
            articleModel.setTagIds(JSON.toJSONString(tagIdArray));
        }
        articleOldTags = new HashSet<>();//变量置空
        return String.format(generalMapper.updateSelectiveById(articleModel) > 0 ? MNG_URL : EDT_URL, MODEL_NAME);
    }

    @RequestMapping("article-remove")
    public String remove() {
        //根据id查询文章
        String id = request.getParameter("id");
        ArticleModel articleModel = generalMapper.selectById(id, ArticleModel.class);
        //关联分类文章数量-1
        CategoryModel categoryModel = generalMapper.selectById(articleModel.getCateId(), CategoryModel.class);

        categoryModel.setCount(categoryModel.getCount() - 1);
        generalMapper.updateSelectiveById(categoryModel);
        //关联用户文章数量-1
        UserModel userModel = generalMapper.selectById(articleModel.getUserId(), UserModel.class);
        userModel.setArticles(userModel.getArticles() - 1);
        generalMapper.updateSelectiveById(userModel);
        //关联标签文章数量-1
        if (StringUtils.isNotBlank(articleModel.getTagIds())) {
            JSON.parseObject(articleModel.getTagIds(), List.class).stream().forEach(tid -> {
                TagModel tagModel = generalMapper.selectById((int) tid, TagModel.class);
                tagModel.setCount(tagModel.getCount() - 1);
                generalMapper.updateSelectiveById(tagModel);
            });
        }
        return removeById();
    }
}
