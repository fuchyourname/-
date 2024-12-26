<template>
  <div>
    <!-- 评论输入框 -->
    <div class="mt-4 ml-16">
      <div class="border-b border-gray-300 mb-4"></div>
      <div class="flex items-center">
        <img :src="user.avatar" alt="User Avatar" class="w-10 h-10 mr-3 rounded-full">
        <input v-model="post.newComment" @keyup.enter="addComment" type="text" placeholder="添加评论..."
          class="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
      </div>
    </div>

    <!-- 评论列表 -->
    <div v-if="post.comments.length > 0" class="mt-4 ml-16">
      <CommentItem v-for="(comment, index) in post.comments" :key="index" :comment="comment" @like="likeComment"
        @toggle-comments="toggleCommentComments" @add-comment="addSubComment" @like-sub-comment="likeSubComment" />
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import CommentItem from './CommentItem.vue'
const props = defineProps({
  post: Object
})

const user = JSON.parse(localStorage.getItem('user'))

const emit = defineEmits(['add-comment', 'like-comment', 'toggle-comment-comments', 'like-sub-comment'])

const addComment = async () => {
  if (props.post.newComment.trim()) {
    try {
      await axios.post(`/api/user/addComments/`, {
        otherId: props.post.dynamicId,
        content: props.post.newComment,
        type: 5
      }) // 替换为你的API端点
      emit('add-comment', props.post.newComment)
      props.post.newComment = ''
    } catch (error) {
      console.error('添加评论失败:', error)
    }
  }
}

const likeComment = (comment) => {
  emit('like-comment', comment)
}

const toggleCommentComments = (comment) => {
  emit('toggle-comment-comments', comment)
}

const addSubComment = (comment, content) => {
  emit('add-comment', comment, content)
}

const likeSubComment = (comment, subComment) => {
  emit('like-sub-comment', comment, subComment)
}
</script>