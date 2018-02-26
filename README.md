# Google API application for Avery
Google API application for Avery
**1. 前期准备**
    (1)下载JDK 8或以上，设置好系统环境变量，并在Eclipse中设置JRE“windows”→"Preferences"→"JAVA"→“Installed JRES”→“Add”→“Standard VM”→"Next"→"JRE home填写JDK文件夹路径"和设置编译器“windows”→"Preferences"→"JAVA"→“Compiler”→“Compiler compliance level: 1.8”;
    (2)用Eclipse的"help"→"Install New Software"→“Work with填写http://download.eclipse.org/efxclipse/updates-released/3.1.0/site"  → “e(fx)clipse - IDE”,安装e(fx)clipse 插件;
        [http://www.eclipse.org/efxclipse/install.html](url)
        *遇到安装e(fx)clipse过程中出错，请按上述方式先安装"XTEXT"插件，“Work with”填写“http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/” → “XTEXT”
    (3)下载并安装JavaFX Scene Builder 2.0或更高版本,在Eclipse中设置:“windows”→"Preferences"→"JavaFX"→"SceneBuilder executable填写JavaFX Scene Builder 2.0可执行程序的绝对路径";
   JavaFX Scene Builder 2.0下载地址： [http://www.oracle.com/technetwork/java/javase/downloads/javafxscenebuilder-1x-archive-2199384.html](url)
    (4)按向导申请Google Sheet API;
    (5)新建project，导入Google Sheet API，Tomcat7，Win32这几个jar包;
    
**2.申请Google API**
    (1)用个人账号申请Google API;
[https://console.developers.google.com/apis/](url)
    (2)新建项目,参考一下链接的Step 1;
[https://developers.google.com/sheets/api/quickstart/java](url)

**3.代码部分**
    (1)新建"App" project(窗口界面程序)
    项目结构如下:
![image](https://user-images.githubusercontent.com/30543982/36658116-21ceacea-1b0a-11e8-8dc8-ce09e60e740a.png)
    (2)新建"GoogleSheet" project
    项目结构如下:
![image](https://user-images.githubusercontent.com/30543982/36625820-df1ce69a-1961-11e8-92d8-a932f72c05ef.png)

**4.打包**
   **MANIFEST.MF**
   自行编辑MANIFEST.MF,方便管理引用.jar包，引用.jar包放置在可执行.jar包同层目录里,如下图:
![image](https://user-images.githubusercontent.com/30543982/36658345-1599853e-1b0b-11e8-9ae7-3621fbf7e956.png)
   格式要求:参考以下链接.
[http://www.codezyw.com/archives/718](url)
