// vite.config.js
import { defineConfig } from "file:///D:/%E6%AF%95%E8%AE%BE/-/music-and/node_modules/vite/dist/node/index.js";
import vue from "file:///D:/%E6%AF%95%E8%AE%BE/-/music-and/node_modules/@vitejs/plugin-vue/dist/index.mjs";
var vite_config_default = defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8080",
        // 替换为你的后端服务地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
        // 重写路径，去掉 /api 前缀
        secure: false
        // 如果是 https 协议，需要设置为 true
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxcdTZCRDVcdThCQkVcXFxcLVxcXFxtdXNpYy1hbmRcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXFx1NkJENVx1OEJCRVxcXFwtXFxcXG11c2ljLWFuZFxcXFx2aXRlLmNvbmZpZy5qc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovJUU2JUFGJTk1JUU4JUFFJUJFLy0vbXVzaWMtYW5kL3ZpdGUuY29uZmlnLmpzXCI7Ly8gdml0ZS5jb25maWcuanNcbmltcG9ydCB7IGRlZmluZUNvbmZpZyB9IGZyb20gJ3ZpdGUnXG5pbXBvcnQgdnVlIGZyb20gJ0B2aXRlanMvcGx1Z2luLXZ1ZSdcblxuLy8gaHR0cHM6Ly92aXRlanMuZGV2L2NvbmZpZy9cbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XG4gIHBsdWdpbnM6IFt2dWUoKV0sXG4gIHNlcnZlcjoge1xuICAgIHByb3h5OiB7XG4gICAgICAnL2FwaSc6IHtcbiAgICAgICAgdGFyZ2V0OiAnaHR0cDovLzEyNy4wLjAuMTo4MDgwJywgLy8gXHU2NkZGXHU2MzYyXHU0RTNBXHU0RjYwXHU3Njg0XHU1NDBFXHU3QUVGXHU2NzBEXHU1MkExXHU1NzMwXHU1NzQwXG4gICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSxcbiAgICAgICAgcmV3cml0ZTogKHBhdGgpID0+IHBhdGgucmVwbGFjZSgvXlxcL2FwaS8sICcnKSwgLy8gXHU5MUNEXHU1MTk5XHU4REVGXHU1Rjg0XHVGRjBDXHU1M0JCXHU2Mzg5IC9hcGkgXHU1MjREXHU3RjAwXG4gICAgICAgIHNlY3VyZTogZmFsc2UsIC8vIFx1NTk4Mlx1Njc5Q1x1NjYyRiBodHRwcyBcdTUzNEZcdThCQUVcdUZGMENcdTk3MDBcdTg5ODFcdThCQkVcdTdGNkVcdTRFM0EgdHJ1ZVxuICAgICAgfVxuICAgIH1cbiAgfVxufSk7Il0sCiAgIm1hcHBpbmdzIjogIjtBQUNBLFNBQVMsb0JBQW9CO0FBQzdCLE9BQU8sU0FBUztBQUdoQixJQUFPLHNCQUFRLGFBQWE7QUFBQSxFQUMxQixTQUFTLENBQUMsSUFBSSxDQUFDO0FBQUEsRUFDZixRQUFRO0FBQUEsSUFDTixPQUFPO0FBQUEsTUFDTCxRQUFRO0FBQUEsUUFDTixRQUFRO0FBQUE7QUFBQSxRQUNSLGNBQWM7QUFBQSxRQUNkLFNBQVMsQ0FBQyxTQUFTLEtBQUssUUFBUSxVQUFVLEVBQUU7QUFBQTtBQUFBLFFBQzVDLFFBQVE7QUFBQTtBQUFBLE1BQ1Y7QUFBQSxJQUNGO0FBQUEsRUFDRjtBQUNGLENBQUM7IiwKICAibmFtZXMiOiBbXQp9Cg==
