/*
 Navicat Premium Data Transfer

 Source Server         : music
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : music

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 23/03/2025 15:33:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for albums
-- ----------------------------
DROP TABLE IF EXISTS `albums`;
CREATE TABLE `albums`  (
  `album_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `artist_id` int NULL DEFAULT NULL,
  `cover_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `play_count` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `song_count` int NOT NULL,
  PRIMARY KEY (`album_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of albums
-- ----------------------------
INSERT INTO `albums` VALUES (1, '5:20AM', 1, 'http://127.0.0.1:9000/music/cover/4/1_1735025514577.jpg', '这是一个专辑', 0, 1, '2003-08-05 01:02:32', 1, '2005-04-05 14:05:27', 00000000000000000000, 12, 1);
INSERT INTO `albums` VALUES (2, '微微', 21, 'http://127.0.0.1:9000/music/cover/4/2_1735025528320.jpg', '这是一个专辑', 0, 1, '2024-12-18 19:42:53', 1, '2024-12-18 19:43:22', 00000000000000000000, 12, 1);
INSERT INTO `albums` VALUES (3, '就是 莫文蔚(It Is Karen Mok)', 22, 'http://127.0.0.1:9000/music/cover/4/3_1735025558896.jpg', '这是一个专辑', 0, 1, '2024-12-18 19:42:54', 1, '2024-12-18 19:43:24', 00000000000000000000, 12, 1);
INSERT INTO `albums` VALUES (4, '夏季备忘录', 23, 'http://127.0.0.1:9000/music/cover/4/4_1735025603479.jpg', '这是一个专辑', 0, 1, '2024-12-18 19:42:57', 1, '2024-12-18 19:43:25', 00000000000000000000, 12, 1);
INSERT INTO `albums` VALUES (5, '银河飞车', 24, 'http://127.0.0.1:9000/music/cover/4/5_1735025616665.jpg', '这是一个专辑', 0, 1, '2024-12-18 19:42:55', 1, '2024-12-18 19:43:27', 00000000000000000000, 12, 1);
INSERT INTO `albums` VALUES (6, '在你的身边', 25, 'http://127.0.0.1:9000/music/cover/4/6_1735025633903.jpg', '这是一个专辑', 0, 1, '2024-12-18 19:42:58', 1, '2024-12-18 19:43:28', 00000000000000000000, 12, 1);
INSERT INTO `albums` VALUES (7, '我的711', 24, 'http://127.0.0.1:9000/music/cover/4/7_1735025649575.jpg', '这是一个专辑', 0, 1, '2024-12-18 19:42:59', 1, '2024-12-18 19:43:29', 00000000000000000000, 12, 1);
INSERT INTO `albums` VALUES (8, '未知专辑', 2, ' ', ' ', 0, 1, '2024-12-19 12:12:32', 1, '2024-12-19 12:12:35', 00000000000000000000, 13, 1);

-- ----------------------------
-- Table structure for artists
-- ----------------------------
DROP TABLE IF EXISTS `artists`;
CREATE TABLE `artists`  (
  `artist_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bio` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int NOT NULL,
  `create_user` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `nationality` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `initials` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`artist_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of artists
-- ----------------------------
INSERT INTO `artists` VALUES (1, '刀酱', '简介：商务合作联系Atonevoom', 'http://127.0.0.1:9000/music/cover/2/1_1734699378303.webp', 2, 2, '2011-12-14 10:02:16', 1, '2013-05-24 07:47:08', '内地', 'd');
INSERT INTO `artists` VALUES (2, '未知歌手', '这是一个歌手', ' ', 0, 1, '2024-12-19 12:11:25', 1, '2024-12-19 12:11:30', ' ', '0');
INSERT INTO `artists` VALUES (21, '郭顶', '外文名：Dee Kwok\r\n\r\n别名：郭小顶|老郭|郭大师|郭老师\r\n\r\n国籍：中国\r\n\r\n出生地：湖南省怀化市芷江侗族自治县\r\n\r\n生日：1985年8月16日\r\n\r\n职业：歌手、音乐人、音乐制作人\r\n\r\n代表作：《不是说好的吗》、《下个入口》、《1985》、《我们俩》、《情歌两三首》、《幸运大门》、《凄美地》、《水星记》、《保留》\r\n\r\n简介：郭顶，1985年8月16日出生于湖南省怀化市，当代唱作人、音乐制作人、华语男歌手。\r\n\r\n2005年3月15日，发行首张个人创作专辑《郭顶D.Kwok.》；2009年8月22日，发行第二张个人专辑《微微》；2016年5月，凭借《当我身边空无一人》，夺得2015年度MusicRadio中国TOP音乐盛典内地年度最佳制作人。2016年11月25日，发行第三张个人唱作专辑《飞行器的执行周期》。2017年5月7日，出席“全球流行音乐金榜颁奖盛典”，并斩获“全球流行音乐金榜传媒推荐制作人”及“MusicRadio音乐之声推荐大奖”。2017年6月21日，参加2016年度“MusicRadio中国TOP排行榜颁奖盛典”获得最佳年度制作人。2017年6月22日，获得中华音乐人交流协会“年度十大专辑”及“年度十大单曲”。2017年6月23日，携专辑《飞行器的执行周期》六项提名出席第28届台湾金曲奖颁奖典礼。2017年7月14日，为电影《悟空传》制作电影插曲《紫》正式发行，由蔡健雅演唱。2017年9月22日，发行全新单曲《不明下落》。2017年10，举办首次个人15城livehouse巡演《落地之约》。2018年6月出席第18届华语音乐传媒盛典，凭借《飞行器的执行周期》获颁“年度国语专辑”奖项；同年，11月与美国独立音乐人山形瑞秋RachelYamagata共同创作歌曲《It’s Always The Little Things》上线。2022至2023年期间，他接连参加秦皇岛阿那亚虾米、第六届太湖湾、成都元气森林等音乐节。\r\n\r\n2023年3月，参加《杭州·聚橙梦响音乐节》，郭顶压轴出场，连唱《在云端》《落地之前》《凄美地》《水星记》等六首耳熟能详的歌曲，并多次引发现场合唱；同年4月至5月，又陆续在《2023成都元气森林音乐节》《2023琅琊云雀音乐节》《2023广州星巢秘境音乐节》的舞台上献唱；10月2日，与罗大佑、杨千嬅、杨丞琳等知名艺人一起参加《南昌星驰音乐节》。\r\n\r\n从艺历程：2005年3月15日，郭顶发行了首张个人创作专辑《郭顶D.Kwok.》。3月21日，担任东方神起北京歌友会表演嘉宾。7月9日担任周杰伦北京演唱会表演嘉宾。\r\n\r\n2009年8月22日，时隔4年，发行第二张专辑《微微》。\r\n\r\n2010年3月，为戚薇&袁成杰创作并制作单曲《做你的一半》； 同年11月，为王睿制作EP《夜一青春》。\r\n\r\n2011年10月，为那英《那又怎样》专辑创作同名主打歌《那又怎样》； 同年11月11日，为周笔畅创作《福尔摩斯》发行； 12月22日，为刘惜君创作的歌曲《怎么唱情歌》提名“中歌榜”年度最佳作曲奖 。\r\n\r\n2012年，与薛之谦首次合作《伏笔》； 担任丁爽首张专辑《不知道你喜不喜欢这样的我》的制作人 。\r\n\r\n2013年，为周笔畅歌曲《密友》《花樽与花》作曲；为薛之谦创作并制作《有没有》、《潮流季》。\r\n\r\n2014年，为叶一茜创作《一撇落花》。\r\n\r\n2015年，为刘惜君创作了《来》和《后来我们会怎样》两首饱含正能量的歌曲，收录在《当我身边空无一人》中 ；为薛之谦创作歌曲《小孩》；为周笔畅创作歌曲《翻白眼》、《隔墙花》。\r\n\r\n2016年郭顶加盟环球音乐，并于11月25日发行第三张个人创作专辑《飞行器的执行周期》。12月7日郭顶携新专辑《飞行器的执行周期》在北京举办影音创作全亚洲分享会，成为首位Apple Music（中国）独家首发深度合作视觉专辑华人歌手。\r\n\r\n2017年5月7日，郭顶出席“全球流行音乐金榜颁奖盛典”，并斩获“全球流行音乐金榜传媒推荐制作人”及“MusicRadio音乐之声推荐大奖”； 6月4日，出席“2017Hito流行音乐颁奖礼”获爱奇艺传媒推荐歌手；6月21日，参加2016年度“MusicRadio中国TOP排行榜颁奖盛典”获得最佳年度制作人；2017年6月12日，获得“第十届FreshmusicAwards”年度十大专辑、年度十大单曲及大跃进奖项； 6月22日，获得中华音乐人交流协会“年度十大专辑”及“年度十大单曲”； 6月23日，携专辑《飞行器的执行周期》六项提名出席第28届台湾金曲奖颁奖典礼；6月30日，为SoundLink Revolve倾请现场。 7月14日，为电影《悟空传》制作电影插曲《紫》正式发行，由蔡健雅演唱。9月22日，发行全新单曲《不明下落》； 9月30日，出席2017SuperSummerMusicFestival犀利趴。 10月14日，举办首次个人15城livehouse巡演《落地之约》。 11月5日，郭顶加盟On Air云游国际音乐节 。2017年12月29日，为刘惜君制作的专辑《如我》正式发行。\r\n\r\n2018年3月25日，深圳欢乐谷雅马水公园后青年音乐节演出\r\n\r\n2018年5月1日，苏州太湖迷笛音乐节演出\r\n\r\n2018年5月12日，杭州西湖音乐节演出\r\n\r\n2018年5月20日，张家口天漠音乐节演出\r\n\r\n2018年6月30日，出席第18届华语音乐传媒盛典，凭借《飞行器的执行周期》获得年度国语专辑奖。\r\n\r\n2018年11月，郭顶为易烊千玺创作的单曲《亲爱的，这里没有一个人》正式上线。\r\n\r\n2018年11月23日，与美国独立音乐人山形瑞秋RachelYamagata共同创作歌曲《It’s Always The Little Things》上线。\r\n\r\n2021年4月23日，单曲《_5:15》上线。 12月31日，参加“2021最美的夜bilibili·晚会”，演唱《水星记》《凄美地》。\r\n\r\n2022年3月21日，郭顶在社交平台宣布上线两张全新音乐辑《DIGI1》和《DIGI2》，这是两张关于“睡眠障碍”主题的环境音乐辑，封面合作摄影师许闯。\r\n\r\n2023年3月，参加《杭州·聚橙梦响音乐节》，郭顶压轴出场，连唱《在云端》《落地之前》《凄美地》《水星记》等六首耳熟能详的歌曲，并多次引发现场合唱；同年4月至5月，又陆续在《2023成都元气森林音乐节》《2023琅琊云雀音乐节》《2023广州星巢秘境音乐节》的舞台上献唱；10月2日，与罗大佑、杨千嬅、杨丞琳等知名艺人一起参加《南昌星驰音乐节》。\r\n\r\n2024年7月20日，参加的节日《芒果青春派对音乐节》在湖南卫视播出。\r\n\r\n荣誉记录：▪ 2018 2017阿鹿比音乐 年度独立流行单曲 不明下落 （获奖）\r\n\r\n▪ 2018 第18届华语音乐传媒盛典 年度国语专辑 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 全球流行音乐金榜颁奖盛典 全球流行音乐金榜传媒推荐制作人 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 全球流行音乐金榜颁奖盛典 MusicRadio音乐之声推荐大奖 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 2017Hito流行音乐颁奖礼 爱奇艺传媒推荐歌手 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 MusicRadio中国Top音乐盛典 最佳年度制作人 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 第十届FreshmusicAwards 年度十大专辑 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 第十届FreshmusicAwards 年度十大单曲 保留 （获奖）\r\n\r\n▪ 2017 第十届FreshmusicAwards 年度大跃进 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 中华音乐人交流协会 年度十大专辑 飞行器的执行周期 （获奖）\r\n\r\n▪ 2017 中华音乐人交流协会 年度十大单曲 凄美地 （获奖）\r\n\r\n▪ 2017 第28届台湾金曲奖 最佳国语男歌手 飞行器的执行周期 （提名）\r\n\r\n▪ 2017 第28届台湾金曲奖 最佳国语专辑 飞行器的执行周期 （提名）\r\n\r\n▪ 2017 第28届台湾金曲奖 最佳作曲 凄美地 （提名）\r\n\r\n▪ 2017 第28届台湾金曲奖 最佳作词 凄美地 （提名）\r\n\r\n▪ 2017 第28届台湾金曲奖 最佳音乐录影带 水星记 （提名）\r\n\r\n▪ 2017 第28届台湾金曲奖 年度专辑 飞行器的执行周期 （提名）\r\n\r\n▪ 2016 MusicRadio中国TOP音乐盛典 内地年度最佳制作人 当我身边空无一人 （获奖）', 'http://127.0.0.1:9000/music/cover/2/21_1734699344993.webp', 1, 1, '2024-11-25 13:50:39', 1, '2024-11-25 13:50:39', '内地', 'g');
INSERT INTO `artists` VALUES (22, '莫文蔚', '外文名：Karen Mok\r\n\r\n国籍：英国\r\n\r\n出生地：中国香港\r\n\r\n出生日期：1970年6月2日\r\n\r\n职业：歌手、演员、作曲人、作词人\r\n\r\n代表作品：《喜剧之王》、《如果没有你》、《盛夏的果实》、《忽然之间》、《食神》等\r\n\r\n主要成就：香港电影金像奖最佳女配角，MTV亚洲大奖启发精神奖，CCTV-MTV音乐盛典最佳女歌手，两届台湾金曲奖最佳国语女歌手\r\n\r\n简介：莫文蔚（Karen Joy Morris，艺名Karen Mok），混血儿，中国香港歌手和演员，是歌、影、视的三栖艺人。\r\n\r\n1993年发行首张粤语唱片《karen莫文蔚》进入娱乐圈；1995年主演电影《大话西游》获得广泛关注。1996年凭借电影《堕落天使》获得香港电影金像奖最佳女配角奖。1997年莫文蔚在台湾推出首张国语唱片《做自己》，专辑卖出80多万张，主打歌《他不爱我》成为其代表作之一；1999年被香港时装设计师协会评为香港十大杰出衣着人士；2000年被《君子杂志》评选为“我们最喜欢的女性”；截至2013年共发行30多张专辑，参演50多部电影，举行过30多场个人演唱会；2008年代表香港参与韩国第五届亚洲音乐节，并获颁亚洲最佳女歌手奖（中国），2011年凭借专辑《宝贝》夺得台湾金曲奖最佳国语女歌手奖。2013年，莫文蔚发行首张英语爵士专辑“Somewhere I Belong”，成为首位itunes全球同步发行的华人歌手。2014年9月19日，莫文蔚在沪举办2014“莫后年代”莫文蔚20周年世界巡回演唱会-安可场，而此次场地选址上海大舞台。 2015年2月18日莫文蔚登上中央电视台春节联欢晚会，独唱歌曲《当你老了》。9月展开莫文蔚“看看世界巡回演唱会”。2016年7月，参与录制《我们来了》。2016年10月16日，莫文蔚参与录制的综艺节目《天籁之战》首播。10月12日，发行《天籁 Studio Version》EP。2017年10月15日，其参与大型星素结合励志音乐类节目《天籁之战第二季》在东方卫视首播。2023年4月20日，《声生不息·宝岛季》发布第三次公演上半场的歌单，莫文蔚加盟并演唱歌曲《忽然之间》。6月，参加声生不息金曲盛典。9月26日，发行与欧阳靖 MC Jin、李铢衔 James、张淇合作的单曲《月光光》。9月29日，参与演出的《中央广播电视总台2023年中秋晚会》播出，在晚会中演唱歌曲《一生所爱》《老街》《慢慢喜欢你》。2024年3月14日，参加的节目《嘉人自友约》播出。5月9日，参加的综艺《很高兴认识你》第三季播出。\r\n\r\n从艺历程：1997年 莫文蔚被选为PIFAN 1997第一届韩国富山市国际电影节评审委员\r\n\r\n2001年 成为Christian Dior口红亚洲区诱人舞会代言人；2001年 在电视剧《烈爱伤痕》中饰演更纱。\r\n\r\n2001年 莫文蔚被选为第四届法国杜维尔亚洲电影节评审委员。\r\n\r\n2003年 凭借专辑《i》 获得第14届台湾金曲奖最佳国语女演唱人奖，成为首位赢得台湾金曲奖的香港女歌手。\r\n\r\n2005年 被选为香港购物节代言人。\r\n\r\n2005年 莫文蔚是领衔百老汇热门音乐剧《吉屋出租》十周年全球巡演的首位亚洲明星，并在电视剧《老冯日记》第8集饰演了莫文蔚。\r\n\r\n2007年 莫文蔚以首次担任音乐监制的专辑《拉活…莫文蔚》，夺得第19届台湾金曲奖最佳国语专辑奖，是首位获颁此奖的香港女歌手。同年莫文蔚歌曲“广岛之恋”于台北之音FM107.7 -“百首经典对唱歌曲票选活动”中以20011票拿下冠军。\r\n\r\n2008年 莫文蔚与好莱坞公司CAA签约，与碧咸及毕彼特等同门，为首位香港女艺人加盟该大型国际经理人公司，美国电影杂志《Hollywood Reporter》更形容她是“亚洲最成功的女演员”之一。同年莫文蔚专辑《12楼的莫文蔚》入选中华音乐人交流协会（1993 - 2005年台湾流行音乐百大专辑），排名60位。并参与北京奥运的倒数奥运100天晚会、开幕式及闭幕式均与容祖儿有文艺表演；莫文蔚还在湖北省宜昌市担任第159棒火炬手。\r\n\r\n2010年获2010年 亚洲运动会广州亚组委颁发了亚运歌手的荣誉证书，并是亚运歌曲《天涯共此时》演唱者之一。同年登上亚运特别版“贺新春”四人版邮票《天涯共此时》，此乃中国邮政首次为港台及海外艺人发行的专属邮票，莫文蔚则成为首位香港代表。\r\n\r\n2010年、2011年、2012年 连续三年进入富比士杂志“中国百大名人权力榜”，并连续在2011年、2012年的香港女歌手、演员类别中获得第一位。\r\n\r\n2011年 凭借专辑《宝贝》获得第22届台湾金曲奖最佳专辑提名，并且获得第22届台湾金曲奖最佳国语女演唱人奖 。\r\n\r\n2013年 为莫文蔚出道20周年，9月 于置地广场举行《莫后年代－莫文蔚20周年展览》，并于十一月开始巡回演唱会，巡回演唱会在14个城市举行25场，打破莫文蔚个人演唱会场数纪录，并进占内地个人演唱会场数最多的香港女歌手第三位。重庆站最高票价为人民币3680，刷新华语女歌手最高票价纪录。\r\n\r\n2014年 参演由徐峥监制并主演的，年度烧脑心理悬疑片《催眠大师》 ，讲述了徐铮饰演的催眠师邂逅莫文蔚饰演的神秘女病人之后发生了一系列神秘事件。\r\n\r\n2014年6月19日 莫文蔚低调抵达长沙参与备战，拉开了莫文蔚“莫后年代”世界巡回演唱会长沙站序幕，6月20日、21日 在湖南大剧院莫文蔚连唱两夜。 9月19日 莫文蔚来沪举办2014“莫后年代”莫文蔚20周年世界巡回演唱会-安可场，而此次场地选址上海大舞台。\r\n\r\n2015年2月18日 莫文蔚将登上央视春晚，独唱歌曲《当你老了》。7月24日，刘镇伟执导的《大话西游终结篇》在银川开机，莫文蔚将继续饰演白晶晶一角 。\r\n\r\n2015第19届全球华语榜中榜获亚洲全能歌手，亚洲影响力最受欢迎歌手等音乐奖项；而2015年最新专辑《不散，不见》，又入围今年第26届金曲奖7项提名。莫文蔚“看看世界巡回演唱会”首站9月19日于台北小巨蛋。\r\n\r\n2016年4月9日，莫文蔚在新加坡举行世巡演唱会，获好友杨采妮和孙燕姿一家捧场。杨采妮还化身小粉丝在台下尖叫，又唱又跳。\r\n\r\n2016年7月，莫文蔚参加的湖南卫视明星女神生活体验秀节目《我们来了》首播。10月，莫文蔚参加的东方卫视音乐挑战综艺节目《天籁之战》首播。\r\n\r\n2017年10月15日，其参与大型星素结合励志音乐类节目《天籁之战第二季》在东方卫视首播。\r\n\r\n2018年1月23日，莫文蔚宣布今年将开启全新个人巡演，分享她的音乐人生，演唱会也正式命名为“绝色莫文蔚世界巡回演唱会”。5月31日，莫文蔚携全新数字专辑《我们在中场相遇》在京举行新歌首唱会；2018年7月18日，莫文蔚在京召开发布会，宣布签约索尼音乐娱乐。发布会现场公布了莫文蔚将与索尼音乐娱乐联合成立个人厂牌“Mok-A-Bye Baby Records”，这是世界上第一个华人歌手和国际唱片公司共同创办的音乐厂牌。\r\n\r\n2019年5月11日晚，“绝色莫文蔚世界巡回演唱会”在广州海心沙亚运公园拉开了第21场的帷幕。10月12日，莫文蔚在拉萨举行演唱会，该场演唱会被授予吉尼斯世界纪录——在海拔3646米的场馆挑战“最高海拔的大规模演唱会”。\r\n\r\n2020年1月24日，在2020年中央广播电视总台春节联欢晚会上与宋丹丹、张国立、林永健等表演歌舞《春潮颂》。5月11日，参与由人民日报客户端、国家人文历史等联合发起的“致敬白衣天使”线上音乐会 。\r\n\r\n2021年1月17日，莫文蔚在“2020年度劲歌金曲颁奖典礼”上凭《飞虎之雷霆极战》歌曲《呼吸有害》夺得劲歌金曲金奖，同日凭借该曲获得第43届十大中文金曲颁奖典礼十大中文金曲奖、全球华人至尊金曲；6月11日，发行专辑《The Voyage》；9月21日，参加《2021年中央广播电视总台中秋晚会》并演唱歌曲《这世界那么多人》；10月15日，参加《抖音美好奇妙夜》；10月29日，在《披荆斩棘的哥哥》作为压轴帮唱嘉宾，与李铢衔、欧阳靖和言承旭一起合作歌曲《如果没有你》。\r\n\r\n2022年2月1日10:00，参与唱响的音乐短片《一起向未来》全网首发， 22:00电影频道（CCTV6）首播。11月，演唱的歌曲《这世界那么多人》提名第34届华鼎奖全球最佳影视歌曲。\r\n\r\n2023年1月，在《大湾仔的夜第二季》中为大湾仔和观众们送上新春祝福。4月20日，《声生不息·宝岛季》发布第三次公演上半场的歌单，莫文蔚加盟并演唱歌曲《忽然之间》。6月，参加声生不息金曲盛典。9月26日，发行与欧阳靖 MC Jin、李铢衔 James、张淇合作的单曲《月光光》。9月29日，参与演出的《中央广播电视总台2023年中秋晚会》播出，在晚会中演唱歌曲《一生所爱》《老街》《慢慢喜欢你》。\r\n\r\n2024年1月27日，参加的《航天之夜》在央视频客户端与央视网平台同步直播。3月14日，参加的节目《嘉人自友约》播出。5月9日，参加的综艺《很高兴认识你》第三季播出。6月17日，领衔主演的电影《焚城》官宣。\r\n\r\n荣誉记录：2018华人歌曲音乐盛典\r\n\r\n▪ 2018-08 年度全能女艺人、年度最受欢迎女歌手 （获奖）\r\n\r\n东方风云榜\r\n\r\n▪ 2018-03 第25届 亚洲杰出歌手及风云成就大奖 （获奖）\r\n\r\nGMIC X盛典\r\n\r\n▪ 2017 2017互联网时代非凡风尚人物 （获奖）\r\n\r\n福布斯\r\n\r\n2012 中国名人吧第62位 （获奖）\r\n\r\n真维斯娱乐大典\r\n\r\n2011 第5届 年度音乐榜样人物、年度最具影响力公益人物 （获奖）\r\n\r\n2010年度MusicRadio\r\n\r\n2011 音乐之声中国TOP排行榜获得年度最佳唱片 宝贝 （获奖）\r\n\r\n台湾金曲奖\r\n\r\n2011 第22届 最佳专辑、单曲、最佳女演唱人、最佳制作人、作曲人、作词人共\r\n\r\n六项 宝贝 （提名）\r\n\r\n2011 第22届 最佳国语女演唱人奖 宝贝 （获奖）\r\n\r\n2010 第21届 最佳专辑、最佳女演唱、最佳编曲、最佳制作人 回蔚 （提名）\r\n\r\n2008 第19届 最佳国语专辑奖 拉活 （获奖）\r\n\r\n2003 第14届 最佳国语女演唱人奖 i （获奖）\r\n\r\n2001 第12届 最佳国语女演唱人奖、最佳流行音乐演唱专辑奖 12楼的莫文蔚 （提名）\r\n\r\n2000 第11届 最佳国语女演唱人奖 You Can （提名）\r\n\r\n1998 第9届 流行音乐演唱专辑奖 to be （提名）\r\n\r\n中歌榜\r\n\r\n2010 年度金曲、年度全能艺人、年度最佳专辑 （获奖）\r\n\r\n蒙牛酸酸乳音乐风云榜十年盛典\r\n\r\n2010 十年最具影响力音乐人物(港台) （获奖）\r\n\r\n2010 十年港台十大金曲 爱 （获奖）\r\n\r\n东南劲爆音乐榜\r\n\r\n2009 第7届 港台地区最佳女歌手、最佳数字专辑 回蔚 （获奖）\r\n\r\n2009 第7届 十大金曲 打起手鼓唱起歌 （获奖）\r\n\r\n咪咕汇\r\n\r\n2009 歌曲奖 密流 （获奖）\r\n\r\n2009 获最畅销首发数字专辑 （获奖）\r\n\r\n2009 最佳数字专辑 回蔚 （获奖）\r\n\r\nELLE\r\n\r\n2008 ELLE风尚全能之星 （获奖）\r\n\r\n中国移动无线音乐\r\n\r\n2008 奥运推广杰出歌手 （获奖）\r\n\r\n香港电影金像奖\r\n\r\n2008 第27届 最佳女配角 （提名）\r\n\r\n2007 第26届 最佳女配角 老港正传 （提名）\r\n\r\n2006 第25届 最佳女主角 童梦奇缘 （提名）\r\n\r\n2005 第24届 最佳女主角 童梦奇缘 （提名）\r\n\r\n1997 第16届 最佳女主角 食神 （提名）\r\n\r\n1996 第15届 最佳女配角 堕落天使 （获奖）\r\n\r\nTVB\r\n\r\n2008 TVB8金曲奖 日场夜场 （获奖）\r\n\r\n2003 TVB十大劲歌金曲最受欢迎国语歌曲金奖歌曲 看透 （获奖）\r\n\r\n2003 TVB8金曲榜最受欢迎女歌手奖 （获奖）\r\n\r\n2002 TVB劲歌金曲第二季最受欢迎国语歌 爱 （获奖）\r\n\r\nMTV\r\n\r\n2008 亚洲大奖启发精神奖 （获奖）\r\n\r\n1999 MTV Award99(Beijing)-Newcorner Artist Asia （获奖）\r\n\r\nCCTV-MTV\r\n\r\n2008 第9届 香港地区最受欢迎女歌手大奖 （获奖）\r\n\r\n2003 第5届 音乐盛典香港地区年度最佳女歌手 （获奖）\r\n\r\n新城\r\n\r\n2004 新城国语力歌曲、国语力热播冠军歌曲大奖、国语力亚洲歌手大奖 看透 （获奖）\r\n\r\n2003 新城劲爆歌曲、劲爆卡拉OK歌曲、劲爆亚洲女歌手 透视 （获奖）\r\n\r\n2002 新城电台劲爆-最佳歌曲、最佳女艺人 爱 （获奖）\r\n\r\n金鸡百花奖\r\n\r\n2007 最佳女配角 老港正传 （提名）\r\n\r\n十大Style Icon\r\n\r\n2005 第1届 十大Style Icon香港时尚之星 （获奖）\r\n\r\n雪碧我的选择中国原创音乐流行榜\r\n\r\n2005 第4届 最受欢迎女歌手台湾地区、“我的选择”最受欢迎女歌手、传媒推荐大奖 （获奖）\r\n\r\n2001 最受欢迎歌曲、香港最受欢迎女歌手 两个女孩 （获奖）\r\n\r\n2000 季选歌曲 忽然之间 （获奖）\r\n\r\n华语音乐榜中榜\r\n\r\n2002 CHANNEL V全球华语音乐榜中榜-最受欢迎歌曲 爱 （获奖）\r\n\r\n2001 CHANNEL V新千年全球华语音乐榜中榜-最受欢迎歌曲 懒得管 （获奖）\r\n\r\n1999 CHANNEL V华语榜中榜1999-歌曲奖 阴天 （获奖）\r\n\r\nMTV超级盛典\r\n\r\n2004 最具风格外型女艺人奖、港台地区最具风格女歌手奖 （获奖）\r\n\r\n莱卡亚洲\r\n\r\n2003 风尚杰出艺人奖 （获奖）\r\n\r\n第三季华语流行乐传媒大奖\r\n\r\n2003 我最喜爱的华语歌曲 X （获奖）\r\n\r\n2003 最佳女艺人 （获奖）\r\n\r\n2003 十佳单曲 爱死你 （获奖）\r\n\r\n《壹周刊》健康之星选举\r\n\r\n2001 Body By Deborah最具活力健美大奖 （获奖）\r\n\r\n精选104好友音乐会\r\n\r\n2000 台湾地区最受欢迎香港歌手 （获奖）\r\n\r\n优秀国语歌\r\n\r\n2000 香港电台十大中文金曲颁奖礼-优秀国语歌银奖 那么爱你为什么 （获奖）\r\n\r\n最佳电影主题曲\r\n\r\n1999 新城劲爆-最佳电影主题曲 The Way You Make Me Feel （获奖）\r\n\r\n叱咤东方飞碟至尊歌手奖\r\n\r\n1999 99\'叱咤乐坛流行榜-叱咤东方飞碟至尊歌手奖 （获奖）\r\n\r\n金紫荆奖\r\n\r\n1999 最佳女配角 心动 （提名）\r\n\r\n1996 最佳女配角 堕落天使 （获奖）\r\n\r\n“魅力之星”奖\r\n\r\n1999 精选104我指定精选-“魅力之星”奖 （获奖）\r\n\r\n女歌手奖\r\n\r\n1998 超过台湾最受欢迎女歌手奖 （获奖）\r\n\r\n台湾金马奖\r\n\r\n1997 最佳女主角 食神 （提名）\r\n\r\n学生奖\r\n\r\n1987 1987-1989 意大利联合世界书院奖学金 （获奖）\r\n\r\n1985 年度香港最杰出学生奖 （获奖）', 'http://127.0.0.1:9000/music/cover/2/22_1734699305137.webp', 2, 1, '2024-11-25 13:55:13', 1, '2024-11-25 13:55:13', '内地', 'm');
INSERT INTO `artists` VALUES (23, 'Lil Nico', '简介：喜欢黑怕的怕黑小学生。', 'http://127.0.0.1:9000/music/cover/2/23_1734699258938.webp', 2, 1, '2024-11-25 13:57:05', 1, '2024-11-25 13:57:05', '内地', 'l');
INSERT INTO `artists` VALUES (24, '张叶蕾', '简介：张叶蕾，独立唱作人，知名音乐自媒体人。大学就读于加州大学欧文分校，康奈尔音乐学院作词作曲系研究生。 自2017年起发布《还是分开》《梦中人》《你回头看》等近30首原创歌曲，其中《梦中人》及其MV被收录于美剧《博斯》。2019年原创歌曲《还是分开》风靡全网，音乐累计播放量近10亿次。2021年，由《还是分开》改编的歌曲《还是劈开》火爆全网，让她华丽蜕变为一众玩家心目中的“诺克萨斯”公主。2020年末，张叶蕾凭借优秀的创作才华获得了第二届“刘欢原创音乐专项公益金”前三甲；2021年初，在《星光大道》节目中张叶蕾一路过关斩成为第一期周冠军。音乐是张叶蕾生活中的主角，除了音乐人的身份外，由作为自媒体人的她所创办的原创音乐教学节目《没有音吃》全网点击量如今已突破8000万次！', 'http://127.0.0.1:9000/music/cover/2/24_1734699221385.webp', 2, 1, '2024-11-25 14:02:00', 1, '2024-11-25 14:02:00', '内地', 'z');
INSERT INTO `artists` VALUES (25, '盛哲', '简介：盛哲，唱作歌手。荣誉毕业于美国顶尖工程院校，拉法耶特学院，获得音乐和机械工程双学位。研究生毕业于纽约大学（NYU）音乐科技专业。\r\n\r\n他的作品跨越欧美及华语流行、R&B、电子等诸多风格。复古|前卫，流行|独立，他的音乐中穿越着不同流派的声音，听得到多维度的思想。 是值得聆听的原创之声。', 'http://127.0.0.1:9000/music/cover/2/25_1734699125368.webp', 1, 1, '2024-11-25 14:03:38', 1, '2024-11-25 14:03:38', '内地', 's');
INSERT INTO `artists` VALUES (26, '周深', '周深，1992年9月29日出生于湖南邵阳，长于贵州贵阳，中国内地男歌手，中国青年歌唱家，2016年6月毕业于乌克兰利沃夫国立立谢科音乐科学院美声男高音专业，全A毕业。\r\n\r\n2014年参加浙江卫视《中国好声音》第三季初次登场；2015年8月发行个人全新单曲《玫瑰与小鹿》，获2016年东方风云榜最佳新人。2016年献唱动画电影《大鱼海棠》印象曲《大鱼》；《玫瑰与小鹿》和《大鱼》双双获得亚洲新歌榜年度十大金曲。\r\n\r\n2017年11月发行个人首张专辑《深的深》；2018年9月发行首支个人作曲原创作品《可它爱着这个世界》；先后献唱奥斯卡最佳影片《水形物语》 、《触不可及》（美版）电影同名推广曲、电影《侍神令》主题曲《归处》、电视剧《鹤唳华亭》主题曲《愿得一心人》 、《锦衣之下》主题曲《愿》 、《大江大河2》主题曲《和光同尘》、《风起霓裳》主题曲《明月传说》等。\r\n\r\n分别于2018-2019年度举办“深空间”、2019-2020年度举办”C-929星球“个人巡回演唱会；先后获得2018年全球华人歌曲排行榜年度最佳新人 、MTV EMA最受欢迎大中华区艺人、2020TMEA腾讯音乐娱乐盛典年度最佳内地男歌手等奖项；参加江苏卫视《蒙面唱将猜猜猜》第一季和第三季、东方卫视《我们的歌》、湖南卫视2020《歌手·当打之年》等综艺节目，演唱《身骑白马》、《雪落下的声音》、《天下有情人》、《达拉崩吧》等曲目，参加2021年中央电视台春节联欢晚会，与张也合唱《灯火里的中国》。\r\n\r\n2020年7月，周深成立个人工作室；2020年8月，周深名列《2020福布斯中国名人榜》第42位。', 'http://127.0.0.1:9000/music/cover/1/26_1735040212999.jpg', 1, 1, '2024-12-24 19:36:53', 1, '2024-12-24 19:36:53', '内地', 'a');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `other_id` int NULL DEFAULT NULL,
  `type` int NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (1, 1, 4, 5, '测试评论', '2024-12-21 00:00:00', 1, '2024-12-21 00:00:00', 1, 0);
INSERT INTO `comments` VALUES (9, 2, 1, 4, '测试子评论', '2024-12-24 13:52:43', 2, '2024-12-24 13:52:43', 2, 0);
INSERT INTO `comments` VALUES (10, 2, 1, 4, '1', '2024-12-24 13:57:10', 2, '2024-12-24 13:57:10', 2, 1);
INSERT INTO `comments` VALUES (11, 2, 1, 4, '12', '2024-12-24 14:15:36', 2, '2024-12-24 14:15:36', 2, 1);
INSERT INTO `comments` VALUES (12, 2, 4, 5, '123', '2024-12-25 12:36:38', 2, '2024-12-25 12:36:38', 2, 0);
INSERT INTO `comments` VALUES (13, 2, 4, 5, '123', '2024-12-26 16:24:55', 2, '2024-12-26 16:24:55', 2, 0);
INSERT INTO `comments` VALUES (14, 2, 7, 5, '1', '2025-03-22 10:35:09', 2, '2025-03-22 10:35:09', 2, 0);
INSERT INTO `comments` VALUES (15, 2, 14, 4, '1', '2025-03-22 10:35:16', 2, '2025-03-22 10:35:16', 2, 14);

-- ----------------------------
-- Table structure for dynamics
-- ----------------------------
DROP TABLE IF EXISTS `dynamics`;
CREATE TABLE `dynamics`  (
  `dynamic_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `music_id` int NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`dynamic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dynamics
-- ----------------------------
INSERT INTO `dynamics` VALUES (1, 1, '测试🏷️ Engineering\n🎵 Song 1 by Artist 1\n', 1, 1, '2024-12-05 15:45:03', 1, '2024-12-05 15:45:03');
INSERT INTO `dynamics` VALUES (2, 1, '哈哈哈😄 Happy\n', 31, 1, '2024-12-05 16:24:03', 1, '2024-12-05 16:24:03');
INSERT INTO `dynamics` VALUES (3, 1, '1', 1, 1, '2024-12-10 15:24:21', 1, '2024-12-10 15:24:21');
INSERT INTO `dynamics` VALUES (4, 2, '今天天气真好! #Engineering#', 33, 2, '2024-12-18 22:23:34', 2, '2024-12-18 22:23:34');
INSERT INTO `dynamics` VALUES (5, 2, '第二条动态#Engineering#', 29, 2, '2024-12-21 17:03:36', 2, '2024-12-21 17:03:36');
INSERT INTO `dynamics` VALUES (6, 2, '哈哈哈', 32, 2, '2025-03-22 10:20:25', 2, '2025-03-22 10:20:25');
INSERT INTO `dynamics` VALUES (7, 2, 'test', 29, 2, '2025-03-22 10:22:59', 2, '2025-03-22 10:22:59');

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `user_id` int NOT NULL,
  `type` int NULL DEFAULT NULL,
  `content_id` int NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `favorite_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`favorite_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES (2, 2, 1005, 2, '2024-12-24 16:34:53', 2, '2024-12-24 16:34:53', 1);
INSERT INTO `favorites` VALUES (2, 2, 1021, 2, '2025-03-22 09:47:08', 2, '2025-03-22 09:47:08', 2);
INSERT INTO `favorites` VALUES (2, 2, 1007, 2, '2025-03-22 09:48:22', 2, '2025-03-22 09:48:22', 3);
INSERT INTO `favorites` VALUES (2, 2, 1012, 2, '2025-03-23 11:01:05', 2, '2025-03-23 11:01:05', 4);
INSERT INTO `favorites` VALUES (2, 2, 1015, 2, '2025-03-23 11:01:37', 2, '2025-03-23 11:01:37', 5);
INSERT INTO `favorites` VALUES (7, 2, 1015, 7, '2025-03-23 11:50:00', 7, '2025-03-23 11:50:00', 6);
INSERT INTO `favorites` VALUES (7, 2, 1015, 7, '2025-03-23 12:00:09', 7, '2025-03-23 12:00:09', 10);
INSERT INTO `favorites` VALUES (3, 2, 1010, 3, '2025-03-23 13:40:41', 3, '2025-03-23 13:40:41', 11);
INSERT INTO `favorites` VALUES (8, 2, 1005, 8, '2025-03-23 13:41:57', 8, '2025-03-23 13:41:57', 12);
INSERT INTO `favorites` VALUES (10, 2, 1, 10, '2025-03-23 13:48:09', 10, '2025-03-23 13:48:09', 13);
INSERT INTO `favorites` VALUES (11, 2, 1007, 11, '2025-03-23 13:48:22', 11, '2025-03-23 13:48:22', 14);
INSERT INTO `favorites` VALUES (13, 2, 1021, 13, '2025-03-23 13:48:37', 13, '2025-03-23 13:48:37', 15);
INSERT INTO `favorites` VALUES (14, 2, 1008, 14, '2025-03-23 13:48:49', 14, '2025-03-23 13:48:49', 16);
INSERT INTO `favorites` VALUES (15, 2, 1021, 15, '2025-03-23 13:49:06', 15, '2025-03-23 13:49:06', 17);
INSERT INTO `favorites` VALUES (2, 2, 1, 2, '2025-03-23 13:49:57', 2, '2025-03-23 13:49:57', 18);
INSERT INTO `favorites` VALUES (2, 2, 1008, 2, '2025-03-23 13:50:01', 2, '2025-03-23 13:50:01', 19);
INSERT INTO `favorites` VALUES (2, 2, 1016, 2, '2025-03-23 13:51:34', 2, '2025-03-23 13:51:34', 20);
INSERT INTO `favorites` VALUES (3, 2, 1008, 3, '2025-03-23 13:56:50', 3, '2025-03-23 13:56:50', 21);
INSERT INTO `favorites` VALUES (4, 2, 1008, 4, '2025-03-23 14:27:32', 4, '2025-03-23 14:27:32', 22);
INSERT INTO `favorites` VALUES (4, 2, 1016, 4, '2025-03-23 14:27:57', 4, '2025-03-23 14:27:57', 23);
INSERT INTO `favorites` VALUES (2, 2, 1014, 2, '2025-03-23 14:47:57', 2, '2025-03-23 14:47:57', 24);
INSERT INTO `favorites` VALUES (3, 2, 1021, 3, '2025-03-23 14:57:01', 3, '2025-03-23 14:57:01', 25);

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `follow_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `follow_user_id` int NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES (1, 2, 24, 2, '2024-12-18 14:25:49', 2, '2024-12-18 14:25:55', 1);
INSERT INTO `follow` VALUES (2, 2, 2, 2, '2024-12-19 21:29:04', 2, '2024-12-19 21:29:04', 2);
INSERT INTO `follow` VALUES (3, 2, 2, 2, '2024-12-20 19:26:57', 2, '2024-12-20 19:26:57', 2);
INSERT INTO `follow` VALUES (5, 2, 21, 2, '2024-12-26 16:25:21', 2, '2024-12-26 16:25:21', 1);

-- ----------------------------
-- Table structure for genres
-- ----------------------------
DROP TABLE IF EXISTS `genres`;
CREATE TABLE `genres`  (
  `genre_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  PRIMARY KEY (`genre_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of genres
-- ----------------------------
INSERT INTO `genres` VALUES (1, '流行', '这是一个分类', '2011-05-07 10:59:53', 1, 1, '2012-02-14 09:18:43', 3);
INSERT INTO `genres` VALUES (2, '摇滚', '这是一个分类', '2024-12-18 20:36:46', 1, 1, '2025-03-21 21:16:36', 3);
INSERT INTO `genres` VALUES (3, '电子', '这是一个分类', '2024-12-18 20:36:48', 1, 1, '2025-03-21 21:16:38', 3);
INSERT INTO `genres` VALUES (4, '流行', '这是一个分类', '2011-05-07 10:59:53', 1, 1, '2012-02-14 09:18:43', 1);
INSERT INTO `genres` VALUES (5, '摇滚', '这是一个分类', '2024-12-18 15:23:41', 1, 1, '2024-12-18 15:23:45', 1);
INSERT INTO `genres` VALUES (6, '电子', '这是一个分类', '2024-12-18 15:23:43', 1, 1, '2024-12-18 15:23:46', 1);
INSERT INTO `genres` VALUES (7, '内地', '这是一个分类', '2011-05-07 10:59:53', 1, 1, '2012-02-14 09:18:43', 2);
INSERT INTO `genres` VALUES (8, '港台', '这是一个分类', '2024-12-18 15:23:41', 1, 1, '2024-12-18 15:23:45', 2);
INSERT INTO `genres` VALUES (9, '欧美', '这是一个分类', '2024-12-18 15:23:43', 1, 1, '2024-12-18 15:23:46', 2);
INSERT INTO `genres` VALUES (10, '日本', '这是一个分类', '2024-12-18 15:48:37', 1, 1, '2024-12-18 15:48:47', 2);
INSERT INTO `genres` VALUES (11, '韩国', '这是一个分类', '2024-12-18 15:48:39', 1, 1, '2024-12-20 15:48:48', 2);
INSERT INTO `genres` VALUES (12, '流行', '这是一个分类', '2024-12-18 19:41:56', 1, 1, '2024-12-18 19:42:04', 4);
INSERT INTO `genres` VALUES (13, '未知分类', ' ', '2024-12-19 17:11:02', 1, 1, '2024-12-19 17:11:07', 0);
INSERT INTO `genres` VALUES (21, '伤感', '这是一个类型', '2024-12-24 16:05:42', 1, 1, '2024-12-24 16:05:42', 1);
INSERT INTO `genres` VALUES (22, '思念', '这是一个描述', '2024-12-24 16:06:27', 1, 1, '2024-12-24 16:06:27', 3);
INSERT INTO `genres` VALUES (23, '民谣', '这是一个描述', '2024-12-24 16:07:51', 1, 1, '2024-12-24 16:07:51', 3);
INSERT INTO `genres` VALUES (24, '国语', '这是一个描述111', '2024-12-24 16:08:39', 1, 1, '2024-12-24 16:12:07', 1);
INSERT INTO `genres` VALUES (25, '治愈', '111', '2024-12-24 16:17:50', 1, 1, '2024-12-24 16:17:50', 1);

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `label_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL COMMENT '0-歌单 1-歌曲',
  `content_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`label_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES (1, '伤感', '111', 1, '2025-03-01 09:54:28', 1, '2025-03-22 09:54:32', 0, 1);
INSERT INTO `label` VALUES (2, '国语', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1005);
INSERT INTO `label` VALUES (3, '网络歌曲', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1007);
INSERT INTO `label` VALUES (4, '背景音乐', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1008);
INSERT INTO `label` VALUES (5, '流行', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1009);
INSERT INTO `label` VALUES (6, '摇滚', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1010);
INSERT INTO `label` VALUES (7, '民谣', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1011);
INSERT INTO `label` VALUES (8, '古典', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1012);
INSERT INTO `label` VALUES (9, '爵士', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1013);
INSERT INTO `label` VALUES (10, '乡村', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1014);
INSERT INTO `label` VALUES (11, '世界音乐', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1015);
INSERT INTO `label` VALUES (12, '金属', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1016);
INSERT INTO `label` VALUES (13, '朋克', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1021);
INSERT INTO `label` VALUES (14, '新世纪', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1);
INSERT INTO `label` VALUES (15, '古风', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1005);
INSERT INTO `label` VALUES (16, '轻音乐', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1007);
INSERT INTO `label` VALUES (17, 'R&B', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1009);
INSERT INTO `label` VALUES (18, '古筝', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1010);
INSERT INTO `label` VALUES (19, '嘻哈', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1011);
INSERT INTO `label` VALUES (20, '蓝调', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1012);
INSERT INTO `label` VALUES (21, '拉丁', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1013);
INSERT INTO `label` VALUES (22, '氛围音乐', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1014);
INSERT INTO `label` VALUES (23, '蓝草', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1016);
INSERT INTO `label` VALUES (24, '城市音乐', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1015);
INSERT INTO `label` VALUES (25, '后摇', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1021);
INSERT INTO `label` VALUES (26, '后摇', '222', 1, '2025-03-22 09:55:14', 1, '2025-03-22 09:55:20', 0, 1015);

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `like_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `content_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of likes
-- ----------------------------
INSERT INTO `likes` VALUES (22, 2, 1, 28, '2024-12-19 17:46:16', 2, 2, '2024-12-19 17:46:16');
INSERT INTO `likes` VALUES (25, 2, 1, 33, '2024-12-19 19:31:18', 2, 2, '2024-12-19 19:31:18');

-- ----------------------------
-- Table structure for playlist_songs
-- ----------------------------
DROP TABLE IF EXISTS `playlist_songs`;
CREATE TABLE `playlist_songs`  (
  `playlist_id` int NOT NULL,
  `song_id` int NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`playlist_id`, `song_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of playlist_songs
-- ----------------------------
INSERT INTO `playlist_songs` VALUES (1, 1, '2024-12-20 16:06:52', 1, 1, '2024-12-20 16:07:14');
INSERT INTO `playlist_songs` VALUES (1, 28, '2024-12-20 16:06:54', 1, 1, '2024-12-20 16:07:18');
INSERT INTO `playlist_songs` VALUES (1, 29, '2024-12-20 16:06:56', 1, 1, '2024-12-20 16:07:20');
INSERT INTO `playlist_songs` VALUES (1, 30, '2024-12-20 16:07:33', 1, 1, '2024-12-20 16:07:21');
INSERT INTO `playlist_songs` VALUES (1, 31, '2024-12-20 16:07:34', 1, 1, '2024-12-20 16:07:22');
INSERT INTO `playlist_songs` VALUES (1, 32, '2024-12-20 16:07:36', 1, 1, '2024-12-20 16:07:23');
INSERT INTO `playlist_songs` VALUES (1, 33, '2024-12-20 16:07:37', 1, 1, '2024-12-20 16:07:24');
INSERT INTO `playlist_songs` VALUES (1005, 1, '2024-12-20 16:07:38', 1, 1, '2024-12-20 16:07:26');
INSERT INTO `playlist_songs` VALUES (1007, 1, '2024-12-20 16:07:40', 1, 1, '2024-12-20 16:07:28');
INSERT INTO `playlist_songs` VALUES (1008, 1, '2024-12-20 16:07:41', 1, 1, '2024-12-20 16:07:30');
INSERT INTO `playlist_songs` VALUES (1009, 1, '2024-12-20 16:07:43', 1, 1, '2024-12-20 16:07:31');
INSERT INTO `playlist_songs` VALUES (1010, 1, '2024-12-20 16:07:44', 1, 1, '2024-12-20 16:07:32');
INSERT INTO `playlist_songs` VALUES (1016, 31, '2025-03-22 11:39:46', 1, 1, '2025-03-22 11:39:54');
INSERT INTO `playlist_songs` VALUES (1020, 1, '2024-12-20 16:31:56', 2, 2, '2024-12-20 16:31:56');
INSERT INTO `playlist_songs` VALUES (1020, 33, '2024-12-20 16:34:39', 2, 2, '2024-12-20 16:34:39');
INSERT INTO `playlist_songs` VALUES (1021, 29, '2025-03-22 09:34:02', 1, 1, '2025-03-22 09:34:09');

-- ----------------------------
-- Table structure for playlists
-- ----------------------------
DROP TABLE IF EXISTS `playlists`;
CREATE TABLE `playlists`  (
  `playlist_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `song_count` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `play_count` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`playlist_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1021 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of playlists
-- ----------------------------
INSERT INTO `playlists` VALUES (1, 1, '伤感片段｜红着脸相遇.红着眼散', '我也不知道为什么喜欢你，喜欢你什么，有什么让我喜欢的，可我就是喜欢你，很喜欢你，非常喜欢你，越来越喜欢你。', 'http://127.0.0.1:9000/music/cover/3/1_1734695860319.webp', 0, '2024-12-18 21:55:56', 1, 1, '2024-12-18 21:56:11', 1, 00000000000000000107, 1);
INSERT INTO `playlists` VALUES (2, 1, '热歌榜', '官方歌单', 'http://127.0.0.1:9000/music/cover/3/2_1735010793988.jpg', 0, '2024-12-20 12:48:32', 1, 1, '2024-12-20 12:49:03', 0, 00000000000000000090, 1);
INSERT INTO `playlists` VALUES (3, 1, '新歌榜', '官方歌单', 'http://127.0.0.1:9000/music/cover/3/3_1735010803441.jpg', 0, '2024-12-22 13:51:03', 1, 1, '2024-12-22 13:51:08', 0, 00000000000000000056, 1);
INSERT INTO `playlists` VALUES (1005, 2, '全世界最好的猫猫音乐', '你有哪些珍藏的和猫咪有关的可爱音乐？ 点击右下角的投稿，一起在歌单里“云养猫”吧～ (>^ω^<)', 'http://127.0.0.1:9000/music/cover/3/1005_1734695957167.webp', 0, '2024-11-24 12:38:31', 1, 1, '2024-11-24 12:38:31', 2, 00000000000000000022, 1);
INSERT INTO `playlists` VALUES (1007, 2, '有哪些乐器生来就代表自由？', '每种乐器，每种情绪 你可以从钢琴中听到流淌的河流，温柔的絮语 你可以从小提琴中听到缠绵的爱恋与纠葛 又或者是从吉他声中听到淡淡的诉说，淡淡的忧愁 无论是哪一种乐器，都带着一种属于它的风格与滋味 那么，又有哪些乐器是生来就带着自由呢？ 驰骋草原？又或者是翱翔天空，深潜入海？ “欢迎朋友们一起参与互建歌单，可以点击歌单右下角投稿按钮，一起寻找自由的乐器吧！”', 'http://127.0.0.1:9000/music/cover/3/1007_1734696063950.webp', 0, '2024-11-24 12:46:20', 1, 1, '2024-11-24 12:46:20', 3, 00000000000000000023, 1);
INSERT INTO `playlists` VALUES (1008, 2, '假期出行氛围感max的歌', '“诶，路上听什么歌？” “不知道，随便吧？” “不如听听这张歌单？” “什么歌单？” “《假期出行氛围感Max的歌》里面有好多其他人投稿的歌，氛围真不错” “✅，就它了” 笑～～“欢迎朋友们点击右下角投稿你们的假期出行氛围感歌曲，共建这张氛围歌单”', 'http://127.0.0.1:9000/music/cover/3/1008_1734696120033.webp', 0, '2024-11-25 15:44:49', 1, 1, '2024-11-25 15:44:49', 1, 00000000000000000012, 1);
INSERT INTO `playlists` VALUES (1009, 1, '说到中式梦核，你会想起什么歌？', '千禧一代人的成长轨迹正好与社会发展最快速的十年重合，那些深深烙印在童年时光中的印记，被时代的洪流逐一冲刷洗净，逐渐变得难以寻觅。 模糊的老照片、老式电视机与DVD、依稀能辨认出年代的家具和装潢……那些仿佛来自千禧年间的旧物件与意义不明的文字交织，勾勒出一种独特的氛围，让人既熟悉又陌生…… 说起中式梦核，不知道是谁的童年DNA动了？关于那个年代的独特的气息不只有视觉，也有听觉，不知道你会想起什么歌？ “友友们可以点击歌单右下角投稿按钮，分享你的认为的中式梦核物语之歌”', 'http://127.0.0.1:9000/music/cover/3/1009_1734696159988.webp', 0, '2024-11-25 15:45:03', 1, 1, '2024-11-25 15:45:03', 2, 00000000000000000022, 1);
INSERT INTO `playlists` VALUES (1010, 1, '听了这些歌爱上了民谣', '民谣是什么？ 民谣是最安静的角落。 民谣是一场场遇见与离别。 民谣是一种曲风，民谣歌词写的贴切生活又细腻走心。 民谣是衣柜里那件永远不前卫也永远不落伍，而且是上身率最高的Ｔ恤。简单、质朴、舒适、惬意。在一夜成名的时代，民谣朴素真诚有着生活的粗糙，但又直抵人心的力量。 一个人， 听过喧嚣，看过悲喜 为物质而奔波 麻木了精神，冷漠了生活 或许 一首歌响起 就能重新唤起内心的炽热 唤起你对除物质外的追求 一段终生难忘的恋爱 一场说走就走的旅行 一次没有遗憾的青春 一个你所向往的世界 希望这单能给想听民谣而又无从入耳的朋友们一个索引，更加了解民谣。', 'http://127.0.0.1:9000/music/cover/3/1010_1734696212113.webp', 0, '2024-11-25 15:49:19', 1, 1, '2024-11-25 15:49:19', 3, 00000000000000000021, 1);
INSERT INTO `playlists` VALUES (1011, 2, '国风戏腔 · 人心多变，幻中吞幻', '你犹豫了，你在怕吗？你的鹅丢了，你眼里的光没了，你只是一个货郎，可你还是原来的货郎吗。欲望接踵而至，欲望吞噬欲望，她想要逃出“鹅笼”，但何尝不是进入新的“鹅笼”呢。', 'http://127.0.0.1:9000/music/cover/3/1011_1734696249038.webp', 0, '2024-11-25 15:44:49', 1, 1, '2024-11-25 15:44:49', 1, 00000000000000000044, 1);
INSERT INTO `playlists` VALUES (1012, 1, '黑帮浪潮：硬核节奏感，低音超劲', '有没有曾梦想过成为大佬，走出自己的专属bgm身边的兄弟陪你一起叱咤风云，一起征战四方，硬核的节奏，舞动的心脏，迈开步子仗剑走天涯！该走走，该留留，今后一律不强求；该吃吃，该喝喝，破事不往心里搁。   喜欢歌单的可以多多收藏收藏❤️', 'http://127.0.0.1:9000/music/cover/3/1012_1734696301694.webp', 0, '2024-11-25 15:45:03', 1, 1, '2024-11-25 15:45:03', 2, 00000000000000000099, 1);
INSERT INTO `playlists` VALUES (1013, 1, '大佬气场！匪气与霸气的极致碰撞', '匪气、大气和霸气的超强气场来袭 ！硬核节奏感爆发带来的极致体验，超震撼的音律直击心灵，让我们一起随着节奏，感受暗黑风暴带来的热血能量气场，也可以视为逆袭路上的精神氮泵——改变从现在开始', 'http://127.0.0.1:9000/music/cover/3/1013_1734696338391.webp', 0, '2024-11-25 15:49:19', 1, 1, '2024-11-25 15:49:19', 3, 00000000000000000888, 1);
INSERT INTO `playlists` VALUES (1014, 2, '温柔华语 | 把故事藏在歌中', '好听的歌，就是要循环！循环！再循环！这些耐听好歌送给大家，真的会越听越爱喔~', 'http://127.0.0.1:9000/music/cover/3/1014_1734696382277.webp', 0, '2024-11-25 15:44:49', 1, 1, '2024-11-25 15:44:49', 1, 00000000000000000152, 1);
INSERT INTO `playlists` VALUES (1015, 1, '伤感片段 : 别隔着屏幕爱她了', '最遗憾的可能就是… 隔着屏幕认识你。 隔着屏幕爱上你。 最后。隔着屏幕失去你。 这一切都隔着屏幕。 从一开始我就无能为力。', 'http://127.0.0.1:9000/music/cover/3/1015_1734696425229.webp', 0, '2024-11-25 15:45:03', 1, 1, '2024-11-25 15:45:03', 2, 00000000000000000123, 1);
INSERT INTO `playlists` VALUES (1016, 1, '空灵童音丨挥着翅膀的女孩', '古老的童谣说：每个女孩子都是由糖香料以及一切美好的东西做成的，仅比天使差一点。 美是什么呢？ 是一小部分女孩出生时，上天额外赠送的玫瑰。有人将它拧做绳，攀上天；有人枕着入睡，不幸刺破喉；有人捧在嘴边日日炫耀，花儿沾了唾液，落了俗，败了色；还有人，并不拿它做什么，只插进水瓶，藏在角落，静静养着；到逝去了，她还是她。', 'http://127.0.0.1:9000/music/cover/3/1016_1734696483973.webp', 0, '2024-11-25 15:49:19', 1, 1, '2024-11-25 15:49:19', 3, 00000000000000000014, 1);
INSERT INTO `playlists` VALUES (1021, 1, '让耳朵住进海里的歌', '海总是寄托着我们心中那份深远又浪漫的幻想，探寻着我们悠长又私密的心事。闭上眼，打开音乐，感受温热的海风吹过，那些有关夏日的美好记忆正被唤醒，让我们一起让耳朵住进海里说到大海，你会想起哪些歌？快来投稿分享吧！', 'http://127.0.0.1:9000/music/cover/3/null_1735039368331.webp', 0, '2024-12-24 19:22:48', 1, 1, '2024-12-24 19:22:48', 3, 00000000000000000058, 1);

-- ----------------------------
-- Table structure for songs
-- ----------------------------
DROP TABLE IF EXISTS `songs`;
CREATE TABLE `songs`  (
  `song_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `artist_id` int NULL DEFAULT NULL,
  `album_id` int NULL DEFAULT NULL,
  `genre_id` int NULL DEFAULT NULL,
  `duration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `cover_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lyric_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `play_count` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  PRIMARY KEY (`song_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of songs
-- ----------------------------
INSERT INTO `songs` VALUES (1, '5_20AM(我在5_20睡觉13_14准时起)', 1, 1, 1, '2:41', 'http://127.0.0.1:9000/music/file/27_1732354594236.mp3', '2024-11-23 17:36:34', 1, 1, '2024-11-23 17:36:34', 'http://127.0.0.1:9000/music/cover/1/27_1732354594609.jpg', 'http://127.0.0.1:9000/music/lyric/27_1732354594620.lyric', 00000000000000000014);
INSERT INTO `songs` VALUES (28, '在你的身边', 25, 6, 1, '4:22', 'http://127.0.0.1:9000/music/file/28_1733056947226.mp3', '2024-12-01 20:42:27', 1, 1, '2024-12-01 20:42:27', 'http://127.0.0.1:9000/music/cover/1/28_1733056948133.jpg', 'http://127.0.0.1:9000/music/lyric/28_1733056948142.lyric', 00000000000000000000);
INSERT INTO `songs` VALUES (29, '银河飞车', 24, 5, 1, '4:19', 'http://127.0.0.1:9000/music/file/29_1733057019548.mp3', '2024-12-01 20:43:40', 1, 1, '2024-12-01 20:43:40', 'http://127.0.0.1:9000/music/cover/1/29_1733057020002.jpg', 'http://127.0.0.1:9000/music/lyric/29_1733057020012.lyric', 00000000000000000000);
INSERT INTO `songs` VALUES (30, '夏季备忘录（Prod by WayMen）', 23, 4, 1, '2:58', 'http://127.0.0.1:9000/music/file/30_1733057098059.mp3', '2024-12-01 20:44:58', 1, 1, '2024-12-01 20:44:58', 'http://127.0.0.1:9000/music/cover/1/30_1733057098504.jpg', 'http://127.0.0.1:9000/music/lyric/30_1733057098510.lrc', 00000000000000000000);
INSERT INTO `songs` VALUES (31, '我们俩', 21, 2, 1, '3:13', 'http://127.0.0.1:9000/music/file/31_1733057157137.mp3', '2024-12-01 20:45:57', 1, 1, '2024-12-01 20:45:57', 'http://127.0.0.1:9000/music/cover/1/31_1733057157515.jpg', 'http://127.0.0.1:9000/music/lyric/31_1733057157519.lrc', 00000000000000000000);
INSERT INTO `songs` VALUES (32, '忽然之间', 22, 3, 1, '2:44', 'http://127.0.0.1:9000/music/file/32_1733057201171.mp3', '2024-12-01 20:46:41', 1, 1, '2024-12-01 20:46:41', 'http://127.0.0.1:9000/music/cover/1/32_1733057201514.jpg', 'http://127.0.0.1:9000/music/lyric/32_1733057201522.lyric', 00000000000000000000);
INSERT INTO `songs` VALUES (33, '我的711', 24, 7, 1, '4:12', 'http://127.0.0.1:9000/music/file/我的711_1734583955466.mp3', '2024-12-09 13:59:23', 1, 1, '2024-12-09 13:59:23', 'http://127.0.0.1:9000/music/cover/1/33_1733723967043.jpg', 'http://127.0.0.1:9000/music/lyric/33_1733723967054.lrc', 00000000000000000000);
INSERT INTO `songs` VALUES (34, '还是分开', 24, 2, 4, '03:46', 'http://127.0.0.1:9000/music/file/34_1735038935618.flac', '2024-12-24 19:15:36', NULL, NULL, '2024-12-24 19:15:36', 'http://127.0.0.1:9000/music/cover/4/34_1735038939052.jpg', 'http://127.0.0.1:9000/music/lyric/34_1735038939059.lrc', NULL);

-- ----------------------------
-- Table structure for user_behavior
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior`;
CREATE TABLE `user_behavior`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `playlist_id` int NOT NULL,
  `behavior_type` enum('PLAY','COLLECT','SHARE','BROWSE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `original_value` int NULL DEFAULT 0 COMMENT '原始行为值',
  `normalized_value` decimal(7, 4) NOT NULL DEFAULT 0.0000 COMMENT '归一化值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior
-- ----------------------------
INSERT INTO `user_behavior` VALUES (58, 2, 1008, 'BROWSE', 15, 1.0000);
INSERT INTO `user_behavior` VALUES (59, 3, 1016, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (60, 3, 1008, 'BROWSE', 14, 1.0000);
INSERT INTO `user_behavior` VALUES (61, 4, 1005, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (62, 4, 1016, 'BROWSE', 8, 1.0000);
INSERT INTO `user_behavior` VALUES (63, 4, 1008, 'BROWSE', 6, 0.7143);
INSERT INTO `user_behavior` VALUES (64, 2, 1016, 'PLAY', 12, 0.7857);
INSERT INTO `user_behavior` VALUES (65, 2, 1005, 'BROWSE', 3, 0.1429);
INSERT INTO `user_behavior` VALUES (66, 8, 1011, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (67, 7, 1009, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (68, 9, 1007, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (69, 10, 1, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (70, 11, 1010, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (71, 12, 1007, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (72, 13, 1014, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (73, 14, 1015, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (74, 2, 1014, 'BROWSE', 6, 0.3571);
INSERT INTO `user_behavior` VALUES (75, 15, 1021, 'BROWSE', 10, 0.0000);
INSERT INTO `user_behavior` VALUES (76, 2, 1021, 'PLAY', 11, 0.7143);
INSERT INTO `user_behavior` VALUES (77, 3, 1021, 'COLLECT', 4, 0.2308);
INSERT INTO `user_behavior` VALUES (78, 2, 1, 'BROWSE', 1, 0.0000);
INSERT INTO `user_behavior` VALUES (79, 2, 1007, 'BROWSE', 1, 0.0000);

-- ----------------------------
-- Table structure for user_songs
-- ----------------------------
DROP TABLE IF EXISTS `user_songs`;
CREATE TABLE `user_songs`  (
  `user_songs_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `singer` int NULL DEFAULT NULL,
  `song_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `album` int NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `size` double NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_songs_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_songs
-- ----------------------------
INSERT INTO `user_songs` VALUES (1, 2, '', '我的711', 2, 'http://127.0.0.1:9000/music/file/我的711_1734584003481.mp3', 8, 2, 9, 'mp3', '2024-12-19 12:53:24', 2, '2024-12-19 12:53:24');
INSERT INTO `user_songs` VALUES (3, 2, '', '小乐哥 - 顽疾', 2, 'http://127.0.0.1:9000/music/file/小乐哥 - 顽疾_1734584776142.flac', 8, 2, 17, 'flac', '2024-12-19 13:06:17', 2, '2024-12-19 13:06:17');
INSERT INTO `user_songs` VALUES (4, 2, '', '我的711', 2, 'http://127.0.0.1:9000/music/file/我的711_1734584003481.mp3', 8, 2, 9, 'mp3', '2024-12-22 14:28:34', 2, '2024-12-22 14:28:34');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `update_user` int NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nationality` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin', '2875013802@qq.com', '2024-10-23 00:00:00', '2024-12-21 13:04:43', 1, 1, 'http://127.0.0.1:9000/music/cover/1/1005_1732423111003.jpg', '用户很神秘~', '上海', 1, 1);
INSERT INTO `users` VALUES (2, 'and', '123456', '2875013802@qq.com', '2024-11-20 15:47:48', '2024-11-20 15:47:48', 1, 1, 'http://127.0.0.1:9000/music/cover/5/2_1734757836126.jpg', '用户很神秘~', '北京', 0, 1);
INSERT INTO `users` VALUES (3, 'test', '123456', '2875013802@qq.com', '2025-03-21 23:14:23', '2025-03-21 23:14:24', 1, 1, 'http://127.0.0.1:9000/music/cover/5/2_1734757836126.jpg', '用户很神秘~', '武汉', 0, 1);
INSERT INTO `users` VALUES (4, 'test2', '123456', '2875013802@qq.com', '2025-03-22 10:56:12', '2025-03-22 10:56:12', 1, 4, 'http://127.0.0.1:9000/music/cover/5/4_1742614411231.png', '用户很神秘~', 'US', 0, 1);
INSERT INTO `users` VALUES (7, 'test3', '123456', '2875013802@qq.com', '2025-03-22 10:57:48', '2025-03-22 10:57:48', 1, 1, NULL, '用户很神秘~', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (8, 'test1', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (9, 'test4', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (10, 'test5', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (11, 'test6', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (12, 'test7', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (13, 'test8', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (14, 'test9', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);
INSERT INTO `users` VALUES (15, 'test10', '123456', '2875013802@qq.com', '2025-03-22 11:10:41', '2025-03-22 11:10:41', 1, 1, NULL, '用户很懒, 暂无更多数据', '暂无更多数据', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
