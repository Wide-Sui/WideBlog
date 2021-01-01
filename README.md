# WideBlog我的博客

🔗 [Wide Blog](http://159.75.7.169/index)

## 一、搭建环境

### 1、开发环境

* [Semantic-UI 2.4](https://semantic-ui.com/)
* Thymeleaf 3.0
* SpringBoot 2.4.1
* Mybatis 2.1.4
* MySQL 8.0.11
* Redis 6.0.9
* JDK 1.8

### 2、服务器

* [腾讯云轻量应用服务器](https://cloud.tencent.com/product/lighthouse)
* [腾讯云对象存储](https://cloud.tencent.com/product/cos)

### 3、其他工具

* 中文网页重设与排版：[Typo.css](https://github.com/sofish/typo.css)
* 代码高亮显示：[Prism](https://github.com/PrismJS/prism)
* 图片轮播工具：[Swiper6](https://www.swiper.com.cn/)

* 动画效果：[animate.css](https://daneden.github.io/animate.css/)

* 目录生成：[Tocbot](https://tscanlin.github.io/tocbot/)

* 阅读二维码生成：[qrcode.js](https://davidshimjs.github.io/qrcodejs/)

### 4、开发工具

* WebStorm
* IntelliJ IDEA
* DataGrip
* Docker
* Another Redis Desktop Manager
* Postman
* PicGo



## 二、功能介绍

### 1、角色

角色有两种：**管理员**（也就是我）和**游客**（其他浏览博客的人）

管理员功能：添加/修改/删除**博客**（Blog）、添加/修改/删除博客**类别**（Type）、添加/修改/删除博客**标签**（Tag）

游客功能：查看博客、添加**评论**、按照类别查找博客、按照标签查找博客、按年份归档查看博客、查看我的个人主页

### 2、功能展示

#### （1）主页

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/20210101215323.png" style="zoom:33%;" />



#### （2）博客详情

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/20210101215409.png" style="zoom:30%;" />



#### （3）按分类查看

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/image-20210101215541818.png" style="zoom:37%;" />



#### （4）按标签查看

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/image-20210101215637451.png" style="zoom:37%;" />



#### （5）归档

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/20210101215155.png" style="zoom:49%;" />



#### （6）导航栏

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/image-20210101215747681.png" style="zoom:37%;" />



#### （7）底部导航

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/image-20210101215905348.png" alt="image-20210101215905348" style="zoom: 37%;" />



## 三、数据库设计

<img src="https://myblog-1304618530.cos.ap-beijing.myqcloud.com/blogPics/image-20210102002450870.png" alt="image-20210102002450870" style="zoom:50%;" />

