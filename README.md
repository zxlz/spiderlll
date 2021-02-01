# spiderlll
 以前写爬虫总结出的一个快速开发框架，spiderll分支是下载、解析、存储顺序执行的版本

 sample:

* 启动方法：传入json参数{...}  
   * 方法1. 直接control.CreateTh 传入json配置  
   * 方法2. server做服务部署，监听10001端口，传入json配置后由server代替启动  

* 启动过程：  
  1. 生成任务组件，根据此任务组件启动一个spider线程-->myspider(core包)  
  2. myspider  
      1. 调用其他模块生命周期  
      2. 启动3个子线程 
         * download， 从url模块取地址，给browser模块下载，结果给原始数据管道  
         * parse，从原始数据管道取一个任务，给parse模块解析，结果给产出数据管道  
         * storage，从产出管道取一个任务，给service模块持久化  
      3. 暂停，空闲 状态检测，结束退出  

* 结束过程： 
  1. spider线程发现3个子线程均为"空闲"，spider线程状态不再运行，  
  2. 子线程在检查点发现后安全退出，  
  3. 所有子线程均退出后，spider继续执行结束  
         

* 必须扩展：  
   1. service持久化部分（存储结果，初始化种子url和去重）  
   2. parse内容解析部分 （解析下载的html或json内容，也可以提取新url）  

* 可选扩展：  
   - url管理模块  
   - browser下载器模块  
   - repeatFilter去重模块  

下载模块有 httpconnection、普通浏览器、ajax浏览器等  

其他：taskcontent：任务上下文  

使用方式：实现需要扩展模块接口或抽象类，json传入  
