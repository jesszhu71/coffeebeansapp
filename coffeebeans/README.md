# SPR-CL-RESTCONTROLLER

This the backend for my coffeebeansapp

## Instructions
- Build and run this locally (recommended to be run in Intellij) and end points can be called from postman
- run "Populate defaults" to populate some default values for a demo

## Sample Endpoints:
- Add any coffee shop: POST("/coffeeshop/add"), Request Body: (CoffeeShop json object)
- Retrieve the shop with the highest rating: GET("/coffeeshop/getTopRecShop")
- Retrieve shop by city: GET("/coffeeshop/getByCity/{city}")
- Add users: POST("/user/add"), Request Body: (UserAccount json object)
- Add reviews: POST("/review/add"), Request Body: (Review json object)
- Populate defaults: POST("/coffeeshop/populate"), POST("/review/populate"), POST("/user/populate")
