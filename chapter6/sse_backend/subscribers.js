import { v4 as uuidv4 } from 'uuid';

export let subscribers = [];

export function addSubscriber(response) {
    const id = uuidv4();

    const subscriber = {
        id,
        response
    };

    subscribers.push(subscriber);
    return id;
}

export function removeSubscriber(id) {
    subscribers = subscribers.filter((subscriber) =>
        subscriber.id !== id);
}
