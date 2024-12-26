<template>
  <div class="mt-4">
    <div class="flex items-center mb-2">
      <img :src="comment.userPic" alt="User Avatar" class="w-10 h-10 mr-3 rounded-full">
      <div>
        <span class="font-bold text-blue-500">{{ comment.userName }}</span>
        <span class="text-sm text-gray-500 block mt-1">{{ getTimeAgo(comment.createTime) }}</span>
      </div>
    </div>
    <p class="text-gray-700 mt-1 ml-16">{{ comment.content }}</p>
    <div class="flex items-center mt-2 space-x-6 ml-12">
      <button @click="like" class="flex items-center text-gray-500 hover:text-blue-500 transition-colors duration-200">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M6.633 10.25c.806 0 1.533-.446 2.031-1.08a9.041 9.041 0 0 1 2.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 0 0 .322-1.672V2.75a.75.75 0 0 1 .75-.75 2.25 2.25 0 0 1 2.25 2.25c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282m0 0h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 0 1-2.649 7.521c-.388.482-.987.729-1.605.729H13.48c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 0 0-1.423-.23H5.904m10.598-9.75H14.25M5.904 18.5c.083.205.173.405.27.602.197.4-.078.898-.523.898h-.908c-.889 0-1.713-.518-1.972-1.368a12 12 0 0 1-.521-3.507c0-1.553.295-3.036.831-4.398C3.387 9.953 4.167 9.5 5 9.5h1.053c.472 0 .745.556.5.96a8.958 8.958 0 0 0-1.302 4.665c0 1.194.232 2.333.654 3.375Z" />
        </svg>
        <span class="ml-2">{{ comment.likesCount }}</span>
      </button>
      <button @click="toggleSubComments" class="flex items-center text-gray-500 hover:text-blue-500 transition-colors duration-200">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 0 1 .865-.501 48.172 48.172 0 0 0 3.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0 0 12 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018Z" />
        </svg>
        <span class="ml-2">{{ comment.comments.length }}</span>
      </button>
    </div>

    <!-- 子评论输入框 -->
    <div v-if="comment.showComments" class="mt-4 ml-16">
      <div class="flex items-center">
        <img :src="user.avatar" alt="User Avatar" class="w-8 h-8 mr-3 rounded-full">
        <input v-model="comment.newComment" @keyup.enter="addSubComment" type="text" placeholder="添加评论..." class="w-full p-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
      </div>
    </div>

    <!-- 子评论列表 -->
    <div v-if="comment.comments.length > 0 && comment.showComments" class="mt-4 ml-16">
      <CommentItem v-for="(subComment, index) in comment.comments" :key="index" :comment="subComment" @like="likeSubComment" />
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref } from 'vue'
import CommentItem from './CommentItem.vue'

const props = defineProps({
  comment: Object
})

const user = JSON.parse(localStorage.getItem('user'))

const emit = defineEmits(['like', 'add-comment', 'toggle-comment-comments', 'like-sub-comment'])

const addSubComment = async () => {
  if (props.comment.newComment.trim()) {
    try {
      await axios.post(`/api/user/addComments/`, {
        parentId: props.comment.commentId,
        otherId: props.comment.commentId,
        content: props.comment.newComment,
        type: 4
      }) // 替换为你的API端点
      emit('add-comment', props.comment, props.comment.newComment)
      props.comment.newComment = ''
    } catch (error) {
      console.error('添加子评论失败:', error)
    }
  }
}

const like = async () => {
  try {
    await axios.post(`/api/user/likeComment/${props.comment.id}`) // 替换为你的API端点
    emit('like', props.comment)
  } catch (error) {
    console.error('点赞评论失败:', error)
  }
}

const toggleSubComments = () => {
  props.comment.showComments = !props.comment.showComments
}

const likeSubComment = async (subComment) => {
  try {
    await axios.post(`/api/user/likeSubComment/${subComment.id}`) // 替换为你的API端点
    emit('like-sub-comment', subComment)
  } catch (error) {
    console.error('点赞子评论失败:', error)
  }
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