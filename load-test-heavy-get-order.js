import http from 'k6/http';
import { check } from 'k6';
import { Counter } from 'k6/metrics';

const status200 = new Counter('status_200');
const status400 = new Counter('status_400');
const status403 = new Counter('status_403');
const status404 = new Counter('status_404');
const status429 = new Counter('status_429');
const status500 = new Counter('status_500');
const status502 = new Counter('status_502');
const status503 = new Counter('status_503');
const status504 = new Counter('status_504');
const statusOther = new Counter('status_other');

export const options = {
    stages: [
        { duration: '1m', target: 50 },
        { duration: '2m', target: 150 },
        { duration: '3m', target: 300 },
        { duration: '2m', target: 300 },
        { duration: '1m', target: 0 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<5000', 'p(99)<8000'],
    },
};

const BASE_URL = 'http://order-service-lb-1818340912.us-east-2.elb.amazonaws.com';
const ORDER_ID = 'd8499c4b-dbd4-4a8f-b09f-09541bf425e1';

export default function () {
    const res = http.get(`${BASE_URL}/orders/${ORDER_ID}`);

    if (res.status === 200) status200.add(1);
    else if (res.status === 400) status400.add(1);
    else if (res.status === 403) status403.add(1);
    else if (res.status === 404) status404.add(1);
    else if (res.status === 429) status429.add(1);
    else if (res.status === 500) status500.add(1);
    else if (res.status === 502) status502.add(1);
    else if (res.status === 503) status503.add(1);
    else if (res.status === 504) status504.add(1);
    else statusOther.add(1);

    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}