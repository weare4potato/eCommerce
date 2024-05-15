import http from "k6/http";
import { check, fail } from "k6";

export let options = {
  stages: [
    {duration: '20s', target: 200},
    {duration: '40s', target: 200},
    // {duration: '1m', target: 500},
    // {duration: '1m', target: 750},
    // {duration: '1m', target: 1000}
  ],
  thresholds: {
    http_req_failed: ['rate<=0.05'],
    http_req_duration: ['p(90)<=1000', 'p(95)<=1250', 'p(100)<=1500'],
    checks: ['rate>=0.95']
  }
}

export function setup() {
  // 최초 한 번 로그인하여 토큰을 얻습니다.
  const loginRes = login();
  let token;

  // 로그인이 성공했을 경우 토큰을 저장합니다.
  check(loginRes, {
    'loginRes is OK 200': () => {
      if (loginRes.status === 200) {
        token = loginRes.headers['Authorization'];
      } else {
        console.info('loginRes result >>> ' + loginRes.body);
        fail('Login failed');
      }
      return loginRes.status === 200;
    }
  });

  return token;
}

const uri = "http://3.34.91.233:8080";
const URI = uri + "/api/v1";

const params = {
  headers: {
    'Content-Type': 'application/json',
  },
};

export default (token) => {
  const order = getOrder(`${token}`); // getOrder 함수를 호출하여 주문 정보를 가져옴

  check(order, {
    'order is OK 200': () => {
      if (order.status !== 200) {
        console.info('order result >>> ' + order.body);
      }
      return order.status === 200;
    }
  });
}

function login() {
  let req_login = {
    'email': 'lhinav3@gmail.com',
    'password': '!12345678'
  };
  return http.post(URI + "/users/signin", JSON.stringify(req_login), params);
}

function getOrder(token) {
  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${token}` // 저장된 토큰을 사용합니다.
    }
  };
  return http.get(URI + `/orders`, params);
}
