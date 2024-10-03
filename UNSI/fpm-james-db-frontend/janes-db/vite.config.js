import { defineConfig } from "vite";
import { svelte } from "@sveltejs/vite-plugin-svelte";
import { viteStaticCopy } from 'vite-plugin-static-copy'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [svelte(),
    viteStaticCopy({
      targets: [
        //Copy all cesium files to static folder
        {
          src: 'src/lib/2D/Assets',
          dest: ''
        },
        {
          src: 'src/lib/2D/Icons',
          dest: ''
        },
        //Copy 3D models to 3DModels folder
        {
          src: 'src/lib/CameraView/Assets',
          dest: ''
        },
        // {
        //   src: path.join(cesiumSource,'Widgets'),
        //   dest: 'Widgets'
        // }

        //Camera Modules' static data
        {
          src: 'src/lib/CameraModule/img/',
          dest: 'cameraModule'
        }
      ]
    }),
  ],
  server: {
    port: 5000,
  },
});
