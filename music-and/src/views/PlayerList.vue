<template>
  <div class="flex flex-col space-y-4 pl-4 ml-20">
    <div v-for="category in categories" :key="category.title" class="flex space-x-2">
      <button v-for="item in category.items" :key="item" @click="toggleSelection(item)" :class="{
        ' text-gray-700 font-semibold py-2 px-4 rounded hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-500 text-sm': true,
        'bg-green-500 text-green-100': selectedItems.includes(item)
      }">
        {{ item }}
      </button>
    </div>
  </div>
  <div class="bg-white py-24 sm:py-32">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="mx-auto mt-1 flow-root max-w-2xl sm:mt-1 lg:mx-0 lg:max-w-none">
        <div class="-mt-20 sm:-mx-4 sm:columns-2 sm:text-[0] lg:columns-3">
          <div v-for="testimonial in testimonials" :key="testimonial.author.handle"
            class="mb-4 sm:inline-block sm:w-full sm:px-4">
            <figure class="rounded-2xl bg-gray-50 text-sm leading-6">
              <blockquote class="text-gray-900 p-0">
                <p>{{ `“${testimonial.body}”` }}</p>
              </blockquote>
              <figcaption class="flex items-center gap-x-2 p-0">
                <img class="h-6 w-6 rounded-full bg-gray-50" :src="testimonial.author.imageUrl" alt="" />
                <div>
                  <div class="font-semibold text-gray-900">{{ testimonial.author.name }}</div>
                  <div class="text-gray-600">{{ `@${testimonial.author.handle}` }}</div>
                </div>
              </figcaption>
            </figure>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categories: [
        { title: '全部字母', items: ['全部', ...Array.from({ length: 26 }, (_, i) => String.fromCharCode(65 + i))] },
        { title: '地区', items: ['全部', '内地', '港台', '欧美', '日本', '韩国'] },
        { title: '类型', items: ['全部', '男', '女', '组合'] },
      ],
      selectedItems: [],
      testimonials: [
        {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        },
        {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        },
        {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        }, {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        }
        , {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        }
        , {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        }
        , {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        },
        {
          body: 'Laborum quis quam. Dolorum et ut quod quia. Voluptas numquam delectus nihil. Aut enim doloremque et ipsam.',
          author: {
            name: 'Leslie Alexander',
            handle: 'lesliealexander',
            imageUrl:
              'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
          },
        }
      ]
    };
  },
  methods: {
    toggleSelection(item) {
      const index = this.selectedItems.indexOf(item);
      if (index === -1) {
        this.selectedItems.push(item);
      } else {
        this.selectedItems.splice(index, 1);
      }
    }
  }
};
</script>