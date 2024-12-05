// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080', // 替换为你的后端服务地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''), // 重写路径，去掉 /api 前缀
        secure: false, // 如果是 https 协议，需要设置为 true
      }
    }
  }
});