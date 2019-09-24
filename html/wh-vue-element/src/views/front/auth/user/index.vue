<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="params">
        <el-form-item>
          <el-select v-model="params.userSts">
            <el-option v-for="info in status"
                       :key="info.val"
                       :label="info.label"
                       :value="info.val">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.userName" placeholder="用户名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.userNo" placeholder="用户代号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" :loading="loading.search" type="info" v-on:click="search" circle></el-button>
          <el-button icon="el-icon-plus" type="primary" v-on:click="showDialogWindow('add')" circle></el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="list" highlight-current-row :loading="loading.list" style="width: 100%;">
      <el-table-column prop="userName" label="用户名称" width="160">
      </el-table-column>
      <el-table-column prop="userNo" label="用户代码" width="150">
      </el-table-column>
      <el-table-column prop="orgName" label="所属机构" width="180">
      </el-table-column>
      <el-table-column prop="userSts" label="启停状态" width="80">
        <template slot-scope="scope">
          <div v-if="scope.row.userSts === '1'">
            <div slot="reference"  class="name-wrapper">
              <el-tag class="iconClass-success" size="medium">正常</el-tag>
            </div>
          </div>
          <div v-if="scope.row.userSts !== '1'">
            <el-popover trigger="hover" placement="top">
              <p>用户名称: {{ scope.row.userName }}</p>
              <p>停用时间: {{ scope.row.startTime }}</p>
              <p>停用人: {{ scope.row.startUser }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag class="iconClass-danger" size="medium">停用</el-tag>
              </div>
            </el-popover>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="userLockSts" label="锁定状态" width="80">
        <template slot-scope="scope">
            <div v-if="scope.row.userLockSts === '0'">
              <div slot="reference"  class="name-wrapper">
                <el-tag class="iconClass-success" size="medium">正常</el-tag>
              </div>
            </div>
            <div v-if="scope.row.userLockSts !== '0'">
              <el-popover trigger="hover" placement="top">
                <p>用户名称: {{ scope.row.userName }}</p>
                <p>锁定时间: {{ scope.row.lstTime }}</p>
                <p>锁定人: {{ scope.row.lstUser }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag class="iconClass-danger" size="medium">锁定</el-tag>
                </div>
              </el-popover>
            </div>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150" :formatter="formatTime">
      </el-table-column>
      <el-table-column prop="createUser" label="创建用户" width="120">
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
            <a @click="changeStatus(scope.row)" :title="scope.row.userSts !== '1' ? '启用' : '停用'">
              <i :class="scope.row.userSts !== '1' ? 'fa fa-check iconClass-success': 'fa fa-ban iconClass-danger'"></i>
            </a>
            <a @click="changeLockStatus(scope.row)" :title="scope.row.userLockSts !== '0' ? '解锁' : '锁定'">
              <i class="" aria-hidden="true"></i>
              <i :class="scope.row.userLockSts !== '0' ? 'fa fa-unlock-alt iconClass-success': 'fa fa-lock iconClass-danger'"></i>
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

    <!--详情界面-->
    <el-dialog title="详情" :visible.sync="showDialog.detail" :width="editOrAddWidth" :close-on-click-modal="false">
      <user-detail :data="currentVo" @cancelOp="cancelShowWindow"></user-detail>
    </el-dialog>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="showDialog.edit" :width="editOrAddWidth" :close-on-click-modal="false">
      <user-edit :data="currentVo" :loading="loading.edit" :checkRules="formRules" @op="edit" @cancelOp="cancelShowWindow"></user-edit>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="showDialog.add" :width="editOrAddWidth" :close-on-click-modal="false">
      <user-add :data="currentVo" :loading="loading.add" :checkRules="formRules" @op="add" @cancelOp="cancelShowWindow"></user-add>
    </el-dialog>
  </section>
</template>

<script src="@/services/front/auth/user/userController.js"/>
