<template>
  <div class="flex min-h-full flex-1">
    <div class="flex flex-1 flex-col justify-center px-4 py-12 sm:px-6 lg:flex-none lg:px-20 xl:px-24">
      <div class="mx-auto w-full max-w-sm lg:w-96">
        <div>
          <p class="h-10 w-auto" />
          <h2 class="mt-8 text-2xl font-bold leading-9 tracking-tight text-gray-900">Sign up</h2>
        </div>

        <div class="mt-10">
          <form @submit.prevent="handleSubmit" class="space-y-6">
            <div>
              <label for="username" class="block text-sm font-medium leading-6 text-gray-900">userName</label>
              <div>
                <input v-model="_username" type="text" id="username" required class="block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
              </div>
            </div>
            <div>
              <label for="email" class="block text-sm font-medium leading-6 text-gray-900">email</label>
              <div>
                <input v-model="_email" type="email" id="email" required class="block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
              </div>
            </div>
            <div>
              <label for="password" class="block text-sm font-medium leading-6 text-gray-900">password</label>
              <div>
                <input v-model="_password" type="password" id="password" required class="block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
              </div>
            </div>
            <div>
              <label for="confirmPassword" class="block text-sm font-medium leading-6 text-gray-900">password</label>
              <div>
                <input v-model="_confirmPassword" type="password" id="confirmPassword" required class="block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
              </div>
            </div>
            <div>
              <button type="submit" class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Sign up</button>
            </div>
          </form>
        </div>

        <div class="mt-10">
          <div class="relative">
            <div class="absolute inset-0 flex items-center" aria-hidden="true">
              <div class="w-full border-t border-gray-200" />
            </div>
            <div class="relative flex justify-center text-sm font-medium leading-6">
              <span class="bg-white px-6 text-gray-900">Already have an account</span>
            </div>
          </div>

          <div class="mt-6">
            <router-link to="/login" class="flex w-full justify-center rounded-md bg-white px-3 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus-visible:ring-transparent">
              Sign in
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="relative hidden w-0 flex-1 lg:block">
      <img class="absolute inset-0 h-full w-full object-cover" src="https://images.unsplash.com/photo-1496917756835-20cb06e75b4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1908&q=80" alt="" />
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const _username = ref('');
const _email = ref('');
const _password = ref('');
const _confirmPassword = ref('');

const handleSubmit = async () => {
  if (_password.value !== _confirmPassword.value) {
    alert('密码和确认密码不一致');
    return;
  }

  try {
    const response = await axios.post('/api/index/register', {
      userName: _username.value,
      email: _email.value,
      password: _password.value
    });

    if (response.data.success) {
      router.push('/login');
    }
  } catch (error) {
    alert('注册失败，请检查您的输入。');
    console.error('注册失败:', error);
  }
};
</script>

<style scoped>
/* Add any additional styles here if needed */
</style>