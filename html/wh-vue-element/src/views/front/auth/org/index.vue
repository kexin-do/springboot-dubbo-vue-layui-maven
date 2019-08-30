<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="params">
        <el-form-item>
          <el-input v-model="params.orgName" placeholder="机构名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.orgCode" placeholder="机构编码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" :loading="searchLoading" type="info" v-on:click="search" circle></el-button>
          <el-button icon="el-icon-plus" type="primary" v-on:click="addShow" circle></el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="list" highlight-current-row :loading="listLoading" style="width: 100%;">
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
            <el-button @click="show(scope.row)" size="mini" type="primary" icon="el-icon-view" circle></el-button>
            <el-button @click="editShow(scope.row)" size="mini" type="primary" icon="el-icon-edit" circle></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <!--<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>-->
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :page-size="params.pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editObjShow" :width="editOrAddWidth" :close-on-click-modal="false">
      <el-form :model="editObj" label-position="right" :status-icon="true" :inline="true" size="small" label-width="100px" :rules="editFormRules" ref="editForm">
        <el-form-item label="机构名称" prop="orgName">
          <el-input v-model="editObj.orgName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="机构编码" prop="orgCode">
          <el-input v-model="editObj.orgCode"></el-input>
        </el-form-item>
        <el-form-item label="上级机构名称" prop="orgFName">
          <el-input v-model="editObj.orgFName"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="orgSts">
          <el-input-number v-model="editObj.orgSts"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editObjShow = false">取消</el-button>
        <el-button type="primary" @click="edit('editForm')" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addObjShow" :width="editOrAddWidth" :close-on-click-modal="false">
      <el-form :model="addObj" label-position="right" :status-icon="true" :inline="true" size="small" label-width="100px" :rules="editFormRules" ref="addForm">
        <el-form-item label="机构名称" prop="orgName">
          <el-input v-model="addObj.orgName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="机构编码" prop="orgCode">
          <el-input v-model="addObj.orgCode"></el-input>
        </el-form-item>
        <el-form-item label="上级机构名称" prop="orgFName">
          <el-input v-model="addObj.orgFName"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="orgSts">
          <el-input-number v-model="addObj.orgSts"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addObjShow = false">取消</el-button>
        <el-button type="primary" @click="add('addForm')" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script src="@/services/front/auth/org/orgController.js"/>
