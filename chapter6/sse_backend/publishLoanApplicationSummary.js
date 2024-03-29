import { subscribers } from './subscribers.js';

export default function publishLoanApplicationSummary(loanApplicationSummary) {
  // Send an event to each subscriber
  // Loan application summary data is converted to JSON
  // before sending the event
  // Server sent event must be a string beginning with 'data: '
  // and ending with two newline characters
  const data = JSON.stringify(loanApplicationSummary);
  subscribers.forEach(({ response }) => response.write(`data: ${data}\n\n`));
}
