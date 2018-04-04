#口碑博客（koubeiblog）

演示demo已上线，为方便其他人访问demo，请谨慎操作，勿删除或修改admin账号密码权限。本项目永久免费开源，希望大家理解支持！

持续更新中...

 **技术讨论 QQ 群 592835013（捧个人场）** 

设计：
 整体架构使用spring boot框架
 权限层使用 kisso (集成shiro，单点登录等)
 持久层使用 mybatis-plus ([generalmapper](http://git.oschina.net/angryid/GeneralMapper)是为spring提供的基于mybatis-plus的一套单表crud通用mapper中间件)
 前端视图   velocity
 数据库    内置h2database

特点：
轻量级
 无mapper.xml、无mapper接口、无需配置数据源、无需配置tomcat

易读性
 70k后台代码看完源码只需要30分钟

使用：

- 导入工程
 git导入ide开发工具（推荐eclipse或者idea）

- 启动工程
 找到Application.java类 运行main函数启动项目

- 浏览器访问 (推荐使用火狐、360浏览器访问，ie目前前端js有问题)
  访问地址http://localhost:8080/system/index.html

默认后台管理员 用户名/密码：admin/ admin 
所有测试用户密码admin

示例图：
2016-11-03 升级0.8.0版本，改用springboot架构
![输入图片说明](http://git.oschina.net/uploads/images/2016/1103/002709_70842794_467423.png "在这里输入图片标题")

2016-10-27 合并来自 **尘风** 贡献的代码ueditor 感谢:  _@xjianhero_ 
![输入图片说明](http://git.oschina.net/uploads/images/2016/1027/190322_07bbbe4d_467423.png "在这里输入图片标题")
2016-10-20新增权限管理：
![输入图片说明](http://git.oschina.net/uploads/images/2016/1020/191118_c2c870e3_467423.png "在这里输入图片标题")
用户管理：
![输入图片说明](http://git.oschina.net/uploads/images/2016/1019/190206_9f9451a7_467423.png "在这里输入图片标题")
后台首页
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000511_7c9ef374_467423.png "在这里输入图片标题")
网站设置
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000546_9f2da47d_467423.png "在这里输入图片标题")
文章管理
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000635_de9bb0cf_467423.png "在这里输入图片标题")
分类、标签、评论管理示例图
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000652_e3c087c8_467423.png "在这里输入图片标题")
附件管理
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000724_9150be46_467423.png "在这里输入图片标题")
用户管理
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000814_45056b77_467423.png "在这里输入图片标题")
编辑用户
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000856_fd3e572e_467423.png "在这里输入图片标题")

前端首页
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/000942_c009d1f8_467423.png "在这里输入图片标题")
分类页
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/001010_c9049589_467423.png "在这里输入图片标题")
文章也
![输入图片说明](http://git.oschina.net/uploads/images/2016/1013/001026_35ca644e_467423.png "在这里输入图片标题")
