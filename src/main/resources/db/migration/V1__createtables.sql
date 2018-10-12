create table customer(
    customer_Id int primary key,
    customer_name varchar2,
    contact_number bigint,
    address varchar2,
    gender varchar2
);

create table vendor(

    vendor_Id int primary key,
    vendor_name varchar2,
    vendor_contact_no bigint,
    vendor_Email varchar2,
    vendor_Username varchar2,
    vendor_address varchar2
);

create table item(
    sku_Id int primary key,
    product_name varchar2,
    product_label varchar2,
    inventory_on_hand int,
    min_qty_req int,
    price float
);