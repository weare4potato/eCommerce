import http from "k6/http";
import { sleep, check, fail } from "k6";

export let options = {
  stages: [
    {duration: '20s', target: 200},
    {duration: '40s', target: 200},
    // {duration: '30s', target: 400},
    // {duration: '1m', target: 750},
    // {duration: '1m', target: 1000}
  ],
  thresholds: {
    http_req_failed: ['rate<=0.05'],
    http_req_duration: ['p(90)<=1000', 'p(95)<=1250', 'p(100)<=1500'],
    checks: ['rate>=0.95']
  }
}

const params = {
  headers: {
    'Content-Type': 'application/json',
  },
};

const uri = "http://54.180.79.141:8080";
const URI = uri + "/api/v1"

export default () => {
  const product = products(); // 상품 목록 조회

  check(product, {
    'products is OK 200': () => {
      if (product.status !== 200) {
        console.info('products result >>> ' + product.body);
      }
      return product.status === 200;
    }
  });
}

function products() {
  return http.get(URI + `/products/all`, params);
}