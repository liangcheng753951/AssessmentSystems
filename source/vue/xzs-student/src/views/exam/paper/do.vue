<template>
  <div>
    <el-row  class="do-exam-title">
      <el-col :span="24">
        <span :key="item.itemOrder"  v-for="item in answer.answerItems">
             <el-tag :type="questionCompleted(item.completed)" class="do-exam-title-tag" @click="goAnchor('#question-'+item.itemOrder)">{{item.itemOrder}}</el-tag>
        </span>
        <span class="do-exam-time">
          <label>Time remaining：</label>
          <label>{{formatSeconds(remainTime)}}</label>
        </span>
      </el-col>
    </el-row>
    <el-container  class="app-item-contain">
      <el-header class="align-center">
        <h1>{{form.name}}</h1>
        <div>
          <span class="question-title-padding">Full marks：{{form.score}}</span>
          <span class="question-title-padding">Suggestion Time：{{form.suggestTime}} minutes</span>
        </div>
      </el-header>
      <el-main>
        <el-form :model="form" ref="form" v-loading="formLoading" label-width="100px">
          <el-row :key="index"  v-for="(titleItem,index) in form.titleItems">
            <h3>{{titleItem.name}}</h3>
            <el-card class="exampaper-item-box" v-if="titleItem.questionItems.length!==0">
              <el-form-item :key="questionItem.itemOrder" :label="questionItem.itemOrder+'.'"
                            v-for="questionItem in titleItem.questionItems"
                            class="exam-question-item" label-width="80px" :id="'question-'+ questionItem.itemOrder">
                <QuestionEdit :qType="questionItem.questionType" :question="questionItem"
                              :answer="answer.answerItems[questionItem.itemOrder-1]"/>
              </el-form-item>
            </el-card>
          </el-row>
           <el-row class="do-align-center">
             <el-button type="primary" @click="submitForm">Submit</el-button>
           </el-row>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import { formatSeconds } from '@/utils'
import QuestionEdit from '../components/QuestionEdit'
import examPaperApi from '@/api/examPaper'
import examPaperAnswerApi from '@/api/examPaperAnswer'

export default {
  components: { QuestionEdit },
  data () {
    return {
      form: {},
      formLoading: false,
      answer: {
        questionId: null,
        doTime: 0,
        answerItems: []
      },
      timer: null,
      remainTime: 0
    }
  },
  created () {
    let id = this.$route.query.id
    let _this = this
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      examPaperApi.selectForAssessment(id).then(re => {
        _this.form = re.response
        _this.remainTime = re.response.suggestTime * 60
        _this.initAnswer()
        _this.formLoading = false
        if (re.response.duplicateWork) {
          _this.openConfirm()
        } else {
          _this.timeReduce()
        }
      })
    }
  },
  mounted () {

  },
  beforeDestroy () {
    window.clearInterval(this.timer)
  },
  methods: {
    formatSeconds (theTime) {
      return formatSeconds(theTime)
    },
    timeReduce () {
      let _this = this
      this.timer = setInterval(function () {
        if (_this.remainTime <= 0) {
          _this.submitForm()
        } else {
          ++_this.answer.doTime
          --_this.remainTime
        }
      }, 1000)
    },
    questionCompleted (completed) {
      return this.enumFormat(this.doCompletedTag, completed)
    },
    goAnchor (selector) {
      this.$el.querySelector(selector).scrollIntoView({ behavior: 'instant', block: 'center', inline: 'nearest' })
    },
    initAnswer () {
      this.answer.id = this.form.id
      let titleItemArray = this.form.titleItems
      for (let tIndex in titleItemArray) {
        let questionArray = titleItemArray[tIndex].questionItems
        for (let qIndex in questionArray) {
          let question = questionArray[qIndex]
          this.answer.answerItems.push({ questionId: question.id, content: null, contentArray: [], completed: false, itemOrder: question.itemOrder })
        }
      }
    },
    submitForm () {
      let _this = this
      window.clearInterval(_this.timer)
      _this.formLoading = true
      examPaperAnswerApi.answerSubmit(this.answer).then(re => {
        if (re.code === 1) {
          _this.$alert('Your got ' + re.response + ' marks', 'Assessment Result', {
            confirmButtonText: 'Return to records',
            callback: action => {
              _this.$router.push('/record/index')
            }
          })
        } else {
          _this.$message.error(re.message)
        }
        _this.formLoading = false
      }).catch(e => {
        _this.formLoading = false
      })
    },
    openConfirm () {
      this.$confirm('You have already done this assessment, do you want to do it again?', 'Notice', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        this.timeReduce()
      }).catch(() => {
        let _this = this
        _this.$router.push('/record/index')
      })
    }
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      doCompletedTag: state => state.exam.question.answer.doCompletedTag
    })
  }
}
</script>

<style lang="scss" scoped>
  .align-center {
    text-align: center
  }

  .exam-question-item {
    padding: 10px;

    .el-form-item__label {
      font-size: 15px !important;
    }
  }

  .question-title-padding {
    padding-left: 25px;
    padding-right: 25px;
  }
</style>
