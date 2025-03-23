// index.js
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
import AdminIndex from '../views/admin/IndexView.vue'
import UserListView from '../views/admin/UserListView.vue'
import AlbumListView from '../views/admin/AlbumListView.vue'
import MusicListView from '../views/admin/MusicListView.vue'
import PicListView from '../views/admin/PicListView.vue'
import PlayerListView from '../views/admin/PlayerListView.vue'
import PlayListView from '../views/admin/PlayListView.vue'
import SongItems from '../components/search/SongItems.vue'
import PlayerItems from '../components/PlayerList.vue'
import GenresListView from '../views/admin/GenresListView.vue'
import UserItems from '../components/search/UserItems.vue'
import PlayListItems from '../components/search/PlayListItems.vue'
import AlbumItems from '../components/search/AlbumItems.vue'
import PlayerItem from '../components/search/PlayerItems.vue'
import UserDetailView from '../views/UserDetailView.vue'
import PlayerDetailView from '../views/PlayerDetailView.vue'
import SongsList from '../components/player/SongsList.vue'
import AlbumsList from '../components/player/AlbumsList.vue'
import SettingsView from '../views/SettingsView.vue'
import RankListView from '../views/RankListView.vue'
import RankListDetailView from '../views/RankListDetailView.vue'
import RegisterView from '../views/RegisterView.vue'

const routes = [
  { path: '/', component: LoginView },
  { path: '/login', component: LoginView },
  { path: '/register', component: RegisterView },
  {
    path: '/admin',
    component: AdminIndex,
    meta: { requiresAuth: true },
    children: [
      {
        path: 'index',
        component: UserListView,
        meta: { requiresAuth: true },
      },
      {
        path: 'music',
        meta: { requiresAuth: true },
        component: MusicListView,
      },
      {
        path: 'album',
        component: AlbumListView,
        meta: { requiresAuth: true },
      },
      {
        path: 'player',
        component: PlayerListView,
        meta: { requiresAuth: true },
      },
      {
        path: 'playlist',
        component: PlayListView,
        meta: { requiresAuth: true },
      },
      {
        path: 'pic',
        component: PicListView,
        meta: { requiresAuth: true },
      },
      {
        path: 'genres',
        component: GenresListView,
        meta: { requiresAuth: true },
      }
    ]
  },
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
        path: 'rankList/:id',
        component: RankListView,
        meta: { requiresAuth: true },
      },
      {
        path: 'userDetail/:id',
        component: UserDetailView,
        meta: { requiresAuth: true },
      },
      {
        path: 'playerDetail/:id',
        component: PlayerDetailView,
        meta: { requiresAuth: true },
        children: [
          {
            path: 'songslist/:id',
            component: SongsList,
            props: true,
            meta: { requiresAuth: true },
          },
          {
            path: 'albumslist/:id',
            component: AlbumsList,
            props: true,
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
          {
            path: 'playerlist',
            component: PlayerItems,
            props: true,
            meta: { requiresAuth: true },
          },
        ]
      },
      {
        path: 'serach/:keyword',
        name: 'serachRes',
        component: SerachResView,
        props: true,
        meta: { requiresAuth: true },
        children: [
          {
            path: 'songItems/:keyword',
            name: 'songItems',
            component: SongItems,
            props: true,
            meta: { requiresAuth: true },
          },
          {
            path: 'userItems/:keyword',
            name: 'userItems',
            component: UserItems,
            props: true,
            meta: { requiresAuth: true },
          },
          {
            path: 'playListItems/:keyword',
            name: 'playListItems',
            component: PlayListItems,
            props: true,
            meta: { requiresAuth: true },
          },
          {
            path: 'albumItems/:keyword',
            name: 'albumItems',
            component: AlbumItems,
            props: true,
            meta: { requiresAuth: true },
          },
          {
            path: 'playerItems/:keyword',
            name: 'playerItems',
            component: PlayerItem,
            props: true,
            meta: { requiresAuth: true },
          }
        ]
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
        path: 'rankList',
        component: RankListDetailView,
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
      },
      {
        path: 'settings',
        component: SettingsView,
        meta: { requiresAuth: true },
      },
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
      next();
    }
  } else {
    next();
  }
});

export default router