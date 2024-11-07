# 一、需求分析

1. ## 题目描述

### 基础功能

- 对开发者的技术能力进行量化评级
  - 从开发者在一些主流领域中的热门开源项目的参与度入手，来量化每个开发者在不同领域下的技术能力评分。具体而言，首先我们会对主流领域中的热门开源项目进行一个评分，在量化开发者的参与程度后会将热门开源项目的分数按比例赋给开发者。
- 使用关系网络或其他算法猜测某个开发者的nation
  - 基于开发者followers和following的关系网络推测某个开发者的nation。但由于整个github用户构建的关系网络过于庞大，使用github API爬取有一定的请求limit，最终我们选择从开发者的location，company，github官网主页等信息入手，借助大语言模型llm去推测开发者的nation。
- 爬取数据来确定或推测开发者的领域。在查询的时候可以选定某个领域及Nation筛选相关的开发者，并可以按照tanlentRank排序。
  - 我们充分利用了Github API接口来爬取尽可能多的数据，并且存取了开发者参与过的项目及项目所属的topic标签等信息到了本地数据库中，并且设计了相关的查询接口来通过领域和Nation筛选开发者的TalentRank排名。

### 高级功能

- 置信度
  - 本项目中猜测的数据仅有开发者的Nation。在基础功能的第二点**使用关系网络或其他算法猜测某个开发者的nation**中我们提到了使用大语言模型llm来推测开发者的nation，推测的nation置信度。
- 评估信息自动整理
  - 对于开发者的个人信息，我们应用了大语言模型来对开发者繁多和种类不同的个人信息进行总结，最终结果将以一段文本的形式展示。

1. ## 功能列表

- 搜索领域或者用户返回相应的开发者排名
  - 搜索领域（任意字符串）
    - 精确匹配（只能从指定的热门领域里选）
    - 模糊匹配（允许用户搜索字符串，将字符串进行nlp自然语言处理来解析字符串到不同的领域中）
  - 搜索用户（从本地数据库中存储用户的表中搜索）
  - 返回
    - 开发者，并且按照评分排序
    - 列表
      - 每一行：头像、id、nation、评分、置信度
      - 点击这一行则展开（或者弹出），显示评分理由
        - 该领域评分理由
        - 点击后显示该开发者所有领域的综合得分和理由
        - ai摘要（可选）
- 排行榜
  - 计算开发者所有领域分数之和的总榜

# 二、产品设计（架构设计）

1. ## TalentRank量化标准

### 仓库价值计算公式

- 社区参与度（贡献者数量和用户反馈积极性，也就是open issues的数量）
- Star
- Fork

每个topic下爬取top10的仓库，随后保存全部仓库的信息至服务器的数据库。每个topic下社区参与度，Star，Fork各个维度都各有10分，这10个仓库根据每个维度占总和的比例分配这些分数。

$$Score_i = \sum_{dim}  \frac{dim_i}{\sum_{j=1}^{10}dim_j}  \\ dim \in \{open\_issues,star,fork\}$$

### 基础计算公式

Stars forks

- 开发者能力评估指标计算公式：

$$PA = \sum_{item}{\{W_{item}\times S(C_{item})\}},\\ item\in\{myItems,watch,fork,watch,pr,pr\_merged,pr\_review,follower\}$$

其中PA表示开发者能力值，$$W_{item}$$表示因素item的权重系数，$$C_{item}$$表示因素item的数量，$$S(C_{factor})$$表示因素item的得分。

- 影响因素的权重

$$W_{myItem}=0.175,\\ W_{watch}=0.106 ,\\ W_{fork}=0.171 , \\ W_{watch}=0.087,\\ W_{pr}=0.071,\\ W_{pr_{merged}}=0.127 ,\\  W_{pr\_review}=0.144 ,\\ W_{follower}=0.120$$

- 因素item的评分函数

$$S_{myItems}= \begin{cases} 10,&myItems=0\\60,&myItems=1\\65,&myItems=2\\70,&myItems=3\\75,&myItems=4\\80,&myItems=5\\85,&myItems=6\\90,&myItems=7\\95,&myItems=8\\100,&myItems≥9 \end{cases} \ \ \ \ \ \ \ \ \ \  S_{watches}= \begin{cases} 10,&watches=0\\60,&watches=1\\65,&watches=2\\70,&watches=3\\75,&4≤watches≤6\\80,&7≤watches≤11\\85,&12≤watches≤24\\90,&25≤watches≤67\\95,&68≤watches≤299\\100,&watches≥300 \end{cases}$$

$$S_{froks}= \begin{cases} 10,&forks=0\\60,&forks=1\\65,&2≤forks≤3\\70,&4≤forks≤9\\75,&10≤forks≤25\\80,&26≤forks≤70\\85,&71≤forks≤226\\90,&227≤forks≤1600\\95,&1601≤forks≤2999\\100,&forks≥3000 \end{cases} \ \ \ \ \ \  S_{parItems}= \begin{cases} 10,&parItems=0\\60,&parItems=1\\65,&parItems=2\\70,&parItems=3\\75,&parItems=4\\80,&parItems=5\\85,&parItems=6\\90,&parItems=7\\95,&parItems=8\\100,&parItems≥9 \end{cases} \\\\$$

