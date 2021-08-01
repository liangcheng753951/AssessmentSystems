<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="130px" v-loading="formLoading" :rules="rules">
      <el-form-item label="Module：" prop="subjectId" required>
        <el-select v-model="form.subjectId" placeholder="Module" >
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id" :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Content：" prop="title" required>
        <el-input v-model="form.title"   @focus="inputClick(form,'title')" />
      </el-form-item>
      <el-form-item label="Key points：" prop="keyPoint">
        <el-tag
          :key="tag"
          v-for="tag in form.keyPoints"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)">
          {{tag}}
        </el-tag>
        <el-input
          style="width:150px;"
          class="input-new-tag"
          v-if="keyPointsVisible"
          v-model="keyPointInputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Key Point</el-button>
      </el-form-item>
      <el-form-item label="Options：" required>
        <el-form-item :label="item.prefix" :key="item.prefix"  v-for="(item) in form.items"  label-width="50px" class="question-item-label">
          <el-input v-model="item.prefix"  style="width:50px;" :disabled="true"/>
          <el-input v-model="item.content"  v-if="item.prefix !== 'C'" @focus="inputClick(item,'content')"  class="question-item-content-input"/>
          <el-input v-model="item.content"  v-if="item.prefix === 'C'" :disabled="true"  class="question-item-content-input"/>
        </el-form-item>
      </el-form-item>
      <el-form-item label="analysis：" prop="analyze" required>
        <el-input v-model="form.analyze"  @focus="inputClick(form,'analyze')" />
      </el-form-item>
      <el-form-item label="Score：" prop="score" required>
        <el-input-number v-model="form.score" :precision="1" :step="1" :max="100"></el-input-number>
      </el-form-item>
      <el-form-item label="difficulty：" required>
        <el-rate v-model="form.difficult" class="question-item-rate"></el-rate>
      </el-form-item>
      <el-form-item label="Answer：" prop="correct" required>
        <el-radio-group v-model="form.correct">A
          <el-radio  v-for="item in form.items"  :key="item.prefix"  :label="item.prefix">{{item.prefix}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">Submit</el-button>
        <el-button @click="resetForm">Reset</el-button>
        <el-button type="success" @click="showQuestion">Preview</el-button>
      </el-form-item>
    </el-form>
    <el-dialog  :visible.sync="richEditor.dialogVisible"  append-to-body :close-on-click-modal="false" style="width: 100%;height: 100%"   :show-close="false" center>
      <Ueditor @ready="editorReady"/>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editorConfirm">OK</el-button>
        <el-button @click="richEditor.dialogVisible = false">Cancel</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="questionShow.dialog" style="width: 100%;height: 100%">
      <QuestionShow :qType="questionShow.qType" :question="questionShow.question" :qLoading="questionShow.loading"/>
    </el-dialog>
  </div>
</template>

<script>
import QuestionShow from '../components/Show'
import Ueditor from '@/components/Ueditor'
import { mapActions, mapGetters, mapState } from 'vuex'
import questionApi from '@/api/question'

export default {
  components: {
    Ueditor, QuestionShow
  },
  data () {
    return {
      keyPointsVisible: false,
      keyPointInputValue: '',
      form: {
        id: null,
        questionType: 1,
        subjectId: 1,
        title: '2',
        keyPoints: ['3'],
        items: [
          { prefix: 'A', content: '444' },
          { prefix: 'B', content: '444' },
          { prefix: 'C', content: 'All above' }
        ],
        analyze: '4',
        correct: 'A',
        score: '2'
      },
      subjectFilter: null,
      formLoading: false,
      rules: {
        subjectId: [
          { required: true, message: 'please select the module', trigger: 'change' }
        ],
        title: [
          { required: true, message: 'please input the content', trigger: 'blur' }
        ],
        analyze: [
          { required: true, message: 'please input the analysis', trigger: 'blur' }
        ],
        score: [
          { required: true, message: 'please input the score', trigger: 'blur' }
        ],
        correct: [
          { required: true, message: 'please select the correct answer', trigger: 'change' }
        ]
      },
      richEditor: {
        dialogVisible: false,
        object: null,
        parameterName: '',
        instance: null
      },
      questionShow: {
        qType: 0,
        dialog: false,
        question: null,
        loading: false
      }
    }
  },
  created () {
    let id = this.$route.query.id
    let _this = this
    this.initSubject(function () {
      _this.subjectFilter = _this.subjects
    })
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      questionApi.select(id).then(re => {
        _this.form = re.response
        _this.formLoading = false
      })
    }
  },
  methods: {
    editorReady (instance) {
      this.richEditor.instance = instance
      let currentContent = this.richEditor.object[this.richEditor.parameterName]
      this.richEditor.instance.setContent(currentContent)
      // 光标定位到Ueditor
      this.richEditor.instance.focus(true)
    },
    inputClick (object, parameterName) {
      this.richEditor.object = object
      this.richEditor.parameterName = parameterName
      this.richEditor.dialogVisible = true
    },
    editorConfirm () {
      this.richEditor.object[this.richEditor.parameterName] = this.richEditor.instance.getContent()
      this.richEditor.dialogVisible = false
    },
    questionItemRemove (index) {
      this.form.items.splice(index, 1)
    },
    questionItemAdd () {
      let items = this.form.items
      let newLastPrefix
      if (items.length > 0) {
        let last = items[items.length - 1]
        newLastPrefix = String.fromCharCode(last.prefix.charCodeAt() + 1)
      } else {
        newLastPrefix = 'A'
      }
      items.push({ id: null, prefix: newLastPrefix, content: '' })
    },
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          questionApi.edit(this.form).then(re => {
            if (re.code === 1) {
              _this.$message.success(re.message)
              _this.delCurrentView(_this).then(() => {
                _this.$router.push('/exam/question/list')
              })
            } else {
              _this.$message.error(re.message)
              this.formLoading = false
            }
          }).catch(e => {
            this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    resetForm () {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,
        questionType: 1,
        gradeLevel: null,
        subjectId: null,
        title: '',
        items: [
          { prefix: 'A', content: '' },
          { prefix: 'B', content: '' },
          { prefix: 'C', content: 'All above' }
        ],
        analyze: '',
        correct: '',
        score: '',
        difficult: 0
      }
      this.form.id = lastId
    },
    showQuestion () {
      this.questionShow.dialog = true
      this.questionShow.qType = this.form.questionType
      this.questionShow.question = this.form
    },
    handleClose (tag) {
      this.form.keyPoints.splice(this.form.keyPoints.indexOf(tag), 1)
    },

    showInput () {
      this.keyPointsVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm () {
      let inputValue = this.keyPointInputValue
      if (inputValue) {
        this.form.keyPoints.push(inputValue)
      }
      this.keyPointsVisible = false
      this.keyPointInputValue = ''
    },
    ...mapActions('exam', { initSubject: 'initSubject' }),
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>
<style>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  margin-left: 10px;
}
</style>
