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
The service runs on port 5000 by default.

## Usage

Vendor entity:
```
case class Vendor(vendor_id: String, vendor_name: String)
```
### Create a Vendor
Request:
```
	POST http://localhost:5000/vendors
	'{"vendor_id": "100", "vendor_name": "ABC"}'
```
### Get a Vendor
Request:
```
     GET http://localhost:5000/vendors/100
     http://localhost:5000/vendors/all
     http://localhost:5000/vendors/ABC
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

