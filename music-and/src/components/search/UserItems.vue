<template>
  <div class="p-4 ml-32">
    <table class="w-full table-auto border-collapse">
      <thead>
        <tr>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
          <th class="px-4 py-2 text-right text-lg font-bold"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user, index) in users" :key="user.userId">
          <td class="px-4 py-2 text-left">
            <img :src="user.picUrl" alt="User Avatar" class="w-12 h-12 rounded-full">
          </td>
          <td class="px-4 py-2 text-left">
            <h2 class="text-lg font-semibold">{{ user.userName }}</h2>
          </td>
          <td class="px-4 py-2 text-left">
            <p class="text-gray-600">歌单:{{ user.playlistCount }}</p>
          </td>
          <td class="px-4 py-2 text-left">
            <p class="text-gray-600">粉丝:{{ user.followerCount }}</p>
          </td>
          <td class="px-4 py-2 text-right">
            <button @click="toggleFollow(user)" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
              {{ isFollowing(user) ? '取消关注' : '关注' }}
            </button>
          </td>
        </tr>
        <tr v-if="users.length === 0">
          <td colspan="5" class="text-center p-4 text-gray-500">
            <p>没有找到相关用户</p>
          </td>
        </tr>
        <tr v-if="users.length > 0 && !hasMoreData">
          <td colspan="5" class="text-center p-4 text-gray-500">
            <p>没有更多数据了</p>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const props = defineProps({
  keyword: String
});

const searchQuery = ref(props.keyword);
const currentPage = ref(1);
const pageSize = ref(10);

const users = ref([]);
const followingUsers = ref(new Set()); // 存储已关注的用户ID
const hasMoreData = ref(true);

// 初始化已关注的用户列表
const initializeFollowing = () => {
  // 这里假设有一个API来获取当前用户已关注的用户列表
  // 根据实际情况实现
  axios.get('/api/user/getFollowUserList').then(response => {
    // 取出每个用户的用户ID
    const followingUserIds = response.data.data.map(user => user.userId);
  followingUsers.value = new Set(followingUserIds);
  });
};

// 判断是否已关注某个用户
const isFollowing = (user) => {
  return followingUsers.value.has(user.userId);
};

// 关注或取消关注用户
const toggleFollow = async (user) => {
  if (isFollowing(user)) {
    followingUsers.value.delete(user.userId);
    // 调用取消关注的API
    try {
      await axios.post('/api/user/removeFollow', {}, { params: { followUserId: user.userId } });
    } catch (error) {
      console.error('取消关注失败:', error);
      followingUsers.value.add(user.userId); // 如果失败则恢复状态
    }
  } else {
    followingUsers.value.add(user.userId);
    // 调用关注的API
    try {
      await axios.post('/api/user/addFollow', {}, { params: { followUserId: user.userId } });
    } catch (error) {
      console.error('关注失败:', error);
      followingUsers.value.delete(user.userId); // 如果失败则恢复状态
    }
  }
};

async function fetchUsers() {
  try {
    const response = await axios.post('/api/user/getUserPage', {
      name: searchQuery.value,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    });
    users.value = response.data.data;
    // 假设响应中有 hasMore 字段来判断是否有更多数据
    hasMoreData.value = response.data.hasMore || false;
  } catch (error) {
    console.error('获取用户列表失败:', error);
  }
}

onMounted(() => {
  fetchUsers();
  initializeFollowing(); // 初始化已关注的用户列表
});
</script>

<style scoped>
table {
  border-collapse: collapse; /* 合并边框 */
}
td, th {
  border: none; /* 移除边框 */
  padding: 8px;
}
</style>