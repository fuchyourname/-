<template>
  <div class="py-4 px-4 relative bg-white border rounded-lg shadow-md mb-4" :class="{ 'mb-16': isLast }" @mouseover="hovered = true" @mouseleave="hovered = false">
    <div class="flex items-center">
      <img :src="post.userAvatar" alt="User Avatar" class="w-14 h-14 mr-3 rounded-full">
      <div class="flex flex-col">
        <span class="font-bold text-blue-500">{{ post.userName }}</span>
        <span class="text-sm text-gray-500">{{ getTimeAgo(post.createTime) }}</span>
      </div>
    </div>
    <p class="mt-2 ml-16">{{ post.content }}</p>

    <!-- 歌曲信息展示 -->
    <div v-if="post.song" class="mt-2 ml-16 flex items-center bg-gray-100 w-96 relative" @mouseover="showPlayButton" @mouseleave="hidePlayButton">
      <div class="relative w-14 h-14 mr-3">
        <img :src="post.song.coverPath" alt="Song Cover" class="w-full h-full rounded-lg">
        <!-- 遮罩层 -->
        <div v-show="hovered" class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center">
          <!-- 播放按钮 -->
          <button v-show="post.showPlayButton" class="p-2 rounded-full shadow-md bg-white">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6 fill-black">
              <path stroke-linecap="round" stroke-linejoin="round" d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.347a1.125 1.125 0 0 1 0 1.972l-11.54 6.347a1.125 1.125 0 0 1-1.667-.986V5.653Z" />
            </svg>
          </button>
        </div>
      </div>
      <div class="flex flex-col">
        <span class="font-semibold">{{ post.song.title }}</span>
        <span class="text-gray-500">{{ post.song.artist }}</span>
      </div>
    </div>

    <!-- 点赞和评论按钮 -->
    <div class="flex items-center mt-6 ml-16">
      <button @click="like" class="flex items-center text-gray-500 hover:text-blue-500">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M6.633 10.25c.806 0 1.533-.446 2.031-1.08a9.041 9.041 0 0 1 2.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 0 0 .322-1.672V2.75a.75.75 0 0 1 .75-.75 2.25 2.25 0 0 1 2.25 2.25c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282m0 0h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 0 1-2.649 7.521c-.388.482-.987.729-1.605.729H13.48c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 0 0-1.423-.23H5.904m10.598-9.75H14.25M5.904 18.5c.083.205.173.405.27.602.197.4-.078.898-.523.898h-.908c-.889 0-1.713-.518-1.972-1.368a12 12 0 0 1-.521-3.507c0-1.553.295-3.036.831-4.398C3.387 9.953 4.167 9.5 5 9.5h1.053c.472 0 .745.556.5.96a8.958 8.958 0 0 0-1.302 4.665c0 1.194.232 2.333.654 3.375Z" />
        </svg>
        <span class="ml-2">{{ post.likesCount }}</span>
      </button>
      <button @click="toggleComments" class="flex items-center text-gray-500 hover:text-blue-500 ml-10">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 0 1 .865-.501 48.172 48.172 0 0 0 3.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0 0 12 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018Z" />
        </svg>
        <span class="ml-2">{{ post.comments.length }}</span>
      </button>
    </div>

    <!-- 评论部分 -->
    <CommentSection v-if="post.showComments" :post="post" @add-comment="addComment" @like-comment="likeComment" @toggle-comment-comments="toggleCommentComments" @like-sub-comment="likeSubComment" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import CommentSection from './CommentSection.vue'

const props = defineProps({
  post: Object,
  isLast: Boolean
})

const hovered = ref(false)

const emit = defineEmits(['like', 'toggle-comments', 'add-comment', 'like-comment', 'toggle-comment-comments', 'like-sub-comment'])

const showPlayButton = () => {
  props.post.showPlayButton = true
}

const hidePlayButton = () => {
  props.post.showPlayButton = false
}

const like = () => {
  emit('like', props.post)
}

const toggleComments = () => {
  emit('toggle-comments', props.post)
}

const addComment = (content) => {
  emit('add-comment', props.post, content)
}

const likeComment = (comment) => {
  emit('like-comment', props.post, comment)
}

const toggleCommentComments = (comment) => {
  emit('toggle-comment-comments', comment)
}

const likeSubComment = (comment, subComment) => {
  emit('like-sub-comment', comment, subComment)
}

const getTimeAgo = (timestamp) => {
  const date = new Date(timestamp)
  const now = new Date().getTime()
  const secondsPast = (now - date.getTime()) / 1000

  if (secondsPast < 60) {
    return `${Math.round(secondsPast)}秒前`
  }
  if (secondsPast < 3600) {
    return `${Math.round(secondsPast / 60)}分钟前`
  }
  if (secondsPast < 86400) {
    return `${Math.round(secondsPast / 3600)}小时前`
  }
  return `${Math.round(secondsPast / 86400)}天前`
}
</script>