Java OOP Project. 
Project name: MyWebShop. 
Creators: Ussabekov Madiyar && Shyngys Azimbayev. 
Description: "MyWebShop" is the web-service, designed to have a functionality of an actual Web Shop. In this service user can login or create new account, see available products, order them, and also chat with other users.   
 
Scenarios: 
Use Case 1 
User receives his authentithication token 
@POST   
/MyWebShop_Web_exploded/auth 
Required arguments 
username 
Password 
Response 
{"token":"eyJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJjaGluZ2EiLCJpYXQiOjE1OTE0NDM2NjgsImV4cCI6MTU5MTQ0NDI2OCwiMUQyMCI6NH0.7ZgouRzgFngZg1WqGhGdgogw2Np287-4lak7D7f7KN7NkKvi_G_-g2_pbqwMbaiy"} 
 
Use Case 2 
User create a new account 
@POST   
/MyWebShop_Web_exploded/users/register 
Required arguments 
name 
surname 
username 
password 
Birthday 
Response 
User successfully created! 
 
Use Case 3 
Users and guests can see a list of other users, even not being authorized 
@GET 
/MyWebShop_Web_exploded/users 
Required arguments 
None 
Response 
[ 
    { 
        "id": 1, 
        "username": "chinga" 
    }, 
    { 
        "id": 2, 
        "username": "MrMaddy" 
    }, 
    { 
        "id": 3, 
        "username": "admin" 
    } 
] 
 
Use Case 4 
User can update his account information 
@POST 
/MyWebShop_Web_exploded/users/update 
Required arguments: 
user_id 
username 
surname 
password 
Response: 
User information has been successfully updated! 
 
Use Case 5 
Only signed in user can delete only his profile, no one elses 
@POST 
/MyWebShop_Web_exploded/users/delete 
Required arguments: 
user_id 
Username 
Response: 
User was successfully deleted! 
 
Use Case 6 
Non authorized user can see the list of products. 
@GET 
/MyWebShop_Web_exploded/products 
Required arguments: 
None 
Response: 
[ 
    { 
        "id": 1, 
        "name": "Apple MacBook Pro 16 2019 Space Gray", 
        "price": 2799.99 
    }, 
    { 
        "id": 2, 
        "name": "ASUS ROG Zephyrus G14", 
        "price": 1449.99 
    }, 
    { 
        "id": 3, 
        "name": "iPhone 11 Pro Max", 
        "price": 1449.99 
    }, 
    { 
        "id": 4, 
        "name": "AirPods Pro", 
        "price": 249.99 
    }, 
    { 
        "id": 5, 
        "name": "SanDisk 2TB Extreme PRO Portable External SSD", 
        "price": 369.99 
    } 
] 
 
Use Case 7 
See each product, by entering their ID in URL(only authorized user have access to this functionality) 
@GET 
/MyWebShop_Web_exploded/products/{id} 
Required arguments: 
product_id 
Response 
{ 
    "category": "Notebooks", 
    "description": "The MacBook Pro 16-inch is not just the most powerful MacBook Apple`s ever made – it`s also the best. By listening to its customers and taking on board the criticisms of previous models, Apple has made a MacBook Pro that improves on nearly every aspect of the iconic workstation. If you have the budget for it, this is one of the best laptops you can buy.", 
    "id": 1, 
    "name": "Apple MacBook Pro 16 2019 Space Gray", 
    "price": 2799.99 
} 
 
Use Case 8 
Administrator can add new product to the Products List 
@POST 
/MyWebShop_Web_exploded/products/add 
Required arguments: 
name 
category 
description 
price 
Response 
Successfully added a new product! 
 
Use Case 9 
Only Administrator can update the name, description, price, category of the product 
@POST   
/MyWebShop_Web_exploded/products/add 
Required arguments 
id 
name 
category 
description 
price 
Response 
Product was successfully updated! 
 
Use Case 10 
Admin deletes product from product list 
@POST   
/MyWebShop_Web_exploded/products/delete 
Required arguments 
id 
name 
Response 
Product has been deleted! 
 
Use Case 11 
Get order list by users ID (available only for authorized users) 
@GET  
/MyWebShop_Web_exploded/orders/users/{id} 
Required arguments 
id (user_id) 
Response 
[ 
    { 
        "product_name": "ASUS ROG Zephyrus G14", 
        "product_price": 1449.99, 
        "username": "MrMaddy" 
    }, 
    { 
        "product_name": "AirPods Pro", 
        "product_price": 249.99, 
        "username": "MrMaddy" 
    }, 
    { 
        "product_name": "SanDisk 2TB Extreme PRO Portable External SSD", 
        "product_price": 369.99, 
        "username": "MrMaddy" 
    } 
] 
 
Use Case 12 
Show total cost of products 
@GET 
/MyWebShop_Web_exploded/orders/users/{id}/total 
Required arguments 
id (user_id) 
Response 
{ 
    "sum": 2069.97, 
    "username": "MrMaddy" 
} 
 
Use Case 13 
User can add a new product to his order 
@POST   
/MyWebShop_Web_exploded/orders/users/add 
Required arguments 
Id (user_id) 
Id (product_id) 
Response 
Successfully added a new product to the order list! 
 
Use Case 14  
@POST   
/MyWebShop_Web_exploded/orders/users/delete 
Required arguments 
id (order_id) 
Response 
Product has been successfully deleted from your order list! 
 
Use Case 15 
Get message list by sender id 
@GET 
/MyWebShop_Web_exploded/messages/sender/{id} 
Required arguments 
id (sender_id) 
Response 
[ 
    { 
        "message": "Salam-popolam", 
        "receiver": "chinga", 
        "sender": "MrMaddy" 
    }, 
    { 
        "message": "Admin, udali Akylbeka", 
        "receiver": "admin", 
        "sender": "MrMaddy" 
    }, 
    { 
        "message": "I hope, that we`ll get the highest mark", 
        "receiver": "chinga", 
        "sender": "MrMaddy" 
    } 
] 
 
Use Case 16 
Get message list by receiver id  
@GET   
/MyWebShop_Web_exploded/messages/receiver/{id} 
Required arguments 
id (receiver_id) 
Response 
[ 
    { 
        "message": "Hello, chuvak", 
        "receiver": "MrMaddy", 
        "sender": "chinga" 
    } 
] 
 
Use Case 17 
Authorized user can send message to any other user 
@POST   
/MyWebShop_Web_exploded/messages/send 
Required arguments 
id (sender_id) 
message 
id (receiver_id) 
Response 
Message was succcessfully sent! + messageItSelf 
