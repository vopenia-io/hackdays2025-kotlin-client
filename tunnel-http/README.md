# Kotlin Hack Proxy tokens

Quick and Dirty token redirection to enable and retrieve tokens used for livekit in dev to have remote access

## Run

Make sure that the gradle.properties keys are 

```properties
HACKDAYS_TESTS_MAIN_HOST=localhost
# HACKDAYS_TESTS_TUNNEL_ENDPOINT is the tunnel-http's host
HACKDAYS_TESTS_TUNNEL_ENDPOINT=http://localhost:8181
HACKDAYS_TESTS_TUNNEL_API=http://localhost:8071
```

```shell
./gradlew :tunnel-http:run
```


## 📄 Licence

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

Developed with ❤️ in Kotlin
