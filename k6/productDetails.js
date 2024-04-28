import http from "k6/http";
import { sleep, check, fail } from "k6";

export let options = {
  stages: [
    {duration: '10s', target: 100},
    {duration: '50s', target: 3000},
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

const params = {
  headers: {
    'Content-Type': 'application/json',
  },
};

const uri = "http://52.78.12.179:8080";
const URI = uri + "/api/v1"
let idx = 38605;

export default () => {
  const productDetail = productDetails(idx); // 상품 목록 조회
  idx++;

  check(productDetail, {
    'productDetail is OK 200': () => {
      if (productDetail.status !== 200) {
        console.info('productDetail result >>> ' + productDetail.body);
      }
      return productDetail.status === 200;
    }
  });
}

function productDetails(productId) {
  return http.get(URI + `/products/details/${productId}`, params);
}