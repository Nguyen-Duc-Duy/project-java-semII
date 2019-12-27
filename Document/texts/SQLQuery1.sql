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

ALTER TABLE employers 
 DROP COLUMN powerfull;
-- thêm quyền admin tổng
insert into employers(name,email,pass) values (N'Nguyễn Đức Duy','duy@gmail.com','1')


