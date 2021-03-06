--创建数据库
create database snack default character set utf8 collate utf8_bin;

--切换到snack库
use snack;

--创建后台管理员信息表
create table if not exists adminInfo(
	aid int primary key auto_increment=1, --管理员编号，主键，自增
	aname varchar(100) not null unique,  --管理员昵称  唯一且非空
	pwd varchar(100) not null,   --密码不为空
	tel varchar(15) unique,   --联系方式，要么没有，要么为空
	status int
) ENGINE = InnoDB AUTO_INCREMENT =101 default charset=utf8 collate=utf8_bin;

--创建商品类型表
create  table if not exists goodsType(
	tid int primary key auto_increment,  --类型编号
	tname varchar(100) not null unique,  --类型，名称
	status int -- 类型状态

)ENGINE = InnoDb AUTO_INCREMENT =101 default charset=utf8 collate=utf8_bin;


--创建商品信息表
create table if not exists goodsInfo(

	gid int primary key auto_increment,
	gname varchar(100) not null, --商品名称
	tid int,
	price decimal(8,2),  --商品价格
	intro varchar(200),  --商品简介
	balance int ,  --商品库存
	pics varchar(50),   --商品图片
	unit varchar(50),   --单位
	qperied varchar(50),  --保质期
	weight varchar(50),  --净重
	descr text,  --商品详情
	status int, -- 商品状态
	constraint FK_goodsInfo_tid foreign key(tid) references goodsType(tid)

)ENGINE = InnoDB AUTO_INCREMENT = 1 default charset = utf8 collate=utf8_bin;