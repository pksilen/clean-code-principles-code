import { v4 as uuidv4 } from 'uuid';
import kafkaClient from './src/broker/kafkaClient';
import KafkaChatMsgBrokerAdminClient from './src/broker/adminclient/KafkaChatMsgBrokerAdminClient';
import WebSocketChatMsgServer from './src/server/WebSocketChatMsgServer';
import KafkaChatMsgBrokerConsumer from './src/broker/consumer/KafkaChatMsgBrokerConsumer';
import ChatMsgServiceImpl from './src/service/ChatMsgServiceImpl';

const serverUuid = uuidv4();
const topic = serverUuid;

new KafkaChatMsgBrokerAdminClient(kafkaClient)
  .tryCreateTopic(topic)
  .then(async () => {
    const chatMsgService = new ChatMsgServiceImpl(serverUuid);

    const chatMsgServer = new WebSocketChatMsgServer(
      serverUuid,
      chatMsgService,
    );

    const chatMsgBrokerConsumer = new KafkaChatMsgBrokerConsumer(
      kafkaClient,
      chatMsgService,
    );

    await chatMsgBrokerConsumer.consumeChatMessages(topic);

    function prepareExit() {
      chatMsgServer.closeServer();
      chatMsgBrokerConsumer.close();
    }

    process.once('SIGINT', prepareExit);
    process.once('SIGQUIT', prepareExit);
    process.once('SIGTERM', prepareExit);
  })
  .catch(() => {
    // Handle error
  });
