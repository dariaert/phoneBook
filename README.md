# ☎️ Телефонная книга (Phone Book)

## 📌 Описание
Проект представляет собой REST API для управления контактами. Реализованы основные CRUD-операции, а также дополнительные возможности, такие как фильтрация, экспорт/импорт в CSV, генерация QR-кодов и интеграция с Telegram.

## 🛠️ Используемый стек технологий
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Jackson** (для сериализации JSON)
- **Apache Commons CSV** (для работы с CSV)
- **ZXing** (для генерации QR-кодов)
- **Telegram Bot API** (для отправки контактов в Telegram)

## 🛡️ Функциональность
- CRUD-операции с контактами
- Фильтрация по имени и номеру телефона
- Импорт/экспорт контактов в CSV
- Группировка контактов по категориям (Семья, Работа, Друзья и т. д.)
- История изменений контактов (Аудит)
- Напоминания о днях рождения
- Генерация QR-кодов для контактов
- Отправка контактов в Telegram

## 📦 Установка и запуск

### 1. Клонирование репозитория
```sh
git clone https://github.com/your-repo/phonebook.git
cd phonebook
```

### 2. Настройка базы данных PostgreSQL
Создай базу данных и настрой подключение в `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/phonebook_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Сборка и запуск приложения
```sh
./mvnw clean install
./mvnw spring-boot:run
```

## 📝 API Эндпоинты

### 1. Контакты
| Метод  | URL | Описание |
|--------|------|----------|
| `GET`  | `/contacts` | Получить список всех контактов |
| `GET`  | `/contacts/{id}` | Получить контакт по ID |
| `POST` | `/contacts` | Создать новый контакт |
| `PUT`  | `/contacts/{id}` | Обновить контакт |
| `DELETE` | `/contacts/{id}` | Удалить контакт |
| `GET`  | `/contacts/search?name=Иван` | Поиск по имени |
| `GET`  | `/contacts/search?phone=+79991112233` | Поиск по номеру |
| `POST` | `/contacts/import` | Импорт контактов из CSV |
| `GET`  | `/contacts/export` | Экспорт контактов в CSV |
| `GET`  | `/contacts/{id}/qrcode` | Получить QR-код контакта |
| `POST` | `/contacts/{id}/send?chatId=123456` | Отправить контакт в Telegram |

### 2. Категории
| Метод  | URL | Описание |
|--------|------|----------|
| `GET`  | `/categories` | Получить список категорий |
| `POST` | `/categories` | Создать категорию |
| `PUT`  | `/categories/{id}` | Обновить категорию |
| `DELETE` | `/categories/{id}` | Удалить категорию |

### 3. История изменений
| Метод  | URL | Описание |
|--------|------|----------|
| `GET`  | `/contacts/{id}/history` | Получить историю изменений контакта |

### 4. Дни рождения
| Метод  | URL | Описание |
|--------|------|----------|
| `GET`  | `/contacts/birthdays` | Получить список контактов с ДР сегодня |

## 📉 Примеры запросов

### Создание контакта
```http
POST /contacts
Content-Type: application/json

{
  "name": "Иван Иванов",
  "phoneNumber": "+79991112233",
  "categoryId": 1,
  "birthday": "1995-06-15"
}
```

### Экспорт контактов в CSV
```http
GET /contacts/export
```

### Генерация QR-кода
```http
GET /contacts/1/qrcode
```
