import {
    addSubscriber,
    removeSubscriber
} from './subscribers.js';

export default function
    loanApplicationSummariesSubscriptionHandler(request, response) {
    // Response headers needed for SSE:
    // - Server sent events are identified with
    //   content type 'text/event-stream'
    // - The connection must be kept alive so that server
    //   can send continuously data to client
    // - Server sent events should not be cached
    const headers = {
        'Content-Type': 'text/event-stream',
        'Connection': 'keep-alive',
        'Cache-Control': 'no-cache',
        // For dev environment you can add CORS header:
        'Access-Control-Allow-Origin': '*'
    };

    response.writeHead(200, headers);

    // Server sent event must be a string beginning with 'data: '
    // and ending with two newline characters
    // First event is empty
    const data = 'data: \n\n';
    response.write(data);
    const subscriberId = addSubscriber(response);
    request.on('close', () => removeSubscriber(subscriberId));
}
