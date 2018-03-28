#fastandroiddev3
## 这是什么
这是[FastAndroidDev](http://git.oschina.net/ijustyce/FastAndroidDev)的第三个版本，FastAndroidDev是基于MVC的一个框架，封装了二维码、支付宝等等。
这是基于[FastAndroidDev](http://git.oschina.net/ijustyce/FastAndroidDev)、retrofit、rxandroid、rxjava、data binding的一个框架，封装了retrofit，支持文件的上传下载并可以监听进度，封装了二维码，一行代码吊起二维码扫描，扫描结果在onActivityResult里获取，封装了支付宝，并基于content provider封装了CommonData原生多进程安全，封装了glide，支持动画、圆角,封装了大量ui，包括：irecyclerview、CommonTitleBar、FloatView、IScollPager、IViewPager、ProgressDialog、ProgressWebView、RecordButton，封装了大量工具类，包括：CommonTool、DateUtil、FileUtils、ILog、ObjectUtils、RegularUtils(常用正则，手机号、邮箱、网址等)、StringUtils、MD5、Base64等，不仅如此，还封装了BaseActivity、BaseListActivity、其中BaseListActivity依赖irecyclerview，用它实现一个列表，简单到你想不到，因为我把adapter也封装了，你不需要写adapter，只需要指定data binding信息即可！
## 扩展性
允许你添加自定义的Activity生命周期回调，这样做的好处是接入类似umeng统计、bugtags的时候不侵入你写的代码，假设某天要改，也只是删除ActivityLiftCall，然后添加新的即可。
## 相关资源
[FastAndroidDev](http://ijustyce.win/sort/fastandroiddev)
[FastAndroidDev备用,17年3月份过期](http://ijustyce.com/sort/fastandroiddev)
[我的新博客](http://blog.ijustyce.win/categories/android/)
