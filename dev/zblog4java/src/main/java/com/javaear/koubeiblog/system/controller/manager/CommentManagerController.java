package com.javaear.koubeiblog.system.controller.manager;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.enums.CommentStatusEnum;
import com.javaear.koubeiblog.system.entity.model.ArticleModel;
import com.javaear.koubeiblog.system.entity.model.CommentModel;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import com.javaear.koubeiblog.system.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author aooer
 */
@Controller
public class CommentManagerController extends AbstractManagerController<CommentModel> {

    @RequestMapping("comment-management")
    public void list(ModelMap modelMap) {
        CommentModel commentModel = new CommentModel();
        String searchStr = request.getParameter("search");
        if (StringUtils.isNotBlank(searchStr)) {
            commentModel.setTitle(searchStr);
            modelMap.put("search", searchStr);
        }
        pageQuery(modelMap, new EntityWrapper<>(commentModel));
        modelMap.put("title", "评论管理");
    }

    @RequestMapping("comment-add")
    public String add(ModelMap modelMap) {
        CommentModel commentModel = RequestUtils.copyProperties(request, CommentModel.class);
        if (commentModel != null) {
            commentModel.check();
            //评论添加
            generalMapper.insertSelective(commentModel);
            ArticleModel articleModel = generalMapper.selectById(commentModel.getArticleId(), ArticleModel.class);
            articleModel.setCommentNum(articleModel.getCommentNum() + 1);
            generalMapper.updateSelectiveById(articleModel);
        }
        return String.format(MNG_URL, MODEL_NAME);
    }

    @RequestMapping("comment-edit")
    public String edit(ModelMap modelMap) {
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            CommentModel commentModel = new CommentModel();
            commentModel.setId(Integer.parseInt(id));
            commentModel.setStatus(CommentStatusEnum.allow.getStatus());
            generalMapper.updateSelectiveById(commentModel);
        }
        return String.format(MNG_URL, MODEL_NAME);
    }

    @RequestMapping("comment-remove")
    public String remove() {
        String id = request.getParameter("id");
        CommentModel commentModel = generalMapper.selectById(id, CommentModel.class);
        //关联用户评论数-1
        UserModel userModel = generalMapper.selectById(commentModel.getUserId(), UserModel.class);
        userModel.setComments(userModel.getComments() - 1);
        generalMapper.updateSelectiveById(userModel);
        //关联文章评论数-1
        ArticleModel articleModel = generalMapper.selectById(commentModel.getArticleId(), ArticleModel.class);
        articleModel.setCommentNum(articleModel.getCommentNum() - 1);
        generalMapper.updateSelectiveById(articleModel);
        return removeById();
    }

}
