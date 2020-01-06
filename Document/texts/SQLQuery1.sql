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

-- tạo bảng đơn vị sản phẩm
CREATE TABLE units
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
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
-- tạo bảng giao diện
CREATE TABLE [views]
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
	code VARCHAR(32) NOT NULL,
	date_created DATETIME,
	date_updated DATETIME
)
GO

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


-- thêm cột trong employers
ALTER TABLE employers
ADD id_couter INT REFERENCES couter(id);


---29/12/2019
-- tạo thủ tục kiẻm tra emai login
CREATE PROC checkEmail
@email VARCHAR(265)
AS SELECT * FROM employers WHERE email like @email

-- SỬA BẢNG ACTIONS
select * from actions
ALTER TABLE actions
ADD id_view INT FOREIGN KEY REFERENCES [views](id)

--sửa bảng employers
SELECT * FROM groupsPers
ALTER TABLE employers
ADD id_per INT FOREIGN KEY REFERENCES groupsPers(id)


-- thêm giá trị cho quyền admin tổng
INSERT INTO groupsPers(name,date_created) VALUES
(N'Chủ hệ thống',GETDATE())
SELECT * FROM employers
UPDATE employers SET id_per = 1
-- thêm giá trị cho view và actions
INSERT INTO [views](name,code,date_created) VALUES
(N'Sản phẩm','Pro',GETDATE()),
(N'Kho','Repository',GETDATE()),
(N'Nhân viên','Em',GETDATE()),
(N'Thống kê','Das',GETDATE()),
(N'Thanh toán','Pay',GETDATE())

-- thêm giá trị cho actions product
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Thêm Mới','P-1',GETDATE(),1),
(N'Cập Nhật','P-2',GETDATE(),1),
(N'Xóa','P-3',GETDATE(),1),
(N'Xem Danh Sách','P-4',GETDATE(),1),
(N'Sản Phẩm Tồn Kho','P-5',GETDATE(),1),
(N'Sản Phẩm Bán Chạy','P-6',GETDATE(),1)
-- thêm giá trị cho actions employers
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Thêm Mới','E-1',GETDATE(),3),
(N'Cập Nhật','E-2',GETDATE(),3),
(N'Xóa','E-3',GETDATE(),3),
(N'Xem Danh Sách','E-4',GETDATE(),3)



-- TẠO PROCEDURE SẢN PHẨM
	-- lấy tất cả
CREATE PROCEDURE selectAllPro
AS
select * from products
	-- tạo
CREATE PROC createPro
	@name NVARCHAR(32) ,
	@code VARCHAR(100),
	@id_cat INT ,
	@price FLOAT ,
	@sale FLOAT ,
	@descript NVARCHAR(120) ,
	@quantity INT ,
	@img VARCHAR(265),
	@id_unit INT
AS 
INSERT INTO products(name,code,id_cat,price,sale,[descript],quantity,img,id_unit,date_crated) 
VALUES
(@name,@code,@id_cat,@price,@sale,@descript,@quantity,@img,@id_unit,GETDATE())
go

exec createPro N'Sản phẩm 1','SP-1',1,1,1,N'mô tả 1',2,'img.png',1
	--cập nhật
CREATE PROC updatePro
	@name NVARCHAR(32) ,
	@code VARCHAR(100),
	@id_cat INT ,
	@price FLOAT ,
	@sale FLOAT ,
	@descript NVARCHAR(120) ,
	@quantity INT ,
	@img VARCHAR(265),
	@id_unit INT ,
	@id INT
AS
UPDATE products SET name = @name,code=@code,id_cat=@id_cat,
price=@price,sale=@sale,descript=@descript,quantity=@quantity,
img=@img,id_unit=@id_unit,date_updated=GETDATE() WHERE id = @id
go
	--thay đổi trạng thái
	CREATE PROC changeSTTPro
	@id int, @status int
	AS UPDATE products SET [status] = @status 
	go
	-- xóa
CREATE PROC deletePro
@id int
AS
DELETE FROM products WHERE id = @id
go


-- TẠO PROCEDURE DANH MỤC
	-- LẤY DANH SÁCH DANH MỤC
CREATE PROC selectAllCat
AS
SELECT * FROM categorys
go
	-- THÊM MỚI
CREATE PROC createCat
@name NVARCHAR(32)
AS INSERT INTO categorys(name,date_created)
VALUES (@name,GETDATE())
go

	--thử
	EXEC createCat N'DANH MỤC 1',1,'2019-01-01'

	--CẬP NHẬT
CREATE PROC updateCat
@name NVARCHAR(32),@id int
AS
UPDATE categorys SET name=@name,date_updated=GETDATE() where id = @id
go

	--THỬ 
	EXEC updateCat N'DANH MỤC ĐÃ SỬA 1',1,'2020-01-01'
	-- THAY ĐỔI TRẠNG THÁI
	CREATE PROC changeSTTCat
	@status int, @id int
	AS UPDATE categorys SET [status]=@status where id = @id
	go
	--XÓA
CREATE PROC deleteCat
@id INT
AS DELETE FROM categorys WHERE id = @id


-- TẠO PROCEDURE UNIT
	--LẤY DANH SÁCH
CREATE PROC selectAllUnit
AS SELECT * FROM units
exec selectAllUnit
	--THÊM MỚI
CREATE PROC createUnit
@name NVARCHAR(32)
AS INSERT INTO units(name,date_created)
VALUES (@name,GETDATE())
go

	--thử
	EXEC createUnit N'kg','2019-01-01'
	--CẬP NHẬT
CREATE PROC updateUnit
@name NVARCHAR(32)
AS
UPDATE units SET name=@name,date_updated=GETDATE()
go

	-- THỬ
	EXEC updateUnit N'KG','2020-01-01'
	--XÓA
CREATE PROC deleteUnit
@id INT
AS DELETE FROM units WHERE id = @id


