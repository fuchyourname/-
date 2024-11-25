import { createMemoryHistory, createRouter } from 'vue-router'

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
import SongsList from '../components/SongsList.vue'
import CommentsList from '../components/CommentsList.vue'
import MusicUploadView from '../views/MusicUploadView.vue'

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
            path: 'like',
            component: LikedList
          },
          {
            path: 'fans',
            component: FansList
          },
          {
            path: 'playlist',
            component: MyPlaylist
          },
          {
            path: 'follow',
            component: FollowList
          }
        ]
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
        children: [
          {
            path: 'songs',
            component: SongsList,
            props: true
          },
          {
            path: 'comment',
            component: CommentsList,
            props: true
          }
        ]
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
  ,
]

const router = createRouter({
  history: createMemoryHistory(),
  routes,
})

export default router