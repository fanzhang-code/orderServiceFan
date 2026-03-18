import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    vus: 10, // Virtual Users (Concurrent clients)
    duration: '30s',
};

const BASE_URL = 'http://order-service-alb-2118743509.us-east-2.elb.amazonaws.com';

export default function () {
    // Test the health endpoint
    const res = http.get(`${BASE_URL}/health`);

    check(res, {
        'status is 200': (r) => r.status === 200,
        'transaction time < 500ms': (r) => r.timings.duration < 500,
    });

    sleep(1);
}