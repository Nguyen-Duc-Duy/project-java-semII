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
select* from employers
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
-- tạo bảng sản phẩm
CREATE TABLE products
(
	id  INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(32) NOT NULL,
	id_cat INT REFERENCES categorys(id),
	code int not null,
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
	[status] int,
	date_created DATETIME,
	date_updated DATETIME
)
select * from [views]
select * from actions
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
	code int unique,
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
insert into employers(name,email,pass,[status]) values
(N'Quản lý hệ thống','programbuilder@gmail.com',1,2)

-- thêm giá trị cho view và actions
INSERT INTO [views](name,code,date_created) VALUES
(N'Sản phẩm','Pro',GETDATE()),
(N'Nhân viên','Em',GETDATE()),
(N'Thanh toán','Pay',GETDATE()),
(N'Danh mục','Cat',getdate()),
(N'Đơn vị','Unit',getdate()),
(N'Tất cả Đơn hàng','Order',getdate()),
(N'Quầy','Couter',getdate()),
(N'Nhóm Quyền','Per',getdate())
select * from [views]
-- thêm giá trị cho actions product
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Thêm Mới','P-1',GETDATE(),1),
(N'Cập Nhật','P-2',GETDATE(),1),
(N'Xóa','P-3',GETDATE(),1),
(N'Xem Danh Sách','P-4',GETDATE(),1),
(N'In Danh Sách','P-5',GETDATE(),1),
(N'Thay đổi trạng thái','P-6',GETDATE(),1)
select * from [views]
-- thêm giá trị cho actions employers
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Thêm Mới','E-1',GETDATE(),2),
(N'Cập Nhật','E-2',GETDATE(),2),
(N'Xóa','E-3',GETDATE(),2),
(N'Xem Danh Sách','E-4',GETDATE(),2),
(N'In danh sách','E-5',GETDATE(),2),
(N'Thay đổi trạng thái','E-6',GETDATE(),2)
select * from [views]
-- thêm giá trị cho actions thanh toán
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Thêm Mới','PAY-1',GETDATE(),3),
(N'Xem Danh sách','PAY-2',GETDATE(),3),
(N'Thống Kê','PAY-3',GETDATE(),3),
(N'Thêm Quầy','C-1',GETDATE(),7),
(N'Cập Nhật','C-2',GETDATE(),7),
(N'Xem Danh sách quầy','C-3',GETDATE(),7),
(N'Xem nhân viên theo quầy','C-4',GETDATE(),7)
-- thêm giá trị cho action unit - đơn vị
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Thêm Mới','U-1',GETDATE(),5),
(N'Cập Nhật','U-2',GETDATE(),5),
(N'Xóa','U-3',GETDATE(),5),
(N'Xem Danh Sách','U-4',GETDATE(),5)

-- thêm giá trị cho action category - danh mục
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Xem Danh Sách','CT-3',GETDATE(),4),
(N'Thêm Mới','CT-1',GETDATE(),4),
(N'Cập Nhật','CT-2',GETDATE(),4),
(N'Thay đổi trạng thái','CT-5',GETDATE(),4)

select * from actions
-- thêm giá trị cho action đơn hàng và phân quyền
INSERT INTO actions(name,code,date_created,id_view) VALUES
(N'Danh sách','O-1',GETDATE(),6),
(N'In','O-2',GETDATE(),6),
(N'Thêm','PER-1',GETDATE(),8),
(N'Sửa','PER-2',GETDATE(),8),
(N'Xóa','PER-3',GETDATE(),8),
(N'Xem danh sách','PER-4',GETDATE(),8),
(N'Cập Nhật','PER-5',GETDATE(),8)

-- TẠO PROCEDURE SẢN PHẨM
	-- lấy tất cả
