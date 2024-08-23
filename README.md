### Spring Boot Project
for more details documentation (when run application): (http://localhost:8080/swagger-ui/index.html)
update MySQL connection in application.properties
* database: db_movie_api
* username: root
* password: Root.1234
```
spring.datasource.url=jdbc:mysql://localhost:3306/db_movie_api 
spring.datasource.username=root
spring.datasource.password=Root.1234
```

## API End Point
+ GET: /api/movies
  - description: retrieve all movies
  - expected response: 200
    ```
    [
        {
            "id": 1,
            "title": "Movie",
            "rating": 3.5,
            "categories": [
                {
                    "id": 1,
                    "categoryName": "Action"
                },
                {
                    "id": 2,
                    "categoryName": "Adventure"
                }
            ]
        }
    ]
    ```
+ GET: /api/movies/{id}
  - description: retrieve movie by id, suppose id is 1
  - expected response: 200
    ```
    {
        "id": 1,
        "title": "Movie",
        "rating": 3.5,
        "categories": [
            {
                "id": 1,
                "categoryName": "Action"
            },
            {
                "id": 2,
                "categoryName": "Adventure"
            }
        ]
    }
    ```
+ POST: /api/movie/create
  - description: create a new movie
  - request body:
    ```
    {
        "title": "Movie",
        "rating": 3.5,
        "categories": [ "Action", "Adventure" ]
    }
    ```
  - expected response: 200
+ PUT: /api/movie/update/{id} (update movie entirely)
  - description: update existed movie entirely, any field not provide will be null
  - request body:
    ```
    {
        "title": "Updated Movie",
        "rating": 3.4,
        "categories": [ "Action", "Horror" ]
    }
    ```
  - expected response: 
+ PATCH: /api/movie/update/{id} (update movie partiailly)
  - description: update existed movie, update only requested field, as for category will be add to existing one
  - request body:
    ```
    {
        "title": "Updated Movie"
    }
    ```
  - expected response: 200
+ DELETE: /api/movie/delete/{id} (delete movie by id)
  - description: delete movie by id
  - expected response: 204
