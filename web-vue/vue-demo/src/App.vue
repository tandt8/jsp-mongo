<template>
<HelloWorld/>
<div class="q-my-xl" v-for=  " (detail,index) in dataFilter " :key="index">
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
import { from } from 'rxjs';
import { take } from 'rxjs/operators';
import { ref } from 'vue';
import HelloWorld from './components/HelloWorld.vue';
import SearchDetail from './components/SearchDetail.vue';

const details = [{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link1 doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' },
{ header: 'Link doc', detail: 'This is some text in a paragraph' }];
let dataLength;
const currents = ref(1);
if (details.length % 4 === 0) {
  dataLength = details.length / 4;
} else {
  dataLength = details.length / 4 + 1;
}
const dataFilter = [];
from(details).pipe(take(4)).subscribe((x) => {
   dataFilter.push(x);
});
console.log(currents.value);
export default {
  name: 'LayoutDefault',

  components: {
    HelloWorld,
    SearchDetail,
    },

  setup() {
    return {
      current: currents,
    };
  },
  data() {
    return {
        dataFilter,
        dataLength,
    };
  },
};
</script>
