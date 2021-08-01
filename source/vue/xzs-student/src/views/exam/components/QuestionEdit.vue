<template>
  <div style="line-height:1.8">
    <div v-loading="qLoading">
      <div class="q-title" v-html="question.title"/>
      <div class="q-content">
        <el-radio-group v-model="answer.content" @change="answer.completed = true" >
          <el-radio  v-for="item in question.items"  :key="item.prefix"  :label="item.prefix" :value="item.prefix">
            <span class="question-prefix">{{item.prefix}}.</span>
            <span v-html="item.content" class="q-item-span-content"></span>
          </el-radio>
        </el-radio-group>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: 'QuestionShow',
  props: {
    question: {
      type: Object,
      default: function () {
        return {}
      }
    },
    answer: {
      type: Object,
      default: function () {
        return { id: null, content: '', contentArray: [] }
      }
    },
    qLoading: {
      type: Boolean,
      default: false
    },
    qType: {
      type: Number,
      default: 0
    }
  },
  methods: {
  }
}
</script>
