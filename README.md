# VendorManagerService
VendorManagerService application with Akka-Http
## How to run the service
Clone the repository:
```
> https://github.com/GL-POC/VendorManagerService.git
```

Get to the `VendorManagerService` folder:
```
Run the service:
```
> sbt run
```
## Usage
### Start up an instance of Mysql db:
install mysql
net start mysql
mysql -u root

CREATE USER 'myuser'@'LOCALHOST' IDENTIFIED BY 'mypass';
CREATE DATABASE vmsdb;
GRANT ALL ON vmsdb.* TO 'myuser'@'localhost';

Vendor entity:
```
case class Vendor(vendor_id: String, vendor_name: String)
```
### Create a Vendor
Request:
```
	POST http://localhost:5000/vendors
	'{"vendor_name": "ABC"}'
```
### Get a Vendor
Request:
```
     GET http://localhost:5000/vendors/100
     http://localhost:5000/vendors
```

### Update a Vendor
Request:
```
	 PUT http://localhost:5000/vendors/100
	 '{"vendor_name":"Another name"}'
```

### Delete a Vendor
Request:
```
     DELETE http://localhost:5000/vendors/100
```

