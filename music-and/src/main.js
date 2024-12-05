// main.js
import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import './assets/styles/global.css';
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import axios from 'axios';
import { useUserStore } from './stores/user';

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

// 配置 Axios 发送 Cookie
axios.defaults.withCredentials = true;

const app = createApp(App)

// 注册 Pinia
app.use(pinia)

// 初始化用户状态
const userStore = useUserStore();
userStore.initialize().then(() => {
  // 用户状态初始化完成后，注册路由并挂载应用
  app.use(router)
  app.mount('#app')
}).catch((error) => {
  console.error('Failed to initialize user store:', error);
  // 处理初始化失败的情况
  app.use(router)
  app.mount('#app')
});