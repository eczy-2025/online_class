# 🎓 教学事务管理系统 (Teaching Affairs Management System)



> 基于 Spring Boot + MyBatis + Vue 的现代化选课与成绩管理系统。



## 📖 项目简介



这是一个教学事务管理系统。本项目在传统 SSM 架构的基础上，对前端界面进行了**卡片式 (Card UI)** 现代化升级，并重构了后端核心逻辑，引入了 MySQL **存储过程**与**触发器**来保障高并发下的选课数据一致性。



系统主要服务于学生、教师和管理员三类用户，实现了从选课、排课到成绩录入的全流程数字化管理。



---



## ✨ 核心亮点 (Highlights)



### 1. 🎨 现代化交互界面 (Modern UI)



* **卡片式布局**：摒弃了传统的表格展示，学生选课和教师管理均采用直观的卡片式设计。

* **可视化反馈**：选课卡片内置**动态进度条**，实时展示剩余名额，满员自动变红并禁用操作。

* **极简登录页**：全新的分栏式登录界面，提供流畅的用户体验。



### 2. 🛡️ 企业级数据保障 (Robust Backend)



* **原子性选课**：通过 MySQL **存储过程 (`sp_enroll_student`)** 处理选课逻辑，将“查重、容量校验、写入”封装为原子操作，有效防止超卖。

* **自动化维护**：引入数据库**触发器 (`Trigger`)**，选课成功后自动更新课程的“已选人数”，无需后端代码干预。

* **分层架构**：清晰的 `Controller` -> `Service` -> `DAO` 分层，解决了复杂的对象嵌套映射 (`Teacher` ↔ `Course`)。



---



## 🛠️ 技术栈 (Tech Stack)



| 模块 | 技术 | 说明 |

| --- | --- | --- |

| **后端** | Spring Boot 2.1.5 | 核心框架 |

|  | MyBatis | ORM 框架，支持动态 SQL |

|  | Java 8+ | 开发语言 |

| **前端** | Thymeleaf | 模板引擎 |

|  | Vue.js + iView | 响应式框架与 UI 组件库 |

|  | Axios | 前后端异步交互 |

| **数据库** | MySQL 5.7 / 8.0 | 关系型数据库 (含存储过程/触发器) |

| **构建** | Maven | 依赖管理 |



---



## 🚀 功能模块



### 👨‍🎓 学生端 (Student)



* **个人中心**：查看及修改个人信息、密码。

* **选课中心**：

* 浏览可选课程（卡片视图，含学分、任课老师、时间信息等）。

* **实时抢课**（受最大容量 `max_num` 限制）。

* 查看我的课表。





* **成绩查询**：查看已修课程的综合成绩。



### 👩‍🏫 教师端 (Teacher)



* **我的课程**：查看所教授的课程列表（卡片视图）。

* **成绩管理**：

* 点击课程卡片直接进入学生名单。

* 在线录入平时分与考试分，系统自动计算总评成绩。

* 数据实时保存至数据库。







### 🔧 管理员端 (Administrator)



* **基础数据管理**：学生、教师、课程的增删改查。

* **系统维护**：重置密码、发布课程等。



---



## 📂 目录结构



```text

online_class/

├── src/main/java/com/onlineclass/

│   ├── controller/      # Web 层控制器 (API 接口)

│   ├── service/         # 业务逻辑层 (接口与实现)

│   ├── dao/             # 数据访问层 (Mapper 接口)

│   ├── pojo/            # 实体类 (Course, Student, Teacher...)

│   └── util/            # 工具类 (MD5 加密等)

├── src/main/resources/

│   ├── mappers/         # MyBatis XML 映射文件

│   ├── static/          # 静态资源 (CSS, JS, Images)

│   ├── templates/       # HTML 页面 (Thymeleaf)

│   └── application.properties # 项目配置

└── online_class.sql     # 数据库初始化脚本



```



---



## ⚡ 快速开始 (Quick Start)



### 1. 环境准备



* JDK 1.8+

* Maven 3.x

* MySQL 5.7+

* IntelliJ IDEA (推荐)



### 2. 数据库配置



1. 创建数据库 `online_class`。

2. 导入根目录下的 `online_class.sql` 文件（包含建表、存储过程、触发器及初始数据）。

3. 修改 `src/main/resources/application.properties`：

```properties

spring.datasource.url=jdbc:mysql://localhost:3306/online_class?serverTimezone=UTC\&useUnicode=true\&characterEncoding=utf-8

spring.datasource.username=root

spring.datasource.password=你的密码



```







### 3. 启动项目



1. 在 IDEA 中打开项目。

2. 运行 `SpringbootMainApplication.java`。

3. 访问地址：`http://localhost:8080/login`



### 4. 测试账号



| 角色 | 账号 | 密码 | 说明 |

| --- | --- | --- | --- |

| **学生** | `20170235` | `123456` | 推荐测试选课功能 |

| **教师** | `1005` | `123456` | 肖伟老师，测试成绩录入 |

| **管理员** | `admin` | `123456` | 系统管理 |



---



## 📝 核心代码示例



**原子性选课存储过程 (SQL):**



```sql

CREATE PROCEDURE sp_enroll_student(...)

BEGIN

&nbsp;   -- 开启事务

&nbsp;   START TRANSACTION;

&nbsp;   -- 1. 检查容量

&nbsp;   SELECT selected_num, max_num INTO v_selected, v_max ...;

&nbsp;   -- 2. 检查是否重复

&nbsp;   SELECT COUNT(*) INTO v_exist ...;

&nbsp;   

&nbsp;   IF v_exist > 0 THEN ... -- 已选

&nbsp;   ELSEIF v_selected >= v_max THEN ... -- 满员

&nbsp;   ELSE

&nbsp;       INSERT INTO score ...; -- 选课

&nbsp;       COMMIT; -- 提交

&nbsp;   END IF;

END



```



---


