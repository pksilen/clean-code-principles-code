import express from 'express';
import bodyParser from 'body-parser';
import loanApplicationSummariesSubscriptionHandler from './loanApplicationSummariesSubscriptionHandler.js';

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.get(
  '/subscribe-to-loan-application-summaries',
  loanApplicationSummariesSubscriptionHandler,
);

app.listen(3001);
