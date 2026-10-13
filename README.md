# Session 12 - Bai 5: Shipping status event

Chay `docker compose up -d --wait`, roi chay `./gradlew :shipping-service:bootRun`, `./gradlew :order-service:bootRun`, `./gradlew :notification-service:bootRun` o 3 terminal.

Don mau ID=1 co trang thai `SHIPPING`. Goi `POST http://localhost:8090/api/shipping/status` voi JSON `{"orderId":1,"status":"DELIVERED","customerEmail":"buyer@example.com"}`. Shipping-Service gui `ShippingStatusEvent` vao `shipping-status-events`; Order-Service group `order-service` doi DB sang `COMPLETED`, Notification-Service group `notification-service` gui mail qua Mailpit http://localhost:18025. Xem trang thai qua `GET http://localhost:8091/api/orders/1`.
