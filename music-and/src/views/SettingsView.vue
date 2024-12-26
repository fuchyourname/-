
<template>
  <div class="flex items-center justify-center min-h-screen">
    <form class="w-full max-w-5xl mb-32 items-center">
      <div class="space-y-12 sm:space-y-16">
        <div>
          <h2 class="text-base/7 font-semibold text-gray-900">个人资料</h2>
          <p class="mt-1 max-w-2xl text-sm/6 text-gray-600">这些信息将会公开显示，请谨慎分享。</p>

          <div class="mt-10 space-y-8 border-b border-gray-900/10 pb-12 sm:space-y-0 sm:divide-y sm:divide-gray-900/10 sm:border-t sm:pb-0">
            <div class="sm:grid sm:grid-cols-3 sm:items-start sm:gap-4 sm:py-6">
              <label for="username" class="block text-sm/6 font-medium text-gray-900 sm:pt-1.5">用户名</label>
              <div class="mt-2 sm:col-span-2 sm:mt-0">
                <div class="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                  <span class="flex select-none items-center pl-3 text-gray-500 sm:text-sm">xxxx.com/</span>
                  <input type="text" v-model="user.username"   class="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm/6" />
                </div>
              </div>
            </div>

            <div class="sm:grid sm:grid-cols-3 sm:items-start sm:gap-4 sm:py-6">
              <label for="about" class="block text-sm/6 font-medium text-gray-900 sm:pt-1.5">简介</label>
              <div class="mt-2 sm:col-span-2 sm:mt-0">
                <textarea id="about" name="about" rows="3" 
                v-model="user.description"
                class="block w-full max-w-2xl rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm/6"></textarea>
                <p class="mt-3 text-sm/6 text-gray-600">写几句话介绍一下自己。</p>
              </div>
            </div>

            <div class="sm:grid sm:grid-cols-3 sm:items-center sm:gap-4 sm:py-6">
              <label for="photo" class="block text-sm/6 font-medium text-gray-900">头像</label>
              <div class="mt-2 sm:col-span-2 sm:mt-0">
                <div class="flex items-center gap-x-3">
                   <img class="h-12 w-12 rounded-full" :src="user.avatar" alt="" />
                  <button type="button" class="rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">更改</button>
                </div>
              </div>
            </div>

            <div class="sm:grid sm:grid-cols-3 sm:items-start sm:gap-4 sm:py-6">
              <label for="cover-photo" class="block text-sm/6 font-medium text-gray-900 sm:pt-1.5">封面照片</label>
              <div class="mt-2 sm:col-span-2 sm:mt-0">
                <div class="flex max-w-2xl justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                  <div class="text-center">
                    <PhotoIcon class="mx-auto size-12 text-gray-300" aria-hidden="true" />
                    <div class="mt-4 flex text-sm/6 text-gray-600">
                      <label for="file-upload" class="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500">
                        <span>上传文件</span>
                        <input id="file-upload" name="file-upload" type="file" class="sr-only" />
                      </label>
                      <p class="pl-1">或拖放文件</p>
                    </div>
                    <p class="text-xs/5 text-gray-600">支持 PNG, JPG, GIF 格式，最大 10MB</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="mt-6 flex items-center justify-end gap-x-6">
        <button type="button" class="text-sm/6 font-semibold text-gray-900">取消</button>
        <button type="submit" class="inline-flex justify-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">保存</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { PhotoIcon, UserCircleIcon } from '@heroicons/vue/24/solid'

const user = JSON.parse(localStorage.getItem('user'))
</script>