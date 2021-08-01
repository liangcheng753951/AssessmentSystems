<template>
  <div style="margin-top: 10px" class="app-contain">
    <el-row :gutter="50">
      <el-col :span="7">
        <el-card>
          <div slot="header" class="clearfix">
            <span>Profile</span>
          </div>
          <el-row style="text-align: center">
            <el-upload action="/api/student/upload/image"  accept=".jpg,.png" :show-file-list="false"  :on-success="uploadSuccess">
              <el-avatar class="el-dropdown-avatar" :size="100" :src="form.imagePath === null ? require('@/assets/avatar.png') : form.imagePath"></el-avatar>
            </el-upload>
          </el-row>
          <el-row class="user-info-userName">
            <label>{{form.userName}}</label>
          </el-row>
          <el-divider/>
          <el-row class="user-info-fullInfo">
            <label>Name：{{form.realName}}</label><br/>
            <label>Register time：{{form.createTime}}</label><br/>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="17">
        <el-card shadow="hover">
          <el-tabs active-name="update" type="card">
            <el-tab-pane label="Update Info" name="update">
              <el-form :model="form" ref="form" label-width="140px" v-loading="formLoading" :rules="rules">
                <el-form-item label="Real name：" prop="realName" required>
                  <el-input v-model="form.realName"></el-input>
                </el-form-item>
                <el-form-item label="Sex：">
                  <el-select v-model="form.sex" placeholder="Sex" clearable>
                    <el-option v-for="item in sexEnum" :key="item.key" :value="item.key"
                               :label="item.value"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="Birthday：">
                  <el-date-picker v-model="form.birthDay" value-format="yyyy-MM-dd" type="date" placeholder="click to select"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm">Update</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="Update password" name="updatePassword">
              <el-form :model="passwordForm" ref="form" label-width="250px" v-loading="formLoading" :rules="rules">
                <el-form-item label="Current password：" required type="password">
                  <el-input v-model="passwordForm.currentPassword" type="password"></el-input>
                </el-form-item>
                 <el-form-item label="New password：" required>
                  <el-input v-model="passwordForm.newPassword" type="password"></el-input>
                </el-form-item>
                 <el-form-item label="New password confirm：" required type="password">
                  <el-input v-model="passwordForm.newPasswordConfirm" type="password"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="updatePassword">Update</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userApi from '@/api/user'
import { mapGetters, mapState } from 'vuex'
import loginApi from '@/api/login'

export default {
  data () {
    return {
      form: {
        realName: '',
        sex: '',
        birthDay: null,
        createTime: null,
        imagePath: null
      },
      passwordForm: {
        currentPassword: null,
        newPassword: null,
        newPasswordConfirm: null
      },
      formLoading: false,
      rules: {
        realName: [
          { required: true, message: 'Please input the real name.', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    let _this = this
    userApi.getCurrentUser().then(re => {
      _this.form = re.response
    })
  },
  methods: {
    uploadSuccess (re, file) {
      if (re.code === 1) {
        window.location.reload()
      } else {
        this.$message.error(re.message)
      }
    },
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          userApi.update(this.form).then(data => {
            if (data.code === 1) {
              _this.$message.success(data.message)
            } else {
              _this.$message.error(data.message)
            }
            _this.formLoading = false
          }).catch(e => {
            _this.formLoading = false
          })
        } else {
          return false
        }
      })
    },

    updatePassword () {
      let _this = this
      userApi.updatePassword(this.passwordForm).then(data => {
        if (data.code === 1) {
          _this.$message.success(data.message)
          _this.logout()
        } else {
          _this.$message.error(data.message)
        }
        _this.formLoading = false
      }).catch(e => {
        _this.formLoading = false
      })
    },
    logout () {
      let _this = this
      loginApi.logout().then(function (result) {
        if (result && result.code === 1) {
          _this.clearLogin()
          _this.$router.push({ path: '/login' })
        }
      })
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      sexEnum: state => state.user.sexEnum
    })
  }
}
</script>

<style lang="scss" scoped>

</style>
