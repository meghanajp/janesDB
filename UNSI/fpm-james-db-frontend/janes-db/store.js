import { derived, readable, writable } from "svelte/store";

export const plans = writable(["Live", "Offline"]);
export const plan = writable(null);
export const frameConfig = writable({
  SRC_W: 2560,
  SRC_H: 2560,
  CUR_W: 2560,
  CUR_H: 2560,
});
export const classificationFrameData = writable({
  frameId: null,
  targets: [],
});

export function createUrlObjectStore() {
  const { subscribe, update } = writable("");

  return {
    subscribe,
    make: frame =>
      update(prevUrl => {
        if (prevUrl) URL.revokeObjectURL(prevUrl);
        let bytes = new Uint8Array(frame);
        return URL.createObjectURL(new Blob([bytes.buffer]));
      }),
  };
}
export const classificationFrame = createUrlObjectStore();
export const jsonData=writable({
  image:"",
  country:"",
  radarRange:"",
  randarCourse:"",
  cameraFov:""
})
export const widgetOpenMutex = writable(false);
export const imageUrl=writable()