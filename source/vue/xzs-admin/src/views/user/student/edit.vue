<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="130px" v-loading="formLoading" :rules="rules">
      <el-form-item label="Username："  prop="userName" required>
        <el-input v-model="form.userName"></el-input>
      </el-form-item>
      <el-form-item label="Real name：" prop="realName" required>
        <el-input v-model="form.realName"></el-input>
      </el-form-item>
      <el-form-item label="Sex：">
        <el-select v-model="form.sex" placeholder="Sex" clearable>
          <el-option v-for="item in sexEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Birthday：">
        <el-date-picker v-model="form.birthDay" type="date" value-format="yyyy-MM-dd" placeholder="click to select date"/>
      </el-form-item>
      <el-form-item label="Modules：">
        <el-select v-model="form.modules" multiple collapse-tags placeholder="Click to select">
          <el-option
            v-for="item in subjectFilter"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Status：" required>
        <el-select v-model="form.status" placeholder="Status">
          <el-option v-for="item in statusEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">Submit</el-button>
        <el-button @click="resetForm">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'
import userApi from '@/api/user'

export default {
  data () {
    return {
      subjectFilter: null,
      form: {
        id: null,
        userName: 'cl9n20',
        role: 1,
        modules: [],
        realName: 'Cheng Liang',
        status: 1,
        sex: '',
        birthDay: null
      },
      formLoading: false,
      rules: {
        userName: [
          { required: true, message: 'please input the username', trigger: 'blur' }
        ],
        realName: [
          { required: true, message: 'please input the real name', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.initSubject(function () {
      _this.subjectFilter = _this.subjects
    })
    let id = this.$route.query.id
    let _this = this
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      userApi.selectUser(id).then(re => {
        _this.form = re.response
        _this.formLoading = false
      })
    }
  },
  methods: {
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.createUser(this.form).then(data => {
            if (data.code === 1) {
              _this.$message.success(data.message)
              _this.delCurrentView(_this).then(() => {
                _this.$router.push('/user/student/list')
              })
            } else {
              _this.$message.error(data.message)
              _this.formLoading = false
            }
          }).catch(e => {
            _this.formLoading = false
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
        userName: '',
        realName: '',
        role: 1,
        status: 1,
        sex: '',
        birthDay: null
      }
      this.form.id = lastId
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' }),
    ...mapActions('exam', { initSubject: 'initSubject' })
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      sexEnum: state => state.user.sexEnum,
      statusEnum: state => state.user.statusEnum
    }),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>
