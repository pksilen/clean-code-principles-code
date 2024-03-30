import { v4 as uuidv4 } from 'uuid';
import kafkaClient from './src/broker/kafkaClient';
import KafkaChatMsgBrokerAdminClient from './src/broker/adminclient/KafkaChatMsgBrokerAdminClient';
import WebSocketChatMsgServer from './src/server/WebSocketChatMsgServer';
import KafkaChatMsgBrokerConsumer from './src/broker/consumer/KafkaChatMsgBrokerConsumer';

const instanceUuid = uuidv4();
const topic = instanceUuid;

try {
  await new KafkaChatMsgBrokerAdminClient(kafkaClient).tryCreateTopic(topic);
} catch (error) {
  // Handle error
}

const chatMsgServer = new WebSocketChatMsgServer(instanceUuid);
const chatMsgBrokerConsumer = new KafkaChatMsgBrokerConsumer(kafkaClient);
chatMsgBrokerConsumer.consumeChatMessages(topic);

function prepareExit() {
  chatMsgServer.closeServer();
  chatMsgBrokerConsumer.close();
}

process.once('SIGINT', prepareExit);
process.once('SIGQUIT', prepareExit);
process.once('SIGTERM', prepareExit);
