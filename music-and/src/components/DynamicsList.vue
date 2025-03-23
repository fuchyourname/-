<template>
  <div class="bg-gray-100 flex justify-center min-h-screen">
    <div class="w-4/5 mt-4">
      <DynamicItem v-for="(post, index) in posts" :key="post.username" :post="post" @like="likePost" @toggle-comments="toggleComments" @add-comment="addComment" @like-comment="likeComment" @toggle-comment-comments="toggleCommentComments" @like-sub-comment="likeSubComment" />
      <div class="text-center text-gray-500 mt-8 mb-24">没有更多数据了</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import DynamicItem from './dynamic/DynamicItem.vue'

const posts = ref([])

const fetchPosts = async () => {
  try {
    const response = await axios.get('/api/user/getDynamics') // 替换为你的API端点
    posts.value = response.data.data.map(post => ({
      ...post,
      hovered: false,
      showPlayButton: false,
      likesCount: post.likesCount || 0,
      commentsCount: post.commentsCount || 0,
      showComments: false,
      newComment: '',
      comments: post.comments.map(comment => ({
        ...comment,
        showComments: false,
        newComment: ''
      }))
    }))
  } catch (error) {
    console.error('获取动态列表失败:', error)
  }
}

onMounted(() => {
  fetchPosts()
})

const likePost = async (post) => {
  try {
    await axios.post(`/api/user/likePost/${post.id}`) // 替换为你的API端点
    post.likesCount++
  } catch (error) {
    console.error('点赞失败:', error)
  }
}

const toggleComments = (post) => {
  post.showComments = !post.showComments
}

const addComment = async (post, content) => {
  try {
    await axios.post(`/api/user/addComment/${post.id}`, { content }) // 替换为你的API端点
    post.comments.push({
      userName: '你', // 假设当前用户为“你”
      createTime: new Date().toISOString(),
      content,
      likesCount: 0,
      showComments: false,
      newComment: '',
      comments: []
    })
    post.newComment = ''
  } catch (error) {
    console.error('添加评论失败:', error)
  }
}

const likeComment = async (post, comment) => {
  try {
    await axios.post(`/api/user/likeComment/${comment.id}`) // 替换为你的API端点
    comment.likesCount++
  } catch (error) {
    console.error('点赞评论失败:', error)
  }
}

const toggleCommentComments = (comment) => {
  comment.showComments = !comment.showComments
}

const likeSubComment = async (comment, subComment) => {
  try {
    await axios.post(`/api/user/likeSubComment/${subComment.id}`) // 替换为你的API端点
    subComment.likesCount++
  } catch (error) {
    console.error('点赞子评论失败:', error)
  }
}
</script>