CREATE PROCEDURE selectAllPro
AS
select p.id,p.name,p.id_cat,p.code,p.price,p.sale,p.descript,p.quantity,p.img,p.id_unit,p.[status],p.date_crated,p.date_updated
from products p 
join categorys c
on p.id_cat = c.id
where c.status = 1
	
	-- tạo
CREATE PROC createPro
	@name NVARCHAR(32) ,
	@id_cat INT ,
	@price FLOAT ,
	@sale FLOAT ,
	@descript NVARCHAR(120) ,
	@quantity INT ,
	@img VARCHAR(265),
	@id_unit INT,
	@code int
AS 
INSERT INTO products(name,id_cat,code,price,sale,[descript],quantity,img,id_unit,date_crated) 
VALUES
(@name,@id_cat,@code,@price,@sale,@descript,@quantity,@img,@id_unit,GETDATE())
go
	--cập nhật
CREATE PROC updatePro
	@name NVARCHAR(32) ,
	@id_cat INT ,
	@price FLOAT ,
	@sale FLOAT ,
	@descript NVARCHAR(120) ,
	@quantity INT ,
	@img VARCHAR(265),
	@id_unit INT ,
	@id INT
AS
UPDATE products SET name = @name,id_cat=@id_cat,
price=@price,sale=@sale,descript=@descript,quantity=@quantity,
img=@img,id_unit=@id_unit,date_updated=GETDATE() WHERE id = @id
go

	--thay đổi trạng thái
	CREATE PROC changeSTTPro
	@id int, @status int
	AS UPDATE products SET [status] = @status where id = @id
	go
	-- xóa
CREATE PROC deletePro
@id int
AS
DELETE FROM products WHERE id = @id
go
	--thay đổi số lượng sản phẩm
create proc changeQuantityPro
@id int,@quantity int
as
update products set quantity = @quantity where id = @id
	-- danh sách các sản phẩm trong ngày
CREATE PROC getProByDay
@date varchar(56)
as
select * from products where date_crated BETWEEN CONCAT(@date,' 00:00:00') AND CONCAT(@date,' 23:59:59')
order by date_crated

	--danh sách sản phẩm trong tuần
CREATE PROC listProOfWeek
AS
select * from products
WHERE date_crated BETWEEN DATEADD(d, -(DATEPART(dw, getdate()-2)), getdate())
AND
dateadd(day,7-(datepart(dw,getdate()-1)),getdate())

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
	EXEC createCat N'DANH MỤC 2'

	--CẬP NHẬT
CREATE PROC updateCat
@id int,@name NVARCHAR(32),@status int
AS
UPDATE categorys SET name=@name,[status] = @status,date_updated=GETDATE() where id = @id
go
	--THỬ 
	EXEC updateCat 1,N'DANH MỤC ĐÃ SỬA 1',1
	-- THAY ĐỔI TRẠNG THÁI
	CREATE PROC changeSTTCat
	@status int, @id int
	AS UPDATE categorys SET [status]=@status where id = @id
	go
	--XÓA
CREATE PROC deleteCat
@id INT
AS DELETE FROM categorys WHERE id = @id
	-- tìm kiếm
CREATE PROC searchCat
@key NVARCHAR(265)
AS
SELECT * FROM categorys WHERE name LIKE @key


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

	--CẬP NHẬT
CREATE PROC updateUnit
@id INT,@name NVARCHAR(32)
AS
UPDATE units SET name=@name,date_updated = GETDATE() WHERE id = @id
go
	
	--XÓA
CREATE PROC deleteUnit
@id INT
AS DELETE FROM units WHERE id = @id

	--Tìm kiếm
CREATE PROC searchUnits
@key NVARCHAR(265)
AS
SELECT * FROM units WHERE name LIKE @KEY


-- THỦ TỤC CỦA ORDERS
	--LẤy DANH SÁCH ORDER
CREATE PROC selectAllO
AS
SELECT * FROM orders

	-- TẠO MỚI
