import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
  vus: 1000,
  duration: "10s"
};

const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2JiaWUiLCJleHAiOjE3MTI4NTc2MTl9.T1ZtUIkXRarka7mRAGFhBlDIGqtZGE7gIOfRghdcML8';
const headers = {
  'Authorization': `Bearer${token}`
};
const BASE_URL = "http://localhost:8080/api/v1/products/all";
export default function () {
  let res = http.get(BASE_URL, { cookie: { headers: headers } });
  check(res, {
    'is status 200': (r) => r.status === 200,
  });
  sleep(1);
}