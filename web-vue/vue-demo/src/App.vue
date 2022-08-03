<template>
<HelloWorld/>
<div class="q-my-xl" v-for=  " (detail,index) in details " :key="index">
  <SearchDetail :dataDetail =detail />
</div>
<div class="q-pa-lg flex flex-center">
    <q-pagination
      v-model="current"
      color="teal"
      :max= dataLength
      :max-pages="4"
      :ellipses="false"
      :boundary-numbers="false"
    />
  </div>
</template>

<script>
import { ref } from 'vue';
import HelloWorld from './components/HelloWorld.vue';
import SearchDetail from './components/SearchDetail.vue';

const details = [{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' }];
let dataLength;
if (details.length % 4 === 0) {
  dataLength = details.length / 4;
} else {
  dataLength = details.length / 4 + 1;
}

export default {
  name: 'LayoutDefault',

  components: {
    HelloWorld,
    SearchDetail,
    },

  setup() {
    return {
      current: ref(1),
    };
  },

  data() {
    return {
        details,
        dataLength,
    };
  },
};
</script>