CREATE PROC createOrder
@id_em INT,@code int
AS
INSERT INTO orders(id_employee,code,date_created) VALUES (@id_em,@code,GETDATE()) 
	--THỬ
	EXEC createOrder 1,111111112
	-- xóa
CREATE PROC deleteOrder
@id int
as
delete from orderDetail where id_order = @id
delete from orders where id = @id
	-- lấy order theo id
create proc selectOrderById
@id int
as
select * from orders where id = @id
--THỦ TUC CỦA ORDER DETAIL
	-- lấy
create proc selectAll
@id_order int
as
select * from orderDetail where id_order = @id_order
exec selectAll 15
	--thêm mới
	select * from products
create proc createOD
@id_order int, @id_pro int,@quantity int
as
insert into orderDetail values (@id_order,@id_pro,@quantity)
	--thử
	exec createOD 2,3,23
	-- xóa sản phẩm trong đơn
create proc deleteProCart
@id_order int,@id_pro int
as
delete orderDetail where id_pro = @id_pro and id_order = @id_order

	--câp nhật
create proc updateOD
@id_order int, @id_pro int,@quantity int
as
update orderDetail set quantity = @quantity where id_order = @id_order and id_pro = @id_pro


update orderDetail set quantity = 5 where id_order = 90 and id_pro = @id_order
select * from orderDetail where id_order = 90 and id_pro = 11
	--select Pro by id
create proc selectProById
@id int
as
select p.id,p.name,p.id_cat,p.code,p.price,p.sale,p.descript,p.quantity,p.img,p.id_unit,p.[status],p.date_crated,p.date_updated,u.name as unit
from products p
join units u
on p.id_unit = u.id
 where p.id = @id 
	-- select Order by em
create proc selectOByEm
@id_em int
as
select * from orders where id_employee = @id_em

select * from products

-- quầy thanh toán
	-- lấy danh sách
create proc selectAllCouter
as
select * from couter
	--tạo mới
create  proc createCouter
@name nvarchar(32)
as insert into couter(name,date_created) values (@name,GETDATE())

exec createCouter N'Quầy 1'
	-- thay đổi trạng thái
create proc changeSTTCouter
@id int , @status int
as
update couter set [status] = @status,date_updated = GETDATE() where id = @id
	-- cập nhật
create proc updateCouter
@id int,@name nvarchar(32),@status int
as update couter set name = @name,[status] = @status,date_updated = GETDATE() where id = @id
	-- lấy nhân viên theo quầy
create proc selectEmByCouter
@couter int
as
select * from employers where id_couter = @couter

-- TẠO PROCEDURES CỦA GroupsPers
	-- lấy
CREATE PROC selectAllGP
AS
SELECT * FROM groupsPers
	-- tạo mới
CREATE PROC createGP
@name NVARCHAR(32)
AS
INSERT INTO groupsPers (name,date_created) VALUES (@name,GETDATE())
	-- cập nhật 
CREATE PROC updateGP
@id int, @name NVARCHAR(32),@status int
AS
UPDATE groupsPers SET name = @name,[status] = @status, date_updated = GETDATE() WHERE id = @id

	--thay đổi trạng thái
CREATE PROC changeSTTGroupsPers
@id int, @status int
as
UPDATE groupsPers SET [status] = @status WHERE id = @id


-- TẠO THỦ TỤC QUẢN LÝ NHÂN VIÊN
	-- lất tất cả bản ghi
create proc selectAttEm
as
select * from employers
	-- lấy các nhân viên
create proc selectEm
as
select * from employers where [status] = 0 or [status] = 1
	-- thêm mới
create proc createEm
@name nvarchar(32),@email varchar(120),@pass varchar(120),@phone varchar(10),@id_couter int null,@id_per int
as
insert into employers (name,email,pass,phone,id_couter,id_per,date_created) values (@name,@email,@pass,@phone,@id_couter,@id_per,getdate())
	exec createEm N'duy','abc','pass','0123456789',null,1
	--cập nhật
