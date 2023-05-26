# Exchange API

## Tech Stack
* Java
* Spring Boot
* MongoDB
* Maven
* JUnit
* Lombok
* Mockito
* Swagger
* Docker


## How to Run Service With Docker
`docker-compose up --build`

## Swagger url:

`http://localhost:8080/documentation`

## Management Endpoints
health: `http://localhost:8080/health`

liveness: `http://localhost:8080/liveness`

readiness: `http://localhost:8080/readiness`

## Service Endpoints:

### Exchange Endpoint

#### Post `http://localhost:8080/v1/exchange/rate`

This endpoint gets exchange rates

Request:

    {
        "base": "TRY",
        "target": "USD"
    }

Response:

    {
        "data": {
            "success": true,
            "base": "TRY",
            "date": "2023-05-26",
            "rates": {
                "USD": 0.050004
            }
        }
    }


#### Post `http://localhost:8080/v1/exchange`

This endpoint get currency exchange

Request:

    {
        "source": "TRY",
        "target": "EUR",
        "amount": 10
    }

Response:

    {
        "data": {
            "transactionId": "64706bab7243f4508545ac96",
            "source": "TRY",
            "target": "EUR",
            "amount": 10,
            "result": 0.46589,
            "createdAt": "2023-05-26T08:19:55.911723"
        }
    }

#### Post `http://localhost:8080/v1/exchange/transactions`

This endpoint get all Exchanges by transactionId or Date Ranges

Request with TransactionId:

    {
        "page": 0,
        "size": 10,
        "transactionId": "646f71f0b9d62d20a8ba137c"
        }
    }

Response:

    {
        "data": [
            {
                "transactionId": "646f71f0b9d62d20a8ba137c",
                "source": "TRY",
                "target": "USD",
                "amount": 10,
                "result": 0.50175,
                "createdAt": "2023-05-25T14:34:24.883"
            }
        ]
    }

Request with Transaction Date Range:

    {
        "page": 0,
        "size": 5,
        "transactionDateRange": {
            "startDate": "2023-05-23T15:31:38.225",
            "endDate": "2023-06-15T15:31:38.225"
        }
    }

Response:
    
    {
        "data": [
            {
                "transactionId": "646f71f0b9d62d20a8ba137c",
                "source": "TRY",
                "target": "USD",
                "amount": 10,
                "result": 0.50175,
                "createdAt": "2023-05-25T14:34:24.883"
            },
            {
                "transactionId": "646f72c60d3e574fd63508af",
                "source": "TRY",
                "target": "USD",
                "amount": 10,
                "result": 0.50174,
                "createdAt": "2023-05-25T14:37:58.93"
            },
            {
                "transactionId": "646f72d40d3e574fd63508b0",
                "source": "TRY",
                "target": "USD",
                "amount": 10,
                "result": 0.50166,
                "createdAt": "2023-05-25T14:38:12.537"
            },
            {
                "transactionId": "646f7902477730385c1c7591",
                "source": "TRY",
                "target": "USD",
                "amount": 10,
                "result": 0.50166,
                "createdAt": "2023-05-25T15:04:34.273"
            },
            {
                "transactionId": "646f790a477730385c1c7592",
                "source": "TRY",
                "target": "EUR",
                "amount": 10,
                "result": 0.46762,
                "createdAt": "2023-05-25T15:04:42.245"
            }
        ]
    }

If both transaction id or transaction date range are not provided, the service will raise an exception.