<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="Paper name:">
        <el-input v-model="queryParam.paperName" clearable></el-input>
      </el-form-item>
      <el-form-item label="Module:" >
        <el-select v-model="queryParam.subjectId"  clearable>
          <el-option v-for="item in subjects" :key="item.id" :value="item.id" :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Username:">
        <el-input v-model="queryParam.username" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">Query</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="paperName" label="Paper name"/>
      <el-table-column prop="userName" label="username" width="100px" />
      <el-table-column  label="Mark" width="100px" >
        <template slot-scope="{row}">
          {{row.userScore}} / {{row.paperScore}}
        </template>
      </el-table-column>
      <el-table-column  label="Answer Result" width="120px" >
        <template slot-scope="{row}">
          {{row.questionCorrect}} / {{row.questionCount}}
        </template>
      </el-table-column>
      <el-table-column prop="doTime" label="Time consumption" width="150px"/>
      <el-table-column prop="createTime" label="Submit time" width="160px"/>
      <el-table-column label="Operation" align="center" width="150px">
        <template slot-scope="{row}">
          <router-link target="_blank" :to="{path:'/read',query:{id:row.id}}" v-if="row.status === 2 ">
            <el-button  type="text" size="small">View</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>

import { mapGetters, mapState, mapActions } from 'vuex'
import Pagination from '@/components/Pagination'
import examPaperAnswerApi from '@/api/examPaperAnwser'

export default {
  components: { Pagination },
  data () {
    return {
      queryParam: {
        paperName: null,
        username: null,
        subjectId: null,
        pageIndex: 1,
        pageSize: 10
      },
      listLoading: false,
      tableData: [],
      total: 0
    }
  },
  created () {
    this.initSubject()
    this.search()
  },
  methods: {
    search () {
      this.listLoading = true
      examPaperAnswerApi.page(this.queryParam).then(data => {
        const re = data.response
        this.tableData = re.list
        this.total = re.total
        this.queryParam.pageIndex = re.pageNum
        this.listLoading = false
      })
    },
    submitForm () {
      this.queryParam.pageIndex = 1
      this.search()
    },
    ...mapActions('exam', { initSubject: 'initSubject' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapGetters('exam', ['subjectEnumFormat']),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>
