### Spring Boot Project


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
  - expected response: 
+ PATCH: /api/movie/update/{id} (update movie partiailly)
+ DELETE: /api/movie/delete/{id} (delete movie by id)
