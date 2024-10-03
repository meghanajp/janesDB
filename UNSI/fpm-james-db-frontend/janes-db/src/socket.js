import { io } from "socket.io-client";
import { logFailure, logSuccess, receivedMessage } from "./utils";

export const CAMERA_SOCK = io(import.meta.env.VITE_CAMERA_SOCK_URL, {
    transports: ["polling", "websocket"],
    upgrade: true,
  });
  
  CAMERA_SOCK.on("connect", _ => logSuccess("connected to AICE server"));
  CAMERA_SOCK.on("disconnect", _ => logFailure("disconnected from AICE server"));
  CAMERA_SOCK.on("error", err => console.log(`error connecting to AICE server ${err}`));
  CAMERA_SOCK.on("message",_ => receivedMessage("Message received successfully"));