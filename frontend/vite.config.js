import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
    server: {
    proxy: {
      "/auth": {
        target: "http://localhost:8080/backend/v1",
        changeOrigin: true
      },
      "/oauth2": {
        target: "http://localhost:8080/backend/v1",
        changeOrigin: true
      },
      "/user": {
        target: "http://localhost:8080/backend/v1",
        changeOrigin: true
      }
    }
  },
  plugins: [react()],
})
