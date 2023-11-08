В данном проекте используется база данных PostgreSQL 11. Запускаем плагин migration/Plugins/liquibase/liquibase:update, создаются таблицы в базе данных и заполняются тестовыми данными. Запускаем сервис заказов OrderServiceApplication.
Для создания нового заказа отправляем POST-запрос по адресу http://localhost:8081/api/v1/orders
{
"restaurantId": "1",
"menuItems": [
{
"quantity": "5",
"menuItemId": "1"
}
]
}

Для оплаты заказа отправляем POST-запрос по адресу http://localhost:8081/api/v1/orders/pay/{orderId}, после 
этого сервис кухни получает сообщение о новом заказе.

Для изменения заказа отправляем PUT-запрос по адресу http://localhost:8081/api/v1/orders/{orderId}
{
"restaurantId": "1",
"menuItems": [
{
"quantity": "10",
"menuItemId": "1"
}
]
}

Для отмены заказа отправляем DELETE-запрос по адресу http://localhost:8081/api/v1/orders/{orderId}

После получения сообщения о новом заказе кухня смотрит заказ, отправляя GET запрос 
http://localhost:8082/api/v1/kitchen/{orderId}

Кухня принимает заказ POST запросом по адресу 
http://localhost:8082/api/v1/kitchen/{orderId}/accept и отправляет сообщение клиенту об этом

Кухня отклоняет заказ POST запросом по адресу
http://localhost:8082/api/v1/kitchen/{orderId}/decline и отправляет сообщение клиенту об этом

Кухня завершает заказ POST запросом по адресу
http://localhost:8082/api/v1/kitchen/{orderId}/ready и отправляет сообщение клиенту и курьерам об этом

После завершения приготовления блюда сервис доставки получает уведомление и ищется ближайший курьер.

Курьер берет заказ POST запросом по адресу http://localhost:8083/api/v1/delivery/{orderId}/take,
клиент и кухня получают уведомления об этом.

Курьер завершает заказ POST запросом по адресу http://localhost:8083/api/v1/delivery/{orderId}/complete,
клиент получает уведомление об этом.
