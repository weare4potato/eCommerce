## 🏗아키텍쳐
![potato drawio](https://github.com/tangpoo/eCommerce/assets/131866367/c76b0c58-4b8a-4a34-87e8-0d98a6b228d7)

## 🍀주요 기술

- 언어 : Java
- 서버 : AWS EC2, Github Action, Docker, RDS, S3, CloudFront
- 모니터링 : Prometheus + Grafana, CloudWatch
- DB : MySQL, Redis
- 프레임워크 : Spring Boot, Spring Data JPA, Gradle, Swagger
- 오픈 API : 토스페이먼츠 PG, OAuth Kakao Login, 

## 🗣️기술적 의사결정

- OAuth Kakao Login API
  - 사용자가 번거로운 회원가입 과정을 거치지 않고도 간편하게 서비스를 시용할 수 있는 방법이 필요
  - 사용자에게 친숙한 Kakao Login을 통해 신뢰성 있고 간편한 로그인 선택지를 제시
- Toss Payment API
  - 사용자에게 안전한 결제 서비스를 제공할 방법이 필요
  - Toss Payment는 간단한 조작을 통해 위젯을 커스텀 할 수 있어, 우리의 정책에 맞지 않는 결제 수단들을 위젯에서 간단히 제거할 수 있었음
  - 추후 KakaoPay, NaverPay 등의 간편한 결제 선택지 또한 구현할 예정으로, 두 Open API와의 연동도 가능
- Redis 
  - Redis의 다양한 확장성과 데이터 안정성을 고려하여 채택
  - 앞으로 메세지, 위치 서비스 등의 다양한 기능을 추가로 개발해 나갈 가능성을 생각함
- Redisson
  - 분산 시스템에서도 빠르고 효율적인 동시성 제어 구현 가능
  - lettuce보다 상대적으로 서버에 부하를 줄일 수 있음
  - 다양한 데이터 구조를 지원하기 때문에 여러 종류의 락을 유연하게 생성하고 관리 가능
  - Lock획득에 대한 재시도와 타임아웃 지정 가능
- Github Action
    - 개발을 진행하면서 배포된 서버를 새로운 버전으로 교체해야 하는 일이 빈번히 일어날 것이라 예상함
      자동화된 test 와 배포를 통해 코드의 안정성과 개발자의 생산성을 증가 시키는 효과를 얻을 수 있었음
    - 협업 툴로 Github을 사용하고 있기 때문에, Github Action을 사용하여 CI/CD를 설정하면 하나의 플랫폼에서 모든 작업을 처리할 수 있다는 장점이 있음
- CloudFront
    - 로딩마다 이미지같은 대용량 데이터를 수십, 수백 개를 오리진 서버에서 직접 불러오는 것은 과금 위험성 및, 트래픽에 영향을 줌
    - 중간에 CDN을 설치함으로써 첫 로딩 이후부터는 CDN에서 복사본을 갖고 오기에 트래픽 부담을 줄여주고 과금 위험성을 낮출 수 있음
- Docker
  - 개발, 테스트 및 프로덕션 환경에서의 일관된 환경을 제공
  - Docker 컨테이너는 가볍고 빠르게 시작할 수 있으므로, AutoScaling 환경에서의 트래픽 대응이 신속해 짐
  - 배포 편의성 증가
- Monitoring
  - 서버의 성능 지표 확인과 에러 감지를 위해 Monitoring을 구성
  - 100% 신뢰할 수 있는 메트릭 정보는 없고, 서버가 다운되면 그 동안의 지표를 잃어버리는 것을 고려하여 Prometheus + Grafana, CloudWatch로 이중 모니터링을 구축
  - 두 모니터링 툴 모두 CPU, Memory가 임계점을 넘어서면 Slack을 통해 개발팀에 메시지를 발송하도록 Alert 설정

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
