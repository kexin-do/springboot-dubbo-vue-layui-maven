<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="params">
        <el-form-item>
          <el-date-picker
            v-model="selectDate"
            type="datetimerange"
            :picker-options="pickerOptions"
            v-on:change="searchDateChange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.orgName" placeholder="机构名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.orgCode" placeholder="机构编码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" :loading="loading.search" type="info" v-on:click="search" circle></el-button>
          <el-button icon="el-icon-plus" type="primary" v-on:click="showDialogWindow('add')" circle></el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="list" highlight-current-row :loading="loading.list" style="width: 100%;">
      <el-table-column type="index" width="50">
      </el-table-column>
      <el-table-column prop="orgName" label="机构名称" width="180">
      </el-table-column>
      <el-table-column prop="orgCode" label="机构编号" width="150">
      </el-table-column>
      <el-table-column prop="orgFName" label="上级机构名称" width="180">
      </el-table-column>
      <el-table-column prop="orgSts" label="状态" width="80" :formatter="formatSts">
      </el-table-column>
      <el-table-column prop="lstTime" label="操作时间" width="150" :formatter="formatTime">
      </el-table-column>
      <el-table-column prop="lstUser" label="操作用户" width="120">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-row>
            <a @click="showDetail(scope.row)" class="iconClass" title="查看详情">
              <i class="fa fa-eye" aria-hidden="true"></i>
            </a>
            <a @click="editShow(scope.row)" class="iconClass-info" title="编辑">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
            <a @click="changeStatus(scope.row)" :title="scope.row.orgSts !== '1' ? '启用' : '停用'">
              <i :class="scope.row.orgSts !== '1' ? 'fa fa-check iconClass-success': 'fa fa-ban iconClass-danger'"></i>
            </a>
          </el-row>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination
        @size-change="sizeChange"
        @current-change="pageChange"
        :current-page="params.pageNum"
        :page-sizes="pageSizes"
        :page-size="params.pageSize"
        layout="total, prev, pager, next, sizes"
        :total="total"
        style="float:right">
      </el-pagination>
      <!--<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>-->
    </el-col>

    <!--编辑界面-->
    <el-dialog title="详情" :visible.sync="showDialog.detail" :width="editOrAddWidth" :close-on-click-modal="false">
      <org-detail :data="currentVo" @cancelOp="cancelShowWindow"></org-detail>
    </el-dialog>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="showDialog.edit" :width="editOrAddWidth" :close-on-click-modal="false">
      <org-edit :data="currentVo" :loading="loading.edit" :checkRules="formRules" @op="edit" @cancelOp="cancelShowWindow"></org-edit>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="showDialog.add" :width="editOrAddWidth" :close-on-click-modal="false">
      <org-add :data="currentVo" :loading="loading.add" :checkRules="formRules" @op="add" @cancelOp="cancelShowWindow"></org-add>
    </el-dialog>
  </section>
</template>

<script src="@/services/front/auth/org/orgController.js"/>
