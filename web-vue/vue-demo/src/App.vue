<template>
<HelloWorld/>
<div class="q-my-xl" v-for=  " (detail,index) in dataFilter " :key="index">
  <SearchDetail :dataDetail =detail />
</div>
<div class="q-pa-lg flex flex-center">
    <q-pagination
      :modelValue="current"
      @update:modelValue = "newValue => {
        current = newValue;
        subject.next(newValue);
      }"
      value = "current"
      color="teal"
      :max= dataLength
      :max-pages="4"
      :ellipses="false"
      :boundary-numbers="false"
    />
  </div>
</template>

<script>
import { from, Subject } from 'rxjs';
import { skip, take } from 'rxjs/operators';
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
const subject = new Subject(1);
if (details.length % 4 === 0) {
  dataLength = details.length / 4;
} else {
  dataLength = details.length / 4 + 1;
}
const dataFilter = [];
from(details).pipe(take(4)).subscribe((x) => {
   dataFilter.push(x);
});
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
        dataFilter,
        dataLength,
        subject,
    };
  },
  mounted() {
    subject.subscribe((x) => {
    console.log(x);
    const newData = [];
    from(details).pipe(skip((x - 1) * 4), take(4)).subscribe((y) => {
     newData.push(y);
    });
      this.dataFilter = newData;
    });
  },
};
</script>
