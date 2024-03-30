import { v4 as uuidv4 } from 'uuid';
import kafkaClient from './src/broker/kafkaClient';
import KafkaChatMsgBrokerAdminClient from './src/broker/adminclient/KafkaChatMsgBrokerAdminClient';
import WebSocketChatMsgServer from './src/server/WebSocketChatMsgServer';
import KafkaChatMsgBrokerConsumer from './src/broker/consumer/KafkaChatMsgBrokerConsumer';
import ChatMsgServiceImpl from './src/service/ChatMsgServiceImpl';

const instanceUuid = uuidv4();
const topic = instanceUuid;

new KafkaChatMsgBrokerAdminClient(kafkaClient)
  .tryCreateTopic(topic)
  .then(() => {
    const chatMsgService = new ChatMsgServiceImpl(instanceUuid);
    const chatMsgServer = new WebSocketChatMsgServer(instanceUuid);

    const chatMsgBrokerConsumer = new KafkaChatMsgBrokerConsumer(
      kafkaClient,
      chatMsgService,
    );

    chatMsgBrokerConsumer.consumeChatMessages(topic);

    function prepareExit() {
      chatMsgServer.closeServer();
      chatMsgBrokerConsumer.close();
    }

    process.once('SIGINT', prepareExit);
    process.once('SIGQUIT', prepareExit);
    process.once('SIGTERM', prepareExit);
  })
  .catch((error) => {
    // Handle error
  });
