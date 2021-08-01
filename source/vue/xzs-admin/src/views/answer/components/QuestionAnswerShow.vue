<template>
  <div v-loading="qLoading" style="line-height:1.8">
    <div v-if="qType==1||qType==2||qType==3||qType==4||qType==5">
      <div v-if="qType==1" >
        <div class="q-title" v-html="question.title"/>
        <div class="q-content">
          <el-radio-group v-model="answer.content">
            <el-radio  v-for="item in question.items"  :key="item.prefix"  :label="item.prefix" disabled >
              <span class="question-prefix">{{item.prefix}}.</span>
              <span v-html="item.content" class="q-item-span-content"></span>
            </el-radio>
          </el-radio-group>
        </div>
      </div>
      <div class="question-answer-show-item" style="margin-top: 15px">
        <span class="question-show-item">Answer：</span>
        {{answer.content}} &ensp;
        <el-tag :type="doRightTagFormatter(answer.doRight)" >
          {{ doRightTextFormatter(answer.doRight) }}
        </el-tag>
      </div>
      <div class="question-answer-show-item">
        <span class="question-show-item">Mark：</span>
        <span>{{question.score}}</span>
      </div>
      <div class="question-answer-show-item">
        <span class="question-show-item">Difficulty：</span>
        <el-rate disabled v-model="question.difficult" class="question-show-item"></el-rate>
      </div>
      <br/>
      <div class="question-answer-show-item" style="line-height: 1.8">
        <span class="question-show-item">Explanation：</span>
        <span v-html="question.analyze" class="q-item-span-content" />
      </div>
      <div class="question-answer-show-item">
        <span class="question-show-item">Correct answer：</span>
        <span v-if="qType==1||qType==2 ||qType==5" v-html="question.correct" class="q-item-span-content"/>
        <span v-if="qType==3" v-html="trueFalseFormatter(question)" class="q-item-span-content"/>
        <span v-if="qType==4">{{question.correctArray}}</span>
      </div>
    </div>
    <div v-else>
    </div>
  </div>

</template>

<script>
import { mapState, mapGetters } from 'vuex'
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
        return { id: null, content: '', contentArray: [], doRight: false }
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
    trueFalseFormatter (question) {
      return question.items.filter(d => d.prefix === question.correct)[0].content
    },
    doRightTagFormatter (status) {
      return this.enumFormat(this.doRightTag, status)
    },
    doRightTextFormatter (status) {
      return this.enumFormat(this.doRightEnum, status)
    }
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      doRightEnum: state => state.exam.questionAnswer.answer.doRightEnum,
      doRightTag: state => state.exam.questionAnswer.answer.doRightTag
    })
  }
}
</script>
