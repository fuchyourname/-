<template>
    <div>
        <TransitionRoot as="template" :show="sidebarOpen">
            <Dialog class="relative z-50 lg:hidden" @close="sidebarOpen = false">
                <TransitionChild as="template" enter="transition-opacity ease-linear duration-300"
                    enter-from="opacity-0" enter-to="opacity-100" leave="transition-opacity ease-linear duration-300"
                    leave-from="opacity-100" leave-to="opacity-0">
                    <div class="fixed inset-0 bg-gray-900/80" />
                </TransitionChild>

                <div class="fixed inset-0 flex">
                    <TransitionChild as="template" enter="transition ease-in-out duration-300 transform"
                        enter-from="-translate-x-full" enter-to="translate-x-0"
                        leave="transition ease-in-out duration-300 transform" leave-from="translate-x-0"
                        leave-to="-translate-x-full">
                        <DialogPanel class="relative mr-16 flex w-full max-w-xs flex-1">
                            <TransitionChild as="template" enter="ease-in-out duration-300" enter-from="opacity-0"
                                enter-to="opacity-100" leave="ease-in-out duration-300" leave-from="opacity-100"
                                leave-to="opacity-0">
                                <div class="absolute left-full top-0 flex w-16 justify-center pt-5">
                                    <button type="button" class="-m-2.5 p-2.5" @click="sidebarOpen = false">
                                        <span class="sr-only">Close sidebar</span>
                                        <XMarkIcon class="size-6 text-white" aria-hidden="true" />
                                    </button>
                                </div>
                            </TransitionChild>
                            <div class="flex grow flex-col gap-y-5 overflow-y-auto bg-white px-6 pb-4">
                        
                                <nav class="flex flex-1 flex-col">
                                    <ul role="list" class="flex flex-1 flex-col gap-y-7">
                                        <li v-for="item in navigation" :key="item.name">
                                            <RouterLink v-if="!item.children" :to="item.href"
                                                :class="[item.current ? 'bg-gray-50 text-indigo-600' : 'text-gray-700 hover:bg-gray-50 hover:text-indigo-600', 'group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold']">
                                                <component :is="item.icon"
                                                    :class="[item.current ? 'text-indigo-600' : 'text-gray-400 group-hover:text-indigo-600', 'size-6 shrink-0']"
                                                    aria-hidden="true" />
                                                {{ item.name }}
                                            </RouterLink>
                                            <Disclosure v-else as="div" class="space-y-1" v-slot="{ open }">
                                                <DisclosureButton
                                                    :class="[open ? 'bg-gray-50' : '', 'group flex w-full items-center rounded-md p-2 text-left text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600']">
                                                    <component :is="item.icon"
                                                        :class="[open ? 'text-indigo-600' : 'text-gray-400 group-hover:text-indigo-600', 'size-6 shrink-0']"
                                                        aria-hidden="true" />
                                                    <span
                                                        class="ml-3 flex-1 flex items-center justify-between py-2 text-sm/6 font-semibold">
                                                        {{ item.name }}
                                                        <ChevronRightIcon
                                                            :class="[open ? 'rotate-90 text-indigo-600' : 'text-gray-400', 'ml-6 h-5 w-5 group-hover:text-indigo-600']"
                                                            aria-hidden="true" />
                                                    </span>
                                                </DisclosureButton>
                                                <DisclosurePanel class="space-y-1">
                                                    <RouterLink v-for="child in item.children" :key="child.name"
                                                        :to="child.href"
                                                        :class="[child.current ? 'bg-gray-50 text-indigo-600' : 'text-gray-700 hover:bg-gray-50 hover:text-indigo-600', 'group flex w-full items-center rounded-md p-2 pl-11 text-sm/6 font-semibold']">
                                                        {{ child.name }}
                                                    </RouterLink>
                                                </DisclosurePanel>
                                            </Disclosure>
                                        </li>
                                        <li>
                                            <div class="text-xs/6 font-semibold text-gray-400">Your teams</div>
                                            <ul role="list" class="-mx-2 mt-2 space-y-1">
                                                <li v-for="team in teams" :key="team.name">
                                                    <RouterLink :to="team.href"
                                                        :class="[team.current ? 'bg-gray-50 text-indigo-600' : 'text-gray-700 hover:bg-gray-50 hover:text-indigo-600', 'group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold']">
                                                        <span
                                                            :class="[team.current ? 'border-indigo-600 text-indigo-600' : 'border-gray-200 text-gray-400 group-hover:border-indigo-600 group-hover:text-indigo-600', 'flex size-6 shrink-0 items-center justify-center rounded-lg border bg-white text-[0.625rem] font-medium']">{{
                                                                team.initial }}</span>
                                                        <span class="truncate">{{ team.name }}</span>
                                                    </RouterLink>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="mt-auto">
                                            <RouterLink to="#"
                                                class="group -mx-2 flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600">
                                                <Cog6ToothIcon
                                                    class="size-6 shrink-0 text-gray-400 group-hover:text-indigo-600"
                                                    aria-hidden="true" />
                                                Settings
                                            </RouterLink>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </DialogPanel>
                    </TransitionChild>
                </div>
            </Dialog>
        </TransitionRoot>

        <!-- Static sidebar for desktop -->
        <div class="hidden lg:fixed lg:inset-y-0 lg:z-50 lg:flex lg:w-72 lg:flex-col">
            <div class="flex grow flex-col gap-y-5 overflow-y-auto border-r border-gray-200 bg-white px-6 pb-4">
                <div class="flex h-16 shrink-0 items-center">
                    
                </div>
                <nav class="flex flex-1 flex-col">
                    <ul role="list" class="flex flex-1 flex-col gap-y-1">
                        <li v-for="item in navigation" :key="item.name">
                            <template v-if="!item.children">
                                <RouterLink :to="item.href"
                                           :class="[item.current ? 'bg-gray-50 text-indigo-600' : 'text-gray-700 hover:bg-gray-50 hover:text-indigo-600', 'group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold']">
                                    <component :is="item.icon"
                                               :class="[item.current ? 'text-indigo-600' : 'text-gray-400 group-hover:text-indigo-600', 'size-6 shrink-0']"
                                               aria-hidden="true" />
                                    {{ item.name }}
                                </RouterLink>
                            </template>
                            <template v-else>
                                <Disclosure as="div" class="space-y-1" v-slot="{ open }" :default-open="true">
                                    <button @click="handleParentClick(item)"
                                            :class="[open ? 'bg-gray-50' : '', 'group flex w-full items-center rounded-md p-2 text-left text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600']">
                                        <component :is="item.icon"
                                                   :class="[open ? 'text-indigo-600' : 'text-gray-400 group-hover:text-indigo-600', 'size-6 shrink-0']"
                                                   aria-hidden="true" />
                                        <span
                                            class="ml-3 flex-1 flex items-center justify-between py-2 text-sm/6 font-semibold">
                                          {{ item.name }}
                                          <ChevronRightIcon
                                              :class="[open ? 'rotate-90 text-indigo-600' : 'text-gray-400', 'ml-6 h-5 w-5 group-hover:text-indigo-600']"
                                              aria-hidden="true" />
                                        </span>
                                    </button>
                                    <DisclosurePanel class="space-y-1">
                                        <RouterLink v-for="child in item.children" :key="child.name" :to="child.href"
                                                  :class="[child.current ? 'bg-gray-50 text-indigo-600' : 'text-gray-700 hover:bg-gray-50 hover:text-indigo-600', 'group flex w-full items-center rounded-md pl-11 text-sm/6 font-semibold pt-1']">
                                            {{ child.name }}
                                        </RouterLink>
                                    </DisclosurePanel>
                                </Disclosure>
                            </template>
                        </li>
                        <li>
                            <div class="text-xs/6 font-semibold text-gray-400">Your teams</div>
                            <ul role="list" class="-mx-2 mt-2 space-y-1">
                                <li v-for="team in teams" :key="team.name">
                                    <RouterLink :to="team.href"
                                               :class="[team.current ? 'bg-gray-50 text-indigo-600' : 'text-gray-700 hover:bg-gray-50 hover:text-indigo-600', 'group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold']">
                                        <span
                                            :class="[team.current ? 'border-indigo-600 text-indigo-600' : 'border-gray-200 text-gray-400 group-hover:border-indigo-600 group-hover:text-indigo-600', 'flex size-6 shrink-0 items-center justify-center rounded-lg border bg-white text-[0.625rem] font-medium']">{{
                                                    team.initial }}</span>
                                        <span class="truncate">{{ team.name }}</span>
                                    </RouterLink>
                                </li>
                            </ul>
                        </li>
                        <li class="mt-auto">
                            <RouterLink to="#"
                                       class="group -mx-2 flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600">
                                <Cog6ToothIcon class="size-6 shrink-0 text-gray-400 group-hover:text-indigo-600"
                                               aria-hidden="true" />
                                Settings
                            </RouterLink>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <div class="lg:pl-72">
            <div
                class="sticky top-0 z-40 flex h-16 shrink-0 items-center gap-x-4 border-b border-gray-200 bg-white px-4 shadow-sm sm:gap-x-6 sm:px-6 lg:px-8">
                <div class="flex flex-1 gap-x-4 self-stretch lg:gap-x-6">
                    <div class="flex items-center flex-1">
                        <MagnifyingGlassIcon class="pointer-events-none size-5 self-center text-gray-400 mr-2"
                                             aria-hidden="true" />
                        <input type="text"
                               class="block h-7 w-full border-0 bg-transparent p-0 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                               placeholder="Search..." aria-label="Search..." />
                    </div>
                    <div class="flex items-center gap-x-4 lg:gap-x-6">
                        <button type="button" class="-m-2.5 p-2.5 text-gray-400 hover:text-gray-500">
                            <span class="sr-only">View notifications</span>
                            <BellIcon class="size-6" aria-hidden="true" />
                        </button>

                        <!-- Separator -->
                        <div class="hidden lg:block lg:h-6 lg:w-px lg:bg-gray-200" aria-hidden="true" />

                        <!-- Profile dropdown -->
                        <Menu as="div" class="relative">
                            <MenuButton class="-m-1.5 flex items-center p-1.5">
                                <span class="sr-only">Open user menu</span>
                                <img class="size-8 rounded-full bg-gray-50"
                                     :src="user.avatar"
                                     alt="" />
                                <span class="hidden lg:flex lg:items-center">
                                    <span class="ml-4 text-sm/6 font-semibold text-gray-900" aria-hidden="true">
                                        {{ user.username }}
                                    </span>
                                    <ChevronDownIcon class="ml-2 size-5 text-gray-400" aria-hidden="true" />
                                </span>
                            </MenuButton>
                            <transition enter-active-class="transition ease-out duration-100"
                                      enter-from-class="transform opacity-0 scale-95"
                                      enter-to-class="transform opacity-100 scale-100"
                                      leave-active-class="transition ease-in duration-75"
                                      leave-from-class="transform opacity-100 scale-100"
                                      leave-to-class="transform opacity-0 scale-95">
                                <MenuItems
                                    class="absolute right-0 z-10 mt-2.5 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none">
                                    <MenuItem v-slot="{ active }">
                                        <RouterLink to="/home/my/playlist"
                                                    :class="[active ? 'bg-gray-100 outline-none' : '', 'block px-4 py-2 text-sm text-gray-700']">
                                            我的</RouterLink>
                                    </MenuItem>
                                    <MenuItem v-slot="{ active }">
                                        <a @click="logout"
                                           :class="[active ? 'bg-gray-100 outline-none' : '', 'block px-4 py-2 text-sm text-gray-700']">登出</a>
                                    </MenuItem>
                                </MenuItems>
                            </transition>
                        </Menu>
                    </div>
                </div>
            </div>

            <main class="py-10">
                <div class="px-4 sm:px-6 lg:px-8">
                    <router-view></router-view>
                </div>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import {
    Dialog,
    DialogPanel,
    Menu,
    MenuButton,
    MenuItem,
    MenuItems,
    TransitionChild,
    TransitionRoot,
    Disclosure,
    DisclosureButton,
    DisclosurePanel,
} from '@headlessui/vue'
import {
    BellIcon,
    Cog6ToothIcon,
    DocumentDuplicateIcon,
    FolderIcon,
    HomeIcon,
    UsersIcon,
    XMarkIcon,
} from '@heroicons/vue/24/outline'
import { ChevronDownIcon, MagnifyingGlassIcon, ChevronRightIcon } from '@heroicons/vue/20/solid'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'

const userStore = useUserStore()

const router = useRouter()

const user = userStore.user;

const navigation = [
    { name: '用户管理', href: '/admin/index', icon: HomeIcon, current: true },
    {
        name: '音乐管理',
        href: '/admin/music',
        icon: FolderIcon,
        current: false,
        children: [
            { name: '歌手管理', href: '/admin/player', current: false },
            { name: '专辑管理', href: '/admin/album', current: false },
            { name: '歌单管理', href: '/admin/playlist', current: false },
        ],
    },
    { name: '标签管理', href: '/admin/genres', icon: DocumentDuplicateIcon, current: false },
]
const teams = [
    { id: 1, name: '图片库', href: '/admin/pic', initial: 'P', current: false },
]
const sidebarOpen = ref(false)

const handleParentClick = (item) => {
    router.push(item.href);
}
const logout = () => {
  userStore.logout();
  router.push('/login');
}

</script>