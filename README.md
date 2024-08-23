### Spring Boot Project


## API End Point
+ GET: /api/movies
  - description: retrieve all movies
  - request body:
    ```
    {
      "title": "Movie",
      "rating": 3.5,
      "categories": [ "Action", "Adventure" ]
    }
    ```
  - expected response: 200
+ GET: /api/movies/{id}
  - description: retrieve movie by id
  - expected response:
    ```
    {
        "id": 1,
        "title": "Movie 1",
        "rating": 1.0,
        "categories": [
            {
                "id": 1,
                "categoryName": "Adventure"
            },
            {
                "id": 2,
                "categoryName": "Action"
            }
        ]
    }
    ```
+ POST: /api/movie/create (create new movie)
+ PUT: /api/movie/update/{id} (update movie entirely)
+ PATCH: /api/movie/update/{id} (update movie partiailly)
+ DELETE: /api/movie/delete/{id} (delete movie by id)
