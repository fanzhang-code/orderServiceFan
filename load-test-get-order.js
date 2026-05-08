import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter } from 'k6/metrics';

const status200 = new Counter('status_200');
const status429 = new Counter('status_429');
const status500 = new Counter('status_500');
const statusOther = new Counter('status_other');

export const options = {
    stages: [
        { duration: '30s', target: 2 },
        { duration: '30s', target: 5 },
        { duration: '1m', target: 10 },
        { duration: '30s', target: 5 },
        { duration: '30s', target: 0 },
    ],
    thresholds: {
        checks: ['rate>0.95'],
        http_req_failed: ['rate<0.05'],
        http_req_duration: ['p(95)<1000', 'p(99)<2000'],
    },
};

const BASE_URL = 'http://order-service-lb-1818340912.us-east-2.elb.amazonaws.com';
const ORDER_ID = 'd8499c4b-dbd4-4a8f-b09f-09541bf425e1';

export default function () {
    const res = http.get(`${BASE_URL}/orders/${ORDER_ID}`);

    if (res.status === 200) {
        status200.add(1);
    } else if (res.status === 429) {
        status429.add(1);
    } else if (res.status === 500) {
        status500.add(1);
    } else {
        statusOther.add(1);
    }

    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(0.2);
}