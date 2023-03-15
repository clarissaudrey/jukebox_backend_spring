# Jukebox's API (GET Endpoint) using Spring

### GET Request Examples

Some examples of the results from the GET /jukeboxes requests, as well as exception handling.
This can be verified using Postman or Talend API.

#### 1. Full result of all jukeboxes that support settingId

GET `http://localhost:8080/jukeboxes?settingId=515ef38b-0529-418f-a93a-7f2347fc5805`
```
[
    {
        "id": "5ca94a8a13385f0c82aa9f2e",
        "model": "virtuo",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "pcb"
            },
            {
                "name": "touchscreen"
            }
        ]
    },
    {
        "id": "5ca94a8a1d1bc6d59afb9392",
        "model": "virtuo",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "speaker"
            }
        ]
    },
    {
        "id": "5ca94a8a8b58770bb38055a0",
        "model": "angelina",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "pcb"
            }
        ]
    },
    {
        "id": "5ca94a8aa2330a0762019ac0",
        "model": "angelina",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "amplifier"
            }
        ]
    },
    {
        "id": "5ca94a8acfdeb5e01e5bdbe8",
        "model": "virtuo",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "money_pcb"
            },
            {
                "name": "money_storage"
            },
            {
                "name": "camera"
            },
            {
                "name": "money_receiver"
            }
        ]
    },
    {
        "id": "5ca94a8af0853f96c44fa858",
        "model": "virtuo",
        "components": [
            {
                "name": "led_matrix"
            },
            {
                "name": "touchscreen"
            },
            {
                "name": "money_storage"
            },
            {
                "name": "pcb"
            },
            {
                "name": "money_receiver"
            }
        ]
    }
]
```

#### 2. Paginated result of jukeboxes with limit and offset

GET `http://localhost:8080/jukeboxes?settingId=515ef38b-0529-418f-a93a-7f2347fc5805&limit=4&offset=3`
```
[
    {
        "id": "5ca94a8aa2330a0762019ac0",
        "model": "angelina",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "amplifier"
            }
        ]
    },
    {
        "id": "5ca94a8acfdeb5e01e5bdbe8",
        "model": "virtuo",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "money_pcb"
            },
            {
                "name": "money_storage"
            },
            {
                "name": "camera"
            },
            {
                "name": "money_receiver"
            }
        ]
    },
    {
        "id": "5ca94a8af0853f96c44fa858",
        "model": "virtuo",
        "components": [
            {
                "name": "led_matrix"
            },
            {
                "name": "touchscreen"
            },
            {
                "name": "money_storage"
            },
            {
                "name": "pcb"
            },
            {
                "name": "money_receiver"
            }
        ]
    }
]
```

#### 3. Result of jukeboxes with specified model

GET `http://localhost:8080/jukeboxes?settingId=515ef38b-0529-418f-a93a-7f2347fc5805&model=angelina`
```
[
    {
        "id": "5ca94a8a8b58770bb38055a0",
        "model": "angelina",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "pcb"
            }
        ]
    },
    {
        "id": "5ca94a8aa2330a0762019ac0",
        "model": "angelina",
        "components": [
            {
                "name": "money_storage"
            },
            {
                "name": "amplifier"
            }
        ]
    }
]
```

#### 4. Unspecified settingId returns error

GET `http://localhost:8080/jukeboxes`
```
message: "Required request parameter 'settingId' for method parameter type String is not present"
```

#### 5. SettingId is not found returns error

GET `http://localhost:8080/jukeboxes?settingId=515ef38b-0529-418f-a93a-7f2347fc5805random`
```
message: "Setting ID 515ef38b-0529-418f-a93a-7f2347fc5805random is not found"
```

#### 6. No jukebox satisfies the required settings returns error

GET `http://localhost:8080/jukeboxes?settingId=bd9df656-323c-4417-b14b-bd9e9743be23`
```
message: "No jukebox satisfies the required settings."
```

### Debugging the Application (Developer Purpose Only)

Below is the steps to run the backend server:
1. Pull the repository.
2. Verify that Maven is installed by running `mvn -v`.
3. Build the Maven package by running `mvn package`.
4. Run the server using `mvn spring-boot:run`.
5. The application should be running and can be checked by visiting `http://localhost:8080/jukeboxes?settingId=<settingId>`.

To run the unit tests:

1. From the base directory, run `mvn test`.
