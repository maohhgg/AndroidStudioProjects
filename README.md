# AndroidStudioProjects

学习Android时练习的DEMO

### 内容

#### [CartView](https://github.com/maohhgg/AndroidStudioProjects/tree/master/CardView)
`CardView` 和 `RecyclerView` DEMO，使用 `RecyclerView.Adapter`

#### [Fileds](https://github.com/maohhgg/AndroidStudioProjects/tree/master/Fileds)  
使用Android各种View组件

#### [OnlineOrOffline](https://github.com/maohhgg/AndroidStudioProjects/tree/master/OnlineOrOffline)  
* 使用 `SQLite` 数据库，实现简单的用户登录注册，简单的用户管理。
  1. 使用了 `CardView` 和 `RecyclerView` 来展示用户列表， 简单案例[CartView](https://github.com/maohhgg/AndroidStudioProjects/tree/master/CardView)。
* 用广播实现其他应用注销当前登录的用户并提示， [Example](https://github.com/maohhgg/AndroidStudioProjects/tree/master/Example)中实现了注销的广播。
* 使用 `ContentProvider` 读取通讯录， 为数据库建立 `ContentProvider` 接口，方便其他应用读取。[ReadContentProvider](https://github.com/maohhgg/AndroidStudioProjects/tree/master/ReadContentProvider)实现读取本应用的 `ContentProvider` 内容。
* `Notification` 使用方法。

#### [Example](https://github.com/maohhgg/AndroidStudioProjects/tree/master/Example)
* 广播接收器 网络环境改变接受器action为`android.net.conn.CONNECTIVITY_CHANGE`，和内部自定义接收器。
* 广播发送器 发送内部自定义的广播和[OnlineOrOffline](https://github.com/maohhgg/AndroidStudioProjects/tree/master/OnlineOrOffline)注销登录的广播。
* `Activity` 的启动模式
  1. `standard` 模式
  2. `singleTop` 模式
  3. `singleTask`模式
  4. `singlestance` 模式
* `Tabbed Activity`

#### [ReadContentProvider](https://github.com/maohhgg/AndroidStudioProjects/tree/master/ReadContentProvider)
  使用 `ContentProvider` 对[OnlineOrOffline](https://github.com/maohhgg/AndroidStudioProjects/tree/master/OnlineOrOffline)数据库进行 `CRUD` 操作。

#### [SMS](https://github.com/maohhgg/AndroidStudioProjects/tree/master/SMS)
* 截取短信广播，实现接受短信
* `server` 后台服务的练习
* 载后台服务子线程中使用 `handler` `Message` `Binder` 对UI进行更新。
* 使用相机API、读取相册、播放音乐文件、播放视频。

#### [NetWork](https://github.com/maohhgg/AndroidStudioProjects/tree/master/NetWork)
* 使用 `HttpURLConnection` 发送请求和接受内容
* <del>使用 `HttpClient` 发送请求和接受内容，Android SDK 5.1开始不建议使用 `HttpClient`</del> 建议使用`HttpURLConnection`
* 解析 `XML` `Json` 格式文件。
