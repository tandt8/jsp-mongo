<template>
<h3 class="text-center">Search Engine</h3>
  <div class="search-bar row justify-center ">
    <q-input class="col-5 text-field q-mr-md" outlined v-model="text" name="text"
    label="Type what you need" />
    <q-btn class="col-1 btn-search" color="secondary"  label="Search" @click ="clickme(text)"
    q-btn/>
  </div>
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
import axios from 'axios';
import { from, Subject } from 'rxjs';
import { skip, take } from 'rxjs/operators';
import { ref } from 'vue';
/* eslint-disable */
import SearchDetail from './components/SearchDetail.vue';
import DetailModel from './model/detailmodel';
const details = [];
let dataLength = 0;
const subject = new Subject(1);
const dataFilter = [];
export default {
  name: 'LayoutDefault',

  components: {
    SearchDetail,
    },

  setup() {
    return {
      current: ref(0),
    };
  },
  data() {
    return {
        dataFilter,
        dataLength,
        subject
    };
  },
  methods: {
    async clickme(data) {
      details.length =0;
      this.dataFilter.length = 0;
      this.dataLength = 0;
      const newData = await axios.post('http://localhost:8081/InvertedIndex', { word: data });
      details.push(...newData.data.map(e => new DetailModel(e.file,e.paragraph)));



from(details).pipe(take(4)).subscribe((x) => {
   this.dataFilter.push(x);
});
   this.dataLength = Math.round(details.length / 4)  ;

      this.$forceUpdate();
    },
  },
  mounted() {
    subject.subscribe((x) => {
    const newData = [];
    from(details).pipe(skip((x - 1) * 4), take(4)).subscribe((y) => {
     newData.push(y);
    });
      this.dataFilter = newData;
    });
  },
};
</script>