$$S_{pr}= \begin{cases} 10,&pr=0\\60,&pr=1\\65,&pr=2\\70,&3≤pr≤6\\75,&7≤pr≤12\\80,&13≤pr≤26\\85,&27≤pr≤57\\90,&58≤pr≤149\\95,&150≤pr≤512\\100,&pr≥513 \end{cases} \ \ \ \ \ \ \ \ \ \  S_{pr\_merged}= \begin{cases} 10,&pr\_merged=0\\60,&pr\_merged=1\\65,&pr\_merged=2\\70,&3≤pr\_merged≤5\\75,&6≤pr\_merged≤8\\80,&9≤pr\_merged≤15\\85,&16≤pr\_merged≤27\\90,&28≤pr\_merged≤51\\95,&52≤pr\_merged≤121\\100,&pr\_merged≥122 \end{cases} \\\\$$

$$S_{pr\_review}= \begin{cases} 10,&pr\_review=0\\60,&pr\_review=1\\65,&2≤pr\_review≤3\\70,&4≤pr\_review≤5\\75,&6≤pr\_review≤8\\80,&9≤pr\_review≤14\\85,&15≤pr\_review≤22\\90,&23≤pr\_review≤38\\95,&39≤pr\_review≤68\\100,&pr\_review≥69 \end{cases} \ \ \ \ \ \ \ \ \ \  S_{followers}= \begin{cases} 10,&followers=0\\60,&followers=1\\65,&2≤followers≤3\\70,&4≤followers≤7\\75,&8≤followers≤14\\80,&15≤followers≤24\\85,&25≤followers≤43\\90,&44≤followers≤92\\95,&93≤followers≤351\\100,&followers≥352 \end{cases}$$

### nlp分类用户搜索字符串的实现方案

- 调用字节大模型doubao-pro4k实现自然语言处理

### 大模型评估指标

大模型会给每个开发者进行评估，对关键指标进行打分，并给出评语。

### 最终的开发者能力计算公式

$$Result=\sum_{item}{\{ W\times S \}}$$

参考：

![img](docs/images/1730992047300-4.png)

![img](docs/images/1730992046771-1.png)

> - [GitHub - OS-HUBU/ProgrammingAbility: 开发者编程能力评估体系](https://github.com/OS-HUBU/ProgrammingAbility)
> - https://compass.gitee.com/analyze/sc2ktdvl
>   - https://compass.gitee.com/zh/blog/
>   - https://github.com/oss-compass
> - https://gitestimate.vercel.app/?username=XuMoheng
>   - https://github.com/taqui-786/GitEstimate
> - https://github.com/OS-HUBU/DevValSys?tab=docs/images-ov-file

1. ## nation推测

开发者国家和地区的推断基于一个朴素的猜想：

1. 如果他在profile里写了nation，则采用profile的值；
2. 如果没写，那么看他项目的docs/images里是否使用了英语以外的语言（如中文、俄语）；
3. 如果仍不能判断nation，那么如果他关注的开发者与关注他的开发者里占多数的nation就是他的nation。

第3点中，如果他关注的开发者与关注他的开发者相同nation的人越多，那么置信度就越高。

但是实际开发中出现了一个比较严重的问题，就是爬取用户的follower和following需要消耗非常多的时间。获取follower的接口每次最多只能爬取100条数据，之后需逐个获取爬取下来的用户的nation，若数据库已有此用户，则直接读数据库，如果没有，就需要再一次逐个爬取用户主页，判断是否有location字段。用此方法推测一个有100个follower和following的用户的nation至少需要20分钟，这显然不太合适。

最后采用了一个可信度非常低的方法：让大模型分析github主页、博客、公司、个人资料、教育背景、项目描述、语言使用、时间记录等方面的信息，推测国籍。

1. ## 网站后端

- 本地数据库架构图

  - ![img](docs/images/1730992046771-2.png)

  -  数据库将在服务器上部署。

  - topic表结构

  - | topicId | topicName |
    | ------- | --------- |
    |         |           |

  - repo表结构

  - githubuser表结构

  - | login        | id           | node_id           | avatar_url        | gravatar_id | url        | html_url            | followers_url | following_url    |
    | ------------ | ------------ | ----------------- | ----------------- | ----------- | ---------- | ------------------- | ------------- | ---------------- |
    | gists_url    | starred_url  | subscriptions_url | organizations_url | repos_url   | events_url | received_events_url | type          | user_view_type   |
    | site_admin   | name         | company           | blog              | location    | email      | hireable            | bio           | twitter_username |
    | public_repos | public_gists | followers         | following         | created_at  | updated_at |                     |               |                  |

- Springboot,Redis,MySQL架构
- 依据特定领域及Nation查询排序相关领域的开发者
  - 请求类型：Get
  - 请求参数：q(String), Nation(String)
- 点击某个开发者头像进入其github面板/能力值面板

1. ## 网站前端

#### Flutter安卓端

- 热门topic接口
- 全部topic接口
- 根据topic返回相关用户的排名
- 用户详情接口

- Vue3,Flutter框架
- 暂时不需要做登录界面
- 需要做搜索框，及设计搜索出来一条一条的个人信息框，组件类似于ArcoDesign的List列表
  - 搜索框like👇
  - ![img](docs/images/1730992046771-3.png)
- 需要设计个人信息主页page

1. ## Python和Java通信

对于在线搜索GitHub开发者或者领域，我们依然采用Python爬取数据。将python爬取的数据返回给Java可以使用http通信，也可以使用RPC。我们实现了让Python和Java通过grpc通信，但是在线搜索需要耗费较多的时间，最终这个功能并未上线。

1. ## 前后端通信

1. ## 部署

# 三、需求列表

## 数据需求

- 爬取github官方所有的topics
- 爬取每个topic下所有的repo
- 从获取的数据中建立图网络

**其他参考资料**

https://blogweb.cn/article/5515335031801