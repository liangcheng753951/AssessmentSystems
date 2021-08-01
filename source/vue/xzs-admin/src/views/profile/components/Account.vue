<template>
  <el-form>
    <el-form-item label="Real name">
      <el-input v-model.trim="userInfo.realName" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import userApi from '@/api/user'
export default {
  props: {
    userInfo: {
      type: Object,
      default: () => {
        return {
          realName: ''
        }
      }
    }
  },
  methods: {
    submit () {
      let _this = this
      userApi.updateUser(this.userInfo).then(re => {
        if (re.code === 1) {
          _this.$message.success(re.message)
        } else {
          _this.$message.error(re.message)
        }
      })
    }
  }
}
</script>
