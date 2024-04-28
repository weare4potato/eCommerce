## 🏗아키텍쳐
![제목 없는 다이어그램.drawio.png](..%2F..%2FUsers%2FUser%2FDownloads%2F%EC%A0%9C%EB%AA%A9%20%EC%97%86%EB%8A%94%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.drawio.png)

## 🍀주요 기술

- 언어 : Java
- 서버 : AWS EC2, Github Action, Docker, RDS, S3, CloudFront
- 모니터링 : Actuator, Prometheus + Grafana, CloudWatch
- DB : MySQL, Redis
- 프레임워크 : Spring Boot, Spring Data JPA, Gradle, Swagger
- 오픈 API : 토스페이먼츠 PG

## 🗣️기술적 의사결정

- Redis 
  - Redis의 다양한 확장성과 데이터 안정성을 고려하여 채택.
  - 앞으로 메세지, 위치 서비스 등의 다양한 기능을 추가로 개발해 나갈 가능성을 생각함.
- Redisson
  - 분산 시스템에서도 빠르고 효율적인 동시성 제어 구현 가능
  - lettuce보다 서버에 부하를 줄일 수 있음
  - Map, List, Set등의 다양한 데이터 구조를 지원하기 때문에 여러 종류의 락을 유연하게 생성하고 관리 가능
  - Lock획득에 대한 재시도와 타임아웃 지정 가능
- Github Action
    - 개발을 진행하면서 배포된 서버를 새로운 버전으로 교체해야 하는 일이 빈번히 일어날 것이라 예상했다.
      자동화된 test 와 배포를 통해 코드의 안정성과 개발자의 생산성을 증가 시키는 효과를 얻을 수 있었다.
    - 협업 툴로 Github을 사용하고 있기 때문에, Github Action을 사용하여 CI/CD를 설정하면 하나의 플랫폼에서 모든 작업을 처리할 수 있다는 장점이 있었다.
- CDN
    - 로딩마다 이미지같은 대용량 데이터를 수십, 수백 개를 오리진 서버에서 직접 불러오는 것은 과금 위험성 및, 트래픽에 영향을 준다.
    - 중간에 CDN을 설치함으로써 첫 로딩 이후부터는 CDN에서 복사본을 갖고 오기에 트래픽 부담을 줄여주고 과금 위험성을 낮출 수 있다.
- Docker
  - 개발, 테스트 및 프로덕션 환경에서의 일관된 환경을 제공
  - 팀원들간의 버전 이슈 걱정이 없음
  - 배포 편의성 증가

## 🛠트러블슈팅

- 검색 성능 이슈
  - 상품 이름에 인덱싱을 걸었지만, 여전히 Full Table Scan 이 발생
  - Full Text Index 를 통해 3990ms -> 138ms 로 개선
- Cors 에러
  - 유저테스트를 위한 프론트와의 연결 과정 중 Cors 에러 발생
  - Preflight 요청에 access-control-request 가 설정되지 않아 발생한 문제
  - Preflight 요청이 OPTIONS 메서드인지, access-control-request 가 포함되었는지 확인한 후 interceptor를 통과하도록 설정
- DeadLock
  - 동시에 같은 상품에 대한 주문 요청 시, 주문이 생성되는 갯수와 감소하는 상품의 재고수의 차이와 함께 DeadLock 이 발생
  - Redisson 을 통한 분산락 구현