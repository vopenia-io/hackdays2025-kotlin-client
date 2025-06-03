# Kotlin Hackdays 2025 PoC

A modern video conferencing solution developed in Kotlin, designed to offer seamless integration between web services, video communication, and user API.

## ✨ Main Features

- 📹 Real-time video conferencing
- 👥 User management
- ...

## 📦 SDKs

In order to compile and execute this SDK locally, you will need to install the following SDKs locally :

- [Vopenia's LiveKit wrapper](https://github.com/vopenia-io/client-sdk-kotlin-multiplatform)
- [Visio's SDK](https://github.com/vopenia-io/kotlin-meet-sdk)
- [LiveKit swift](https://github.com/codlab/LiveKitClient)

The LiveKitClient for Swift needs to be in the same folder as this repositories and the LiveKit wrapper

### Local maven installation

Inside both the Kotlin Multiplatform projects, run the following in the order :

```bash
cd client-sdk-kotlin-multiplatform
bash ./gradlew publishToMavenLocal

cd kotlin-meet-sdk
bash ./gradlew publishToMavenLocal
```

## 🔐 Local Dev Tunneling

In order to join Visio's meeting locally, the localhost:8071 needs to be exposed and the tokens needs to be fetched locally.

Inside the `kotlin-meet-sdk`, follow the instructions from the [README](https://github.com/vopenia-io/kotlin-meet-sdk)

Note : both devices needs to be on the same network in order for it to work

## 📄 Licence

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

Developed with ❤️ in Kotlin
