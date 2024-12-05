// router/index.js
import { createWebHistory, createRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

import LoginView from '../views/LoginView.vue'
import HomeView from '../views/HomeView.vue'
import PersonView from '../views/PersonView.vue'
import MyPlaylist from '../components/MyPlayList.vue'
import DynamicsList from '../components/DynamicsList.vue'
import MyMusicView from '../views/MyMusicView.vue'
import SerachResView from '../views/SerachResView.vue'
import FollowList from '../components/FollowList.vue'
import FansList from '../components/FansList.vue'
import PlayerList from '../views/PlayerList.vue'
import CategoryPlaylistsView from '../views/CategoryPlaylistsView.vue'
import SongListView from '../views/SongListView.vue'
import CommunityView from '../views/CommunityView.vue'
import SongListDetailsView from '../views/SongListDetailsView.vue'
import IndexView from '../views/IndexView.vue'
import MusicUploadView from '../views/MusicUploadView.vue'

const routes = [
  { path: '/', component: LoginView },
  { path: '/login', component: LoginView },
  {
    path: '/home',
    component: HomeView,
    meta: { requiresAuth: true },
    children: [
      {
        path: 'my',
        component: PersonView,
        meta: { requiresAuth: true },
        children: [
          {
            path: 'playlist',
            component: MyPlaylist,
            meta: { requiresAuth: true },
          },
          {
            path: 'dynamicsList',
            component: DynamicsList,
            meta: { requiresAuth: true },
          }
        ]
      },
      {
        path: 'myMusic',
        component: MyMusicView,
        meta: { requiresAuth: true },
        children: [
          {
            path: 'songlistdetails/:id',
            component: SongListDetailsView,
            props: true,
            meta: { requiresAuth: true },
          },
        ]
      },
      {
        path: 'serach/:query',
        name: 'serachRes',
        component: SerachResView,
        props: true,
        meta: { requiresAuth: true },
      },
      {
        path: 'follow',
        component: FollowList,
        meta: { requiresAuth: true },
      },
      {
        path: 'fans',
        component: FansList,
        meta: { requiresAuth: true },
      },
      {
        path: 'player',
        component: PlayerList,
        meta: { requiresAuth: true },
      },
      {
        path: 'category',
        component: CategoryPlaylistsView,
        meta: { requiresAuth: true },
      },
      {
        path: 'songlist',
        component: SongListView,
        meta: { requiresAuth: true },
      },
      {
        path: 'community',
        component: CommunityView,
        meta: { requiresAuth: true },
      },
      {
        path: 'songlistdetails/:id',
        component: SongListDetailsView,
        meta: { requiresAuth: true },
      },
      {
        path: 'index',
        component: IndexView,
        meta: { requiresAuth: true },
      },
      {
        path: 'upload',
        component: MusicUploadView,
        meta: { requiresAuth: true },
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  const isAuthenticated = userStore.user !== null;

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      next('/login');
    } else {
      next(); // 如果已登录，允许访问
    }
  } else {
    next(); // 对于不需要认证的页面，直接允许访问
  }
});

export default router