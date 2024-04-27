// import http from "k6/http";
// import { sleep, check, fail } from "k6";
//
// export let options = {
//   stages: [
//     {duration: '1s', target: 1},
//     // {duration: '1m', target: 100},
//     // {duration: '1m', target: 500},
//     // {duration: '1m', target: 750},
//     // {duration: '1m', target: 1000}
//   ],
//   thresholds: {
//     http_req_failed: ['rate<=0.05'],
//     http_req_duration: ['p(90)<=1000', 'p(95)<=1250', 'p(100)<=1500'],
//     checks: ['rate>=0.95']
//   }
// }
//
// const params = {
//   headers: {
//     'Content-Type': 'application/json',
//   },
// };
//
// const uri = "http://52.78.12.179:8080";
// const URI = uri + "/api/v1"
//
// export default () => {
//
//   const loginRes = login();  // 로그인
//
//   check(loginRes, {
//     'loginRes is OK 200': () => {
//       if (loginRes.status !== 200) {
//         console.info('loginRes result >>> ' + loginRes.body);
//       }
//       return loginRes.status === 200;
//     }
//   });
//
//   const token = loginRes.headers['Authorization'];
//   sleep(1);
//
//   const product = products(); // 상품 목록 조회
//
//   check(product, {
//     'products is OK 200': () => {
//       if (product.status !== 200) {
//         console.info('products result >>> ' + product.body);
//       }
//       return product.status === 200;
//     }
//   });
//
//   sleep(1);
//
//   const productDetail = productDetails(1);  // 상품 상세 조회
//
//   check(productDetail, {
//     'productDetail is OK 200': () => {
//       if (productDetail.status !== 200) {
//         console.info('productDetail result >>> ' + productDetail.body);
//       }
//       return productDetail.status === 200;
//     }
//   });
//
//   sleep(1);
//
//   // 주문 페이지 조회
//   const member = memberDetails(token);  // 유저 정보 조회
//
//   check(member, {
//     'member is OK 200': () => {
//       if (member.status !== 200) {
//         console.info('member result >>> ' + member.body);
//       }
//       return member.status === 200;
//     }
//   });
//
//   const memberId = JSON.parse(member.body).id;
//
//   sleep(1);
//
//   const receiver = receiverAll(token);  // 수령인 목록 조회
//
//   check(receiver, {
//     'receiver is OK 200': () => {
//       if (receiver.status !== 200) {
//         console.info('receiver result >>> ' + receiver.body);
//       }
//       return receiver.status === 200;
//     }
//   });
//
//   sleep(1);
//
//   const order = orderCreate(memberId, token);  // 주문 생성
//
//   check(order, {
//     'order is OK 201': () => {
//       if (order.status !== 201) {
//         console.info('order result >>> ' + order.body);
//       }
//       return order.status === 201;
//     }
//   });
//
//   sleep(1);
//
//   const orderNum = JSON.parse(order.body).orderNum;
//   // const paymentRes = paymentAccept(orderNum, token);  // 결제 승인
//   //
//   // check(paymentRes, {
//   //   'paymentRes is OK 200': () => {
//   //     if (paymentRes.status !== 200) {
//   //       console.info('paymentRes result >>> ' + paymentRes.body);
//   //     }
//   //     return paymentRes.status === 200;
//   //   }
//   // });
//   //
//   // sleep(1);
//
//   const orderCompleteRes = orderComplete(orderNum, token);  // 주문 완료
//
//   check(orderCompleteRes, {
//     'orderCompleteRes is OK 200': () => {
//       if (orderCompleteRes.status !== 200) {
//         console.info('orderCompleteRes result >>> ' + orderCompleteRes.body);
//       }
//       return orderCompleteRes.status === 200;
//     }
//   });
// };
//
// function login() {
//   let req_login = {
//     'email': 'lhinav3@gmail.com',
//     'password': '!12345678'
//   };
//   return http.post(URI + "/users/signin", JSON.stringify(req_login), params);
// }
//
// function products() {
//   return http.get(URI + `/products/all`, params);
// }
//
// function productDetails(productId) {
//   return http.get(URI + `/products/details/${productId}`, params);
// }
//
// function memberDetails(token) {
//   return http.get(URI + `/users`, {
//     headers: {
//       'Content-Type': 'application/json',
//       'Authorization': `${token}`
//     }
//   });
// }
//
// function receiverAll(token) {
//   return http.get(URI + `/users/receivers`, {
//     headers: {
//       'Content-Type': 'application/json',
//       'Authorization': `${token}`
//     }
//   });
// }
//
// function orderCreate(memberId, token) {
//   let req_order = {
//     'memberId': memberId,
//     'receiverId': 1,
//     'type': 'CARD',
//     'totalAmount': 10000,
//     'orderProducts': [
//       {
//         'productId': 1,
//         'quantity': 3
//       }
//     ]
//   };
//   return http.post(URI + `/orders`, JSON.stringify(req_order), {
//     headers: {
//       'Content-Type': 'application/json',
//       'Authorization': `${token}`
//     }
//   });
// }
//
// // function paymentAccept(orderId, token) {
// //   let req_payment = {
// //     'amount': 5000,
// //     'orderId': orderId,
// //     'paymentKey': Math.random().toString(36).substring(2)
// //   };
// //   return http.post(URI + `/payments/toss/confirm/${orderId}`, JSON.stringify(req_payment), {
// //     headers: {
// //       'Content-Type': 'application/json',
// //       'Authorization': `${token}`
// //     }
// //   });
// // }
//
// function orderComplete(orderId, token) {
//   let req_orderComplete = {
//     'amount': 1000,
//     'orderId': orderId,
//     'paymentKey': 'testPaymentKey'
//   };
//
//   return http.post(URI + `/orders/${orderId}/complete`, JSON.stringify(req_orderComplete), {
//     headers: {
//       'Content-Type': 'application/json',
//       'Authorization': `${token}`
//     }
//   });
// }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// // import http from "k6/http";
// // import { check, sleep } from "k6";
// //
// // export const options = {
// //   vus: 1000,
// //   duration: "10s"
// // };
// //
// // const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2JiaWUiLCJleHAiOjE3MTI4NTc2MTl9.T1ZtUIkXRarka7mRAGFhBlDIGqtZGE7gIOfRghdcML8';
// // const headers = {
// //   'Authorization': `Bearer${token}`
// // };
// // const BASE_URL = "http://localhost:8080/api/v1/products/all";
// // export default function () {
// //   let res = http.get(BASE_URL, { cookie: { headers: headers } });
// //   check(res, {
// //     'is status 200': (r) => r.status === 200,
// //   });
// //   sleep(1);
// // }