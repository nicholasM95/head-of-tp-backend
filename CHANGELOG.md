# [1.2.0](https://github.com/nicholasM95/head-of-tp-backend/compare/v1.1.1...v1.2.0) (2026-06-30)


### Features

* add  endpoint with updated request handling and OpenAPI integration ([9d0ec67](https://github.com/nicholasM95/head-of-tp-backend/commit/9d0ec678fe5e3491ff87d9f7d44c3fe68f5bf381))
* add  marker type and conditional route marker creation for specific device; update Java version to 25 in CI ([dcb4652](https://github.com/nicholasM95/head-of-tp-backend/commit/dcb4652d29ba6922495b59f1088a001057a3b1f8))
* add DeviceProjection for device data, update repository methods and endpoints to return detailed device info ([d85c882](https://github.com/nicholasM95/head-of-tp-backend/commit/d85c8824f6f3e5e8340580d4963cccc0000ed15f))
* implement FindDeviceByIdUseCase and update controller, repository, and tests to support device lookup by ID ([da03df9](https://github.com/nicholasM95/head-of-tp-backend/commit/da03df9f349ea2a1b7a0eac3f01cb29b7844160f))


### Bug Fixes

* **deps:** update dependency io.swagger.parser.v3:swagger-parser to v2.1.45 ([3c754e6](https://github.com/nicholasM95/head-of-tp-backend/commit/3c754e6635f8e26ef5ba273b4c43c4a7435ab339))
* **deps:** update dependency org.openapitools:jackson-databind-nullable to v0.2.10 ([b795c5e](https://github.com/nicholasM95/head-of-tp-backend/commit/b795c5ecf34d72ba990b59412c5cc513f13b2486))
* **deps:** update dependency org.springframework.boot:spring-boot-starter-parent to v3.5.16 ([6e2fd8a](https://github.com/nicholasM95/head-of-tp-backend/commit/6e2fd8af81ad25900b3bb3f7f37ac9cac31a0089))
* **deps:** update dependency org.springframework.cloud:spring-cloud-dependencies to v2025.1.2 ([9f733e8](https://github.com/nicholasM95/head-of-tp-backend/commit/9f733e893d0478340f8f8307a9fdb9322925128e))

## [1.1.1](https://github.com/nicholasM95/head-of-tp-backend/compare/v1.1.0...v1.1.1) (2026-06-22)


### Bug Fixes

* **deps:** update dependency org.apache.tika:tika-core to v3.3.1 ([63baf77](https://github.com/nicholasM95/head-of-tp-backend/commit/63baf771268c4290460157e8586304372f7e3e0e))

# [1.1.0](https://github.com/nicholasM95/head-of-tp-backend/compare/v1.0.2...v1.1.0) (2026-06-22)


### Features

* add last update of location ([7ec5e15](https://github.com/nicholasM95/head-of-tp-backend/commit/7ec5e154fdffe972dd90e6b72d78521e0c3049dc))
* add new device location registration endpoint ([9395ba0](https://github.com/nicholasM95/head-of-tp-backend/commit/9395ba03ebd659f12f1a19028c8093345a2f885e))
* create participant ([4b124c9](https://github.com/nicholasM95/head-of-tp-backend/commit/4b124c966c3339cc6de6012a678cb347974e077a))
* delete participant ([927d4b9](https://github.com/nicholasM95/head-of-tp-backend/commit/927d4b98213d6aa70b13c25f640160f4aa29b9db))
* find all participants ([889b837](https://github.com/nicholasM95/head-of-tp-backend/commit/889b83717994591b4be7293077828c5ddbfff7da))
* navigate to meters on route ([762b822](https://github.com/nicholasM95/head-of-tp-backend/commit/762b82282fd15a40f22175f25bf63125a55e0112))
* navigate to tp on route ([81912d1](https://github.com/nicholasM95/head-of-tp-backend/commit/81912d19c9e448f42069dfa24f6baa2d273178a0))
* patch participant ([2c82330](https://github.com/nicholasM95/head-of-tp-backend/commit/2c82330b41c7a18d9ed135f7101b676688e4fb9f))
* return device locations ([c12ff23](https://github.com/nicholasM95/head-of-tp-backend/commit/c12ff23aa1ee648aec24aea75ae29a82e89b126d))


### Bug Fixes

* add dependency injection to avoid NPE on FindAllDeviceIds ([94002e2](https://github.com/nicholasM95/head-of-tp-backend/commit/94002e2ee1c22df7ed856b39f83776bedfb0bb4a))
* resolve issue with last position of device ([8d722e6](https://github.com/nicholasM95/head-of-tp-backend/commit/8d722e66d1410b97a55e0690cbefca5d20d9fe38))
* **deps:** update dependency io.swagger.parser.v3:swagger-parser to v2.1.30 ([5f79d95](https://github.com/nicholasM95/head-of-tp-backend/commit/5f79d95c11db8bfae74e2ea931dbfc74422fff24))
* **deps:** update dependency org.springframework.boot:spring-boot-starter-parent to v3.5.3 ([c3b960c](https://github.com/nicholasM95/head-of-tp-backend/commit/c3b960c9f9a775c13e6eb26d6244981dc0bb4b4c))
* **deps:** update dependency org.springframework.cloud:spring-cloud-dependencies to v2025.0.0 ([38b8f08](https://github.com/nicholasM95/head-of-tp-backend/commit/38b8f086f4c9332b8f7d8cc9592958c437b7ffbb))

## [1.0.2](https://github.com/nicholasM95/head-of-tp-backend/compare/v1.0.1...v1.0.2) (2025-05-29)


### Bug Fixes

* save location with speed 0 or bearing 0 ([6bee3b0](https://github.com/nicholasM95/head-of-tp-backend/commit/6bee3b0dc81504c8023f298714aef1a35f9fc4c0))
* set correct time in correct zone ([2e7b5a1](https://github.com/nicholasM95/head-of-tp-backend/commit/2e7b5a15c1a4057a876ec34a2173e59fb4de5b0e))

## [1.0.1](https://github.com/nicholasM95/head-of-tp-backend/compare/v1.0.0...v1.0.1) (2025-05-26)


### Bug Fixes

* **deps:** update dependency io.jenetics:jpx to v3.2.1 ([034bc6d](https://github.com/nicholasM95/head-of-tp-backend/commit/034bc6d35dce29708cc029eb1fc18e4b9e163a8b))
* **deps:** update dependency io.swagger.parser.v3:swagger-parser to v2.1.28 ([48ab3f6](https://github.com/nicholasM95/head-of-tp-backend/commit/48ab3f68ad5a6223af4e9a0e8295f52540887136))
* **deps:** update dependency io.swagger.parser.v3:swagger-parser to v2.1.29 ([e10e04d](https://github.com/nicholasM95/head-of-tp-backend/commit/e10e04de47200b397a5e5cb86846ef782fb2eb90))
* **deps:** update dependency org.springframework.boot:spring-boot-starter-parent to v3.5.0 ([8d687f9](https://github.com/nicholasM95/head-of-tp-backend/commit/8d687f99870da4b579e88fc64e9b16f9b7f60c36))
* **deps:** update tika monorepo to v2.9.4 ([c22b905](https://github.com/nicholasM95/head-of-tp-backend/commit/c22b905fac930e4624fe3bc124b7de7c18a70ea2))
* **deps:** update tika monorepo to v3 ([8822b77](https://github.com/nicholasM95/head-of-tp-backend/commit/8822b7709709ddedc153b973eaa77d641edb06f8))

# 1.0.0 (2025-05-25)


### Features

* add and delete routes, get route info and route points, save device location ([b4f4486](https://github.com/nicholasM95/head-of-tp-backend/commit/b4f4486e5c5feeb722ef9a82ec7af69511ec71bd))
* deploy application to kubernetes ([f8480b2](https://github.com/nicholasM95/head-of-tp-backend/commit/f8480b2e1d570ca0c7fd700db96050de9c250e58))