create proc updateEm
@name nvarchar(32),@email varchar(120),@pass varchar(120),@phone varchar(10),@id_couter int,@id_per int, @id int
as
update employers set name = @name,email = @email,pass = @pass, phone = @phone,id_couter = @id_couter,id_per = @id_per where id = @id

	-- thay đổi trạng thái
create proc changeSTTEm
@id int,@status int
as
update employers set [status] = @status where id = @id
	-- xóa nhân viên
	create proc deleteEm
	@id int
	as
	delete from employers where id = @id

	-- lấy nhân viên theo id
CREATE PROC selectEmById
@id int
AS
SELECT * FROM employers WHERE id = @id

	--Danh sách nhân viên vừa thêm(trong 1 ngày sớm nhất)
create proc selectEmInNewDate
as
declare @dateOldEm varchar(10)
set @dateOldEm = convert(varchar,(select top(1) e.date_created from employers e where [status] = 1 or [status] = 0 order by date_created DESC), 23)
select * from employers
where date_created between concat(@dateOldEm,' 00:00:00') and concat(@dateOldEm,' 23:59:59')

	--Danh sách nhân viên theo nhóm quyền
create proc selectEmByGP
@id_per int
as
select * from employers where id_per = @id_per


-- THỦ TỤC CỦA VIEWS
	-- lấy 
create proc selectAllViews
as
select * from [views]

-- THỦ TỤC CỦA ACTION
	--LẤY
create proc  selectAllAction
as
select * from actions
-- lấy theo view
create proc selectActByView
@id_view int
as
select * from actions  where id_view = @id_view
exec selectActByView 1
-- THỦ TỤC CỦA NHÓM QUYỀN VÀ HÀNH ĐỘNG PERACCTION
	-- lấy danh sách theo id_per
CREATE PROC selectPerActionById
@id_per INT 
AS
SELECT * FROM persActions WHERE id_per = @id_per
go
alter table groupsPers
ADD CONSTRAINT statusGP
DEFAULT 1 FOR [status];
	-- THÊM MỚI
CREATE PROC createPerAction
@id_per INT ,@id_act INT
AS
INSERT INTO persActions(id_per,id_act,date_created) VALUES (@id_per,@id_act,GETDATE())
exec createPerAction 4,62
	-- CẬP NHẬT
CREATE PROC updatePerAction
@id_per INT, @id_act INT, @id_act_new INT
AS
UPDATE persActions SET id_act = @id_act_new where id_per = @id_per and id_act = @id_act

	-- xóa
CREATE PROC deletePerAction
@id_per INT,@id_act INT
AS
DELETE FROM persActions WHERE id_per = @id_per AND id_act = @id_act
go

select * from groupsPers
go
exec deletePerAction 6,70
select * from persActions where id_Per = 6

select * from actions
	-- KIỂM TRA QUYỀN (sử dụng để chọn quầy hàng khi có quyền thanh toán)
CREATE PROC checkPer
@id_per int
AS
select a.code from groupsPers gp
join persActions pa
on pa.id_per = gp.id
join actions a 
on a.id = pa.id_act
where gp.id = @id_per
go
-- lấy mã view theo id nhân viên
create proc selectPerActByEm
@id_e INT
AS
select v.code from persActions pa
inner join actions a 
on a.id = pa.id_act
inner JOIN views v
on v.id = a.id_view
 where id_per =
(select gp.id from groupsPers gp where id =
(select e.id_per from employers e where id = @id_e))
group by v.code

exec selectPerActByEm 1


--lấy mã actions theo id nhân viên
create proc selectActByEm
@id_e INT
AS
select a.code from persActions p
join actions a
on a.id = p.id_act
where id_per =
(select e.id_per from employers e where id = @id_e)

exec selectActByEm 1  

select * from employers
insert into employers(name,email,pass,[status]) values
(N'Quản lý hệ thống','programbuilder@gmail.com',1,2)

