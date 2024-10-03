export function logSuccess(message) {
  console.log(`%c${message}`, `color: green; font-size: 32px;`);
}

export function logFailure(message) {
  console.log(`%c${message}`, `color: red; font-size: 32px;`);
}

export function receivedMessage(message) {
  console.log("Message Received", message);
}
