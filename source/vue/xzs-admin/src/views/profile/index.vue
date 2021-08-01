<template>
  <div class="app-container">
    <div>
      <el-row >

        <el-col >
          <el-card>
            <el-tabs active-name="account">
              <el-tab-pane label="Account" name="account">
                <account :userInfo="userInfo"  />
              </el-tab-pane>
              <el-tab-pane label="Update password" name="updatePassword">
                <el-form :model="passwordForm" ref="form" label-width="250px" v-loading="formLoading">
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
  </div>
</template>

<script>
import Account from './components/Account'
import userApi from '@/api/user'
import loginApi from "@/api/login";

export default {
  name: 'Profile',
  data () {
    return {
      userInfo: {
        realName: '',
        phone: '',
        lastActiveTime: '',
        createTime: '',
        role: '1',
        imagePath: null
      },
      passwordForm: {
        currentPassword: null,
        newPassword: null,
        newPasswordConfirm: null
      },
      formLoading: false
    }
  },
  components: { Account },
  created () {
    let _this = this
    userApi.getCurrentUser().then(re => {
      _this.userInfo = re.response
    })
  },
  methods: {
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
    },
  }
}
</script>
