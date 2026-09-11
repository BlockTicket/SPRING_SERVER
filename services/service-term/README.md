# service-term 로컬 개발

이용약관 REST API를 실행하고 실제 MySQL까지 연결해 확인하는 방법입니다.
아래 명령은 모두 `SPRING_SERVER` 프로젝트 루트에서 실행합니다.

## 준비

- JDK 21: IntelliJ의 Project SDK와 Gradle JVM을 21로 설정합니다.
- Docker Desktop을 실행합니다.
- `services/service-term/.env.example`을 같은 폴더의 `.env`로 복사합니다. 기존 `.env`가 있다면 덮어쓰지 말고 값을 확인합니다.

`.env.example`은 아래 Compose의 로컬 전용 계정과 13306 포트 설정을 담습니다.
`.env`는 Git에서 제외됩니다. 운영 DB의 접속 정보를 넣지 마세요.
이미 같은 이름의 환경변수가 설정되어 있다면 환경변수가 `.env`보다 우선하므로 IntelliJ 실행 설정도 확인합니다.

## MySQL과 서버 실행

```sh
docker compose --env-file services/service-term/.env -f services/service-term/compose.yaml up -d --wait
./gradlew :services:service-term:bootRun --args='--spring.profiles.active=dev'
```

- MySQL: `127.0.0.1:13306`, 데이터베이스 `blockticket_term`
- API: `http://127.0.0.1:8084`
- `dev` 프로필은 프로젝트 루트 기준 `services/service-term/.env`를 읽습니다.
- Gradle `bootRun`의 작업 디렉터리는 프로젝트 루트로 고정했습니다.
- 기본 `application.yaml`의 배포용 환경변수 설정은 유지합니다.
- 로컬 서버와 DB 포트는 `127.0.0.1`에만 연결합니다.

IntelliJ에서 직접 실행할 때는 `TermApplication`의 실행 설정을 다음과 같이 맞춥니다.

| 항목 | 값 |
| --- | --- |
| Main class | `tikitaka.service.term.TermApplication` |
| JRE | JDK 21 |
| Working directory | `$PROJECT_DIR$` (`SPRING_SERVER` 루트) |
| Program arguments | `--spring.profiles.active=dev` |

서버가 시작되면 다른 터미널에서 확인합니다.

```sh
curl --fail http://127.0.0.1:8084/api/terms
```

처음 실행한 DB에서는 `data: []`가 반환됩니다. IntelliJ HTTP Client를 사용할 수 있다면
`requests.http`를 열어 위에서부터 요청을 실행하세요. 등록 응답의 UUID가 다음 요청에 자동으로 전달됩니다.
5번 요청은 이 파일로 만든 약관을 삭제하므로, 수정 결과를 보고 싶다면 4번 후 3번을 다시 실행합니다.

## 테스트

기존 단위 테스트와 MockMvc 테스트는 Docker 없이 실행합니다.

```sh
./gradlew :services:service-term:test
```

실제 서버와 MySQL을 사용하는 통합 테스트는 별도 명령으로 실행합니다.

```sh
./gradlew :services:service-term:integrationTest
```

통합 테스트는 Testcontainers가 만드는 새 `mysql:8.4` 컨테이너에만 연결합니다.
Compose DB나 로컬 `.env`는 사용하지 않습니다. 첫 실행에는 이미지 다운로드가 필요합니다.
Docker가 꺼져 있으면 실패하며, 테스트를 조용히 건너뛰지 않습니다.
일반 `test`/`check`에는 포함하지 않았으므로 전체 검증 시 두 명령을 모두 실행합니다.

검증 내용:

- HTTP 등록 → 전체/상세 조회 → 수정 → 삭제 → 삭제 후 404
- 255자 한국어 제목과 긴 한국어 본문이 MySQL에 저장되는지 SQL로 확인
- 256자 제목이 400으로 거절되고 DB에 저장되지 않는지 확인

긴 본문을 저장할 수 있도록 `term.content`는 `LONGTEXT`로 명시합니다.
이미 테이블이 있는 환경에 반영할 때는 기존 컬럼 타입과 스키마 변경 절차를 먼저 확인합니다.

`@DynamicPropertySource`가 테스트 컨테이너의 임의 포트와 계정을 Spring에 전달합니다.
HTTP 요청이 사용하는 트랜잭션은 테스트 메서드와 다르므로, 테스트 전 초기화도 이 전용 DB에서만 수행합니다.

## 종료 및 데이터 유지

서버는 실행 터미널에서 `Ctrl+C`로 종료합니다. 로컬 DB는 다음 명령으로 정지합니다.

```sh
docker compose --env-file services/service-term/.env -f services/service-term/compose.yaml stop
```

Compose DB 데이터는 `blockticket-term-local_mysql-data` 볼륨에 남으며 다음 `up`에서 다시 사용합니다.
Testcontainers의 테스트 DB는 테스트 종료 시 정리됩니다.

## 아직 팀 확인이 필요한 항목

- 성공 응답의 `httpStatus`/`status`와 상세 조회의 객체/배열 표기
- DELETE 요청 body에 중복 id가 필요한지 여부
- 입력 검증 오류의 공통 응답 형식
- Gateway 경로 및 등록·수정·삭제의 접근 권한

위 정책을 확정하기 전까지 이 개발용 설정을 외부 공개 환경에 사용하지 않습니다.
