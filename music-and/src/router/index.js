// router/index.js
import { createWebHistory, createRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

import LoginView from '../views/LoginView.vue'
import HomeView from '../views/HomeView.vue'
import PersonView from '../views/PersonView.vue'
import LikedList from '../components/LikedList.vue'
import FansList from '../components/FansList.vue'
import MyPlaylist from '../components/MyPlayList.vue'
import FollowList from '../components/FollowList.vue'
import PlayerList from '../views/PlayerList.vue'
import CategoryPlaylistsView from '../views/CategoryPlaylistsView.vue'
import SongListView from '../views/SongListView.vue'
import CommunityView from '../views/CommunityView.vue'
import SongListDetailsView from '../views/SongListDetailsView.vue'
import IndexView from '../views/IndexView.vue'
import CommentsList from '../components/CommentsList.vue'
import MusicUploadView from '../views/MusicUploadView.vue'
import MusicList from '../components/MusicList.vue'
import DynamicsList from '../components/DynamicsList.vue'
import MyMusicView from '../views/MyMusicView.vue'
import SerachResView from '../views/SerachResView.vue'

const routes = [
  { path: '/', component: LoginView },
  { path: '/login', component: LoginView },
  {
    path: '/home',
    component: HomeView,
    children: [
      {
        path: 'my',
        component: PersonView,
        children: [
          {
            path: 'playlist',
            component: MyPlaylist
          },
          {
            path: 'dynamicsList',
            component: DynamicsList
          }
        ]
      },
      {
        path: 'myMusic',
        component: MyMusicView,
        children: [
          {
            path: 'songlistdetails/:id',
            component: SongListDetailsView,
            props: true,
          },
        ]
      },
      {
        path: 'serach/:query',
        name: 'serachRes',
        component: SerachResView,
        props:true
      },
      {
        path: 'follow',
        component: FollowList
      },
      {
        path: 'fans',
        component: FansList
      },
      {
        path: 'player',
        component: PlayerList
      },
      {
        path: 'category',
        component: CategoryPlaylistsView
      },
      {
        path: 'songlist',
        component: SongListView
      },
      {
        path: 'community',
        component: CommunityView
      },
      {
        path: 'songlistdetails/:id',
        component: SongListDetailsView,
      },
      {
        path: 'index',
        component: IndexView
      },
      {
        path: 'upload',
        component: MusicUploadView
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router