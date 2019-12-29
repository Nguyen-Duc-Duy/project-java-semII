CREATE DATABASE XiXaoManger
GO
USE XiXaoManger
GO
-- bảng nhân viên
CREATE TABLE employers
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
	email VARCHAR(265) NULL,
	pass VARCHAR(32) NOT NULL DEFAULT '1',
	avt NVARCHAR(265) NULL DEFAULT 'avt_user.png',
	phone VARCHAR(10) NULL,
	[status] TINYINT DEFAULT 1,
	date_created DATETIME,
	date_updated DATETIME
)
SELECT * FROM employers

-- bảng phân quyền

-- tạo bảng danh mục 
CREATE TABLE categorys
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
	id_parent INT DEFAULT 0,
	[status] TINYINT DEFAULT 1,
	date_created DATETIME,
	date_updated DATETIME
)
GO
SELECT * FROM categorys

-- tạo bảng sản phẩm
CREATE TABLE products
(
	id  INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
	code VARCHAR(100) NULL,
	id_cat INT REFERENCES categorys(id),
	price FLOAT NOT NULL,
	sale FLOAT NULL,
	branch NVARCHAR(68) NULL,
	descript NVARCHAR(120) NULL,
	quantity INT DEFAULT 0,
	img VARCHAR(265) DEFAULT 'product.png',
	id_supplier INT NOT NULL,
	id_unit INT NOT NULL,
	[status] TINYINT DEFAULT 1,
	date_crated DATETIME,
	date_updated DATETIME
)
GO
SELECT * FROM products

-- tạo bảng nhà cung cấp
CREATE TABLE suppliers
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
	phone VARCHAR(10) NULL,
	[address] NVARCHAR(128) NULL,
	descript NVARCHAR(265) NULL,
	fax VARCHAR(16) NULL,
	email VARCHAR(265) NULL,
	website VARCHAR(265) NULL,
	[status] TINYINT DEFAULT 1,
	date_created DATETIME,
	date_update DATETIME
)
GO
ALTER TABLE products
ADD FOREIGN KEY (id_supplier) REFERENCES suppliers
SELECT * FROM suppliers

-- tạo bảng đơn vị sản phẩm
CREATE TABLE units
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
	descript NVARCHAR(265),
	date_created	DATETIME,
	date_updated	DATETIME
)
GO
ALTER TABLE products
ADD FOREIGN KEY (id_unit) REFERENCES units(id)
SELECT * FROM units

-- thêm quyền admin tổng
insert into employers(name,email,pass) values (N'Nguyễn Đức Duy','duy@gmail.com','1')

-- tạo bảng nhóm quyền
CREATE TABLE groupsPers
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32),
	date_created DATETIME,
	date_updated DATETIME
)
-- tạo bảng quyền người dùng
CREATE TABLE userPers
(
	id_user INT REFERENCES employers(id),
	id_per INT REFERENCES groupsPers(id),
	date_created DATETIME,
	date_updated DATETIME
)


-- tạo bảng chức năng
CREATE TABLE actions
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(64) NOT NULL,
	code VARCHAR(32) NOT NULL,
	date_created DATETIME,
	date_updated DATETIME
)

--tạo bảng quyền chức năng
CREATE TABLE persActions
(
	id_per INT REFERENCES groupsPers(id),
	id_act INT REFERENCES actions(id),
	date_created DATETIME,
	date_updated DATETIME
)

-- tạo bảng quầy hàng
CREATE TABLE couter
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32),
	[status] TINYINT DEFAULT 1,
	date_created DATETIME,
	date_updated DATETIME
)

-- tạo bảng đơn hàng
CREATE TABLE orders
(
	id INT PRIMARY KEY IDENTITY(1,1),
	id_employee INT REFERENCES employers(id),
	id_couter INT REFERENCES couter(id),
	code_customer VARCHAR(12),
	[status] TINYINT DEFAULT 1,
	date_created DATETIME,
	date_updated DATETIME
)

--tạo bảng chi tiết đơn hàng

CREATE TABLE orderDetail
(
	id_order INT REFERENCES orders(id),
	id_pro INT REFERENCES products(id),
	quantity INT,
	CONSTRAINT PK_idOrder_idPro PRIMARY KEY (id_order, id_pro)
)

--  thêm not null trong persActions
ALTER TABLE persActions
 ALTER COLUMN id_per INT NOT NULL;

 ALTER TABLE persActions
 ALTER COLUMN id_act INT NOT NULL;
 -- thêm khóa chính cho  persActions
ALTER TABLE persActions
ADD CONSTRAINT PK_idPer_idAct PRIMARY KEY (id_per, id_act);

--  thêm not null trong userPers
ALTER TABLE userPers
 ALTER COLUMN id_user INT NOT NULL;
ALTER TABLE userPers
 ALTER COLUMN id_per INT NOT NULL;
 -- thêm khóa chính cho  userPers
ALTER TABLE userPers
ADD CONSTRAINT PK_idPer_idUser PRIMARY KEY (id_per, id_user);

-- thêm cột trong employers
ALTER TABLE employers
ADD id_couter INT REFERENCES couter(id);

--tạo bảng comment - phản hồi của khách hàng

CREATE TABLE comments
(
	id INT PRIMARY KEY IDENTITY(1,1),
	email VARCHAR(265) NOT NULL,
	cmt_employer NVARCHAR(686),
	cmt_product NVARCHAR(686),
	[status] TINYINT DEFAULT 1,
	date_created DATETIME,
	date_updated DATETIME
)
---29/12/2019
-- tạo thủ tục kiẻm tra emai login
CREATE PROC checkEmail
@email VARCHAR(265)
AS SELECT * FROM employers WHERE email like @email

-- thêm danh sách quyền
SELECT * FROM products
