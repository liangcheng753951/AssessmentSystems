<template>
  <div style="margin-top: 10px" class="app-contain">
    <el-form :model="queryParam" ref="queryForm" :inline="true">
      <el-form-item label="Paper name：">
        <el-input v-model="queryParam.name" clearable></el-input>
      </el-form-item>
      <el-form-item label="Module：" >
        <el-select v-model="queryParam.subjectId"  clearable>
          <el-option v-for="item in subjectList" :key="item.id" :value="item.id" :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">Query</el-button>
      </el-form-item>
    </el-form>
     <el-row :gutter="50">
       <el-col :span="18">
         <el-card>
         <el-table v-loading="listLoading" :data="tableData" fit highlight-current-row style="width: 100%" @row-click="itemSelect">
           <el-table-column prop="paperName" label="Paper name"  />
           <el-table-column prop="subjectName" label="Module"  width="100" />
           <el-table-column label="Status" prop="status" width="100px">
             <template slot-scope="{row}">
               <el-tag :type="statusTagFormatter(row.status)">
                 {{ statusTextFormatter(row.status) }}
               </el-tag>
             </template>
           </el-table-column>
           <el-table-column prop="createTime" label="Start time"  width="170" />
           <el-table-column  align="right" width="70">
             <template slot-scope="{row}">
               <router-link target="_blank" :to="{path:'/read',query:{id:row.id}}" v-if="row.status === 2 ">
                 <el-button  type="text" size="small">View</el-button>
               </router-link>
             </template>
           </el-table-column>
         </el-table>
         </el-card>
         <pagination v-show="total>0" :total="total" :background="false" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
                     @pagination="search" style="margin-top: 20px"/>
       </el-col>
       <el-col  :span="6" >
         <el-card  class="record-answer-info">
           <h3>Info</h3>
            <el-form label-width="50%" >
              <el-form-item label="System mark：">
                <span>{{selectItem.systemScore}}</span>
              </el-form-item>
              <el-form-item label="Final mark：">
                <span>{{selectItem.userScore}}</span>
              </el-form-item>
              <el-form-item label="Full mark：">
                <span>{{selectItem.paperScore}}</span>
              </el-form-item>
              <el-form-item label="Correct question count：">
                <span>{{selectItem.questionCorrect}}</span>
              </el-form-item>
              <el-form-item label="Full question count：">
                <span>{{selectItem.questionCount}}</span>
              </el-form-item>
              <el-form-item label="Time consumption：">
                <span>{{selectItem.doTime}}</span>
              </el-form-item>
            </el-form>
         </el-card>
       </el-col>
     </el-row>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import Pagination from '@/components/Pagination'
import examPaperAnswerApi from '@/api/examPaperAnswer'
import { scrollTo } from '@/utils/scroll-to'
import subjectApi from '@/api/subject'

export default {
  components: { Pagination },
  data () {
    return {
      queryParam: {
        name: null,
        subjectId: null,
        pageIndex: 1,
        pageSize: 10
      },
      listLoading: false,
      subjectList: [],
      tableData: [],
      total: 0,
      selectItem: {
        systemScore: '0',
        userScore: '0',
        doTime: '0',
        paperScore: '0',
        questionCorrect: 0,
        questionCount: 0
      }
    }
  },
  created () {
    this.init()
    this.search()
    scrollTo(0, 800)
  },
  methods: {
    init () {
      let _this = this
      subjectApi.list().then(re => {
        _this.subjectList = re.response
      })
    },
    search () {
      this.listLoading = true
      let _this = this
      examPaperAnswerApi.pageList(this.queryParam).then(data => {
        const re = data.response
        _this.tableData = re.list
        _this.total = re.total
        _this.queryParam.pageIndex = re.pageNum
        _this.listLoading = false
      })
    },
    itemSelect (row, column, event) {
      this.selectItem = row
    },
    statusTagFormatter (status) {
      return this.enumFormat(this.statusTag, status)
    },
    statusTextFormatter (status) {
      return this.enumFormat(this.statusEnum, status)
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      statusEnum: state => state.exam.examPaperAnswer.statusEnum,
      statusTag: state => state.exam.examPaperAnswer.statusTag
    })
  }
}
</script>

<style lang="scss" scoped>

</style>
