import React from 'react';
import ReactDOM from 'react-dom/client';
import * as Stomp from '@stomp/stompjs';
import './index.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
   <div></div>
  </React.StrictMode>
);

const stompClient = new Stomp.Client({
    brokerURL: 'ws://localhost:8080/orderservice'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);

    stompClient.subscribe('/user/response', (order) => {
        console.log("Order: ", order.body)
    });

    stompClient.publish({
        destination: "/app/getOrderById",
        body: JSON.stringify({id: '7ec9d4c6-e8da-4db9-925e-0b8a03790491'})
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

stompClient.activate();